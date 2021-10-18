package com.prashant.naik.ezcart

import android.content.*
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.prashant.naik.ezcart.data.profile.UserProfile
import com.prashant.naik.ezcart.ui.home.HomeFragment
import com.prashant.naik.ezcart.ui.home.HomeFragmentDirections
import com.prashant.naik.ezcart.utils.Constants.Companion.IMAGE_DIRECTORY
import com.prashant.naik.ezcart.utils.loadProfilePictureFromInternalStorage
import dagger.hilt.android.AndroidEntryPoint
import java.io.*
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var userProfileTextView: TextView
    private lateinit var userProfileImageView: ImageView
    private lateinit var userProfile: UserProfile
    private val REQUEST_IMAGE_CAPTURE = 111
    private val PICK_IMAGE = 122
    lateinit var toolbar: Toolbar
    private lateinit var notificationText: TextView
    private var notificationCount: String = "0"
    private lateinit var navController: NavController
    private lateinit var cartView: MenuItem
    private var cartViewVisibility: Boolean = false
    lateinit var toolbarLogoLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbarLogoLayout = toolbar[0].findViewById(R.id.constraint_toolbar_layout)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        userProfileTextView = navView.getHeaderView(0).findViewById(R.id.profile_text)
        userProfileImageView = navView.getHeaderView(0).findViewById(R.id.profile_image)
        userProfileImageView.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.apply {
                setTitle(getString(R.string.set_profile_image_title))
                    .setItems(
                        arrayOf(
                            getString(R.string.source_camera),
                            getString(R.string.source_gallary)
                        ),
                        DialogInterface.OnClickListener { dialog, which ->
                            when (which) {
                                0 -> dispatchTakePictureIntent()
                                1 -> dispatchPicturePickerIntent()
                            }
                        })
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.cartFragment,
                R.id.orderFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_logout -> {
                    this@MainActivity.drawerLayout.closeDrawer(GravityCompat.START)
                    val builder = AlertDialog.Builder(this)
                    builder.apply {
                        setTitle(getString(R.string.log_out_title))
                        setMessage(getString(R.string.log_out_message))
                        setPositiveButton(
                            R.string.logout_yes
                        ) { _, _ ->
                            val foregroundFragment = getForegroundFragment()
                            if (foregroundFragment is HomeFragment) {
                                foregroundFragment.clearUserData()
                                Toast.makeText(
                                    this@MainActivity,
                                    getString(R.string.log_out_success),
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                            }
                        }
                        setNegativeButton(
                            R.string.logout_no
                        ) { dialog, _ ->
                            dialog.cancel()
                        }
                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }
                R.id.cartFragment -> {
                    navController.navigate(R.id.cartFragment)
                    this.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.orderFragment -> {
                    navController.navigate(R.id.orderFragment)
                    this.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.feedbackFragment -> {
                    navController.navigate(R.id.feedbackFragment)
                    this.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.helpFragment -> {
                    navController.navigate(R.id.helpFragment)
                    this.drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }
    }

    private fun getForegroundFragment(): Fragment? {
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        return navHostFragment?.childFragmentManager?.fragments?.get(0)
    }

    private fun dispatchPicturePickerIntent() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, PICK_IMAGE)
    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun setCartViewVisibility(visibility: Boolean) {
        cartViewVisibility = visibility
        invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        val count: View = menu.findItem(R.id.badge).actionView
        cartView = menu.findItem(R.id.badge)
        notificationText = count.findViewById(R.id.cart_notification) as TextView
        if (Integer.parseInt(notificationCount) == 0) {
            notificationText.visibility = View.GONE
        } else {
            notificationText.visibility = View.VISIBLE
            notificationText.text = notificationCount
        }
        cartView.isVisible = cartViewVisibility
        count.setOnClickListener {
            try {
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToCartFragment())
            } catch (e: Exception) {
                Log.e(
                    "MainActivity",
                    "Navigation from this destination is not currently linked to the Cart fragment"
                )
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    fun setNotificationCount(count: Int) {
        if (this::notificationText.isInitialized) {
            notificationCount = count.toString()
            invalidateOptionsMenu()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            userProfileImageView.setImageBitmap(imageBitmap)
            saveToInternalStorage(imageBitmap, userProfile.userId, this)
            Glide.with(this)
                .load(
                    loadProfilePictureFromInternalStorage(this, userProfile.userId)
                        ?: ResourcesCompat.getDrawable(resources, R.drawable.profile_placeholder, null)
                )
                .circleCrop()
                .into(userProfileImageView)
        } else if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            val imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, data?.data)
            userProfileImageView.setImageBitmap(imageBitmap)
            saveToInternalStorage(imageBitmap, userProfile.userId, this)
            Glide.with(this)
                .load(data?.data)
                .circleCrop()
                .into(userProfileImageView)
        }
    }

    fun updateUserProfileDetails(userProfile: UserProfile) {
        this.userProfile = userProfile
        userProfileTextView.text = userProfile.getNormalisedName()
        Glide.with(this)
            .load(
                loadProfilePictureFromInternalStorage(this, userProfile.userId)
                    ?: resources.getDrawable(R.drawable.profile_placeholder)
            )
            .circleCrop()
            .into(userProfileImageView)
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            Toast.makeText(this, getString(R.string.intent_failure_message), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun saveToInternalStorage(
        bitmapImage: Bitmap,
        userId: String,
        context: Context
    ): String? {
        val contextWrapper = ContextWrapper(context)
        val directory = contextWrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        val filePath = File(directory, "$userId.jpg")
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(filePath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fileOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.absolutePath
    }
}