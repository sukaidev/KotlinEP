package com.sukaidev.core.ui.banner

import com.chad.library.adapter.base.BaseQuickAdapter
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer

/**
 * Created by sukaidev on 2019/08/27.
 * 创建Banner.
 */
class BannerCreator {

    companion object {
        fun setDefault(
            banner: Banner,
            banners: ArrayList<String>,
            clickListener: BaseQuickAdapter.OnItemClickListener
        ) {
            // 设置图片加载器
            banner.setImageLoader(BannerImageLoader())
                .setImages(banners)
                .setBannerAnimation(Transformer.Default)
                .setDelayTime(2000)
                .setIndicatorGravity(BannerConfig.CENTER)
                .start()
        }
    }
}