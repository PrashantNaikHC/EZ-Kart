package com.prashant.naik.ezcart

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.prashant.naik.ezcart.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var userProfileTextView: TextView
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        userProfileTextView = navView.getHeaderView(0).findViewById(R.id.profile_text)
        builder = AlertDialog.Builder(this)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
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
                    builder.apply {
                        setTitle(getString(R.string.log_out_title))
                        setMessage(getString(R.string.log_out_message))
                        setPositiveButton(
                            R.string.logout_yes
                        ) { _, _ ->
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.log_out_success),
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun updateUserProfileName(name: String) {
        userProfileTextView.text = name
    }
}