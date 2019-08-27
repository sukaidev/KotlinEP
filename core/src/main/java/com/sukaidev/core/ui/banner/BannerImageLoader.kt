package com.sukaidev.core.ui.banner

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.youth.banner.loader.ImageLoader

/**
 * Created by sukaidev on 2019/08/13.
 * 自定义ImageLoader.
 */
class BannerImageLoader : ImageLoader() {

    private val OPTIONS = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .dontAnimate()
        .centerCrop()

    override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
        Glide.with(context)
            .load(path.toString())
            .apply(OPTIONS)
            .into(imageView)
    }
}