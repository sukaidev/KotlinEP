package com.sukaidev.core.ui.loader

import android.content.Context
import android.view.Gravity
import androidx.appcompat.app.AppCompatDialog
import com.sukaidev.core.R
import com.sukaidev.core.utils.DimenUtil

/**
 * Created by sukaidev on 2019/09/06.
 *
 */
class Loader {

    companion object {
        // Loader缩放比
        private const val LOADER_SIZE_SCALE = 8
        // Loader偏移量
        private const val LOADER_OFFSET_SCALE = 10

        private val LOADERS = ArrayList<AppCompatDialog>()

        private val DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name

        fun showLoading(context: Context, type: String) {

            val dialog = AppCompatDialog(context, R.style.dialog)
            val loadingIndicatorView = LoaderCreator.create(type, context)
            dialog.setContentView(loadingIndicatorView)

            val deviceWidth = DimenUtil.getScreenWidth()
            val deviceHeight = DimenUtil.getScreenHeight()

            val dialogWindow = dialog.window
            dialogWindow?.let {
                val lp = dialogWindow.attributes
                lp.width = deviceWidth / 8
                lp.height = deviceHeight / 8
                lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE
                lp.gravity = Gravity.CENTER
            }
            LOADERS.add(dialog)
            dialog.show()
        }

        fun showLoading(context: Context, loaderStyle: Enum<LoaderStyle>) {
            showLoading(context, loaderStyle.name)
        }

        fun showLoading(context: Context) {
            showLoading(context, DEFAULT_LOADER)
        }

        fun stopLoading() {
            for (dialog in LOADERS) {
                dialog.let {
                    if (dialog.isShowing) {
                        dialog.cancel()
                    }
                }
            }
        }
    }


}