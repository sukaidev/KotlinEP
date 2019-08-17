package com.sukaidev.common.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.ImageViewCompat
import com.sukaidev.common.R
import com.sukaidev.common.ext.onClick
import kotlinx.android.synthetic.main.layout_header_bar.view.*

/**
 * Created by sukaidev on 2019/08/11.
 *
 */
class HeaderBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var isShowBack = true
    private var titleText: String? = null
    private var rightText: String? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar)

        isShowBack = typedArray.getBoolean(R.styleable.HeaderBar_isShowBack, true)
        titleText = typedArray.getString(R.styleable.HeaderBar_titleText)
        rightText = typedArray.getString(R.styleable.HeaderBar_rightText)

        typedArray.recycle()

        initView()
    }

    private fun initView() {
        View.inflate(context, R.layout.layout_header_bar, this)

        mLeftIv.visibility = if (isShowBack) View.VISIBLE else View.INVISIBLE

        titleText?.let {
            mTitleTv.text = it
        }
        rightText?.let {
            mRightTv.text = it
            mRightTv.visibility = View.VISIBLE
        }

        mLeftIv.onClick {
            if (context is Activity) {
                (context as Activity).finish()
            }
        }
    }

    fun getRightTv(): AppCompatTextView {
        return mRightTv
    }

    fun getLeftIv(): AppCompatImageView {
        return mLeftIv
    }
}