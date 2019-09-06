package com.sukaidev.core.utils

import com.sukaidev.core.common.BaseApplication

/**
 * Created by sukaidev on 2019/09/06.
 *
 */
class DimenUtil {

    companion object {
        /**
         * 拿到屏幕的宽
         */
        fun getScreenWidth(): Int {
            val resource = BaseApplication.instance.resources
            val dm = resource.displayMetrics
            return dm.widthPixels
        }

        /**
         * 拿到屏幕的高
         */
        fun getScreenHeight(): Int {
            val resource = BaseApplication.instance.resources
            val dm = resource.displayMetrics
            return dm.heightPixels
        }
    }
}