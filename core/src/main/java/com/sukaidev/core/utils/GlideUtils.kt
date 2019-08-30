package com.sukaidev.core.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sukaidev.core.R

/**
 * Glide工具类
 */
object GlideUtils {

    private val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .dontAnimate()
        .centerCrop()

    fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).apply(options).into(imageView)
    }

    fun loadImageFitCenter(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
            .fitCenter().into(imageView)
    }

    /**
     * 当fragment或者activity失去焦点或者destroyed的时候，Glide会自动停止加载相关资源，确保资源不会被浪费
     */
    fun loadUrlImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).placeholder(R.drawable.icon_back)
            .error(R.drawable.icon_state_error).centerCrop().into(imageView)
    }
}
