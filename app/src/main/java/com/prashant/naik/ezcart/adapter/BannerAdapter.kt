package com.prashant.naik.ezcart.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.prashant.naik.ezcart.R

class BannerAdapter(
    val bannerImages: List<Drawable?>,
    val context: Context
) : PagerAdapter() {

    override fun getCount(): Int {
        return bannerImages.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.banner_item_layout, container, false)
        val ivProductImage: AppCompatImageView = view.findViewById(R.id.banner_image)
        Glide.with(context)
            .load(bannerImages[position])
            .into(ivProductImage)

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}