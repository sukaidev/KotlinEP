package com.sukaidev.kotlinmall.ui.fragment

import android.os.Bundle
import android.view.View
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.kotlinmall.R

/**
 * Created by sukaidev on 2019/08/29.
 *
 */
class EmptyDelegate : ProxyDelegate() {
    override fun setLayout(): Any {
        return R.layout.delegate_empty
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
    }
}