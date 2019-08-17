package com.sukaidev.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.sukaidev.common.R
import kotlinx.android.synthetic.main.layout_label_textview.view.*

/**
 * Created by sukaidev on 2019/08/17.
 * LabelTextView封装.
 */
class LabelTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mLabelText: CharSequence? = null
    private var mContentText: CharSequence? = null

    //初始化属性
    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelTextView)
        this.mLabelText = typedArray.getText(R.styleable.LabelTextView_labelText)
        this.mContentText = typedArray.getText(R.styleable.LabelTextView_contentText)
        initView()
        typedArray.recycle()
    }

    //初始化视图
    private fun initView() {
        View.inflate(context, R.layout.layout_label_textview, this)

        mLabelText?.let {
            mLabelTv.text = it
        }

        mContentText?.let {
            mContentTv.text = it
        }
    }

    /**
     * 设置内容
     */
    fun setContentText(text:String){
        mContentTv.text = text
    }
}