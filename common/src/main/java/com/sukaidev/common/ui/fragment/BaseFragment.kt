package com.sukaidev.common.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle.components.support.RxFragment

/**
 * Created by sukaidev on 2019/08/10.
 * Fragment基类.
 */
abstract class BaseFragment : RxFragment() {
    /**
     * 设置Fragment布局
     */
    abstract fun setLayout(): Any

    /**
     * 子类逻辑
     */
    abstract fun onBindView(savedInstanceState: Bundle?, rootView: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return when {
            setLayout() is Int -> inflater.inflate(setLayout() as Int, container, false)
            setLayout() is View -> setLayout() as View
            else -> throw ClassCastException("setLayout() type must be int or View!")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onBindView(savedInstanceState,view)
    }
}