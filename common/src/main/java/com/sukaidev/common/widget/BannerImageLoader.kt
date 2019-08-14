package com.sukaidev.common.widget

import android.content.Context
import android.widget.ImageView
import com.sukaidev.common.utils.GlideUtils
import com.youth.banner.loader.ImageLoader

/**
 * Created by sukaidev on 2019/08/13.
 * 自定义ImageLoader.
 */
class BannerImageLoader : ImageLoader() {

    override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
        GlideUtils.loadUrlImage(context, path.toString(), imageView)
    }
}