package com.sukaidev.core.ui.loader

import android.content.Context
import com.wang.avi.AVLoadingIndicatorView
import com.wang.avi.Indicator
import java.lang.Exception
import java.lang.StringBuilder
import java.util.*

/**
 * Created by sukaidev on 2019/09/06.
 * 加载框构建.
 */
class LoaderCreator {

    companion object {
        private val LOADING_MAP = WeakHashMap<String, Indicator>()

        fun create(type: String, context: Context): AVLoadingIndicatorView {
            val avLoadingIndicatorView = AVLoadingIndicatorView(context)
            if (LOADING_MAP[type] == null) {
                val indicator = getIndicator(type)
                LOADING_MAP[type] = indicator
            }
            avLoadingIndicatorView.indicator = LOADING_MAP[type]
            return avLoadingIndicatorView
        }

        private fun getIndicator(name: String): Indicator? {
            if (name.isEmpty()) {
                return null
            }
            val drawableClassName = StringBuilder()
            if (!name.contains(".")) {
                val defaultPackageName = AVLoadingIndicatorView::class.java.`package`!!.name
                drawableClassName
                    .append(defaultPackageName)
                    .append(".indicators")
                    .append(".")
            }
            drawableClassName.append(name)
            return try {
                val drawableClass = Class.forName(drawableClassName.toString())
                drawableClass.newInstance() as Indicator
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}