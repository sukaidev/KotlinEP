package com.sukaidev.mine.ui.setttings

import android.os.Bundle
import android.view.View
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.mine.R

/**
 * Created by sukaidev on 2019/08/30.
 * 关于页面.
 */
class AboutDelegate:ProxyDelegate() {

    override fun setLayout(): Any {
        return R.layout.delegate_about_app
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }
}