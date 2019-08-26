package com.sukaidev.core.ui.delegates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import com.sukaidev.core.ui.activities.ProxyActivity


/**
 * Created by sukaidev on 2019/08/25.
 *
 */
abstract class BaseDelegate : FastSupportDelegate() {

    /**
     * 设置布局
     */
    abstract fun setLayout(): Any

    /**
     * 视图初始化
     */
    abstract fun onBindView(@Nullable savedInstanceState: Bundle?, rootView: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return when (setLayout()) {
            is Int -> inflater.inflate(setLayout() as Int, container, false)
            is View -> setLayout() as View
            else -> throw ClassCastException("setLayout() type must be int or View!")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView(savedInstanceState, view)
    }

    fun getProxyActivity(): ProxyActivity {
        return _mActivity as ProxyActivity
    }
}