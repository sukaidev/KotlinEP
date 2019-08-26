package com.sukaidev.core.ui.delegates

import android.os.Bundle
import android.view.View
import com.sukaidev.core.R

/**
 * Created by sukaidev on 2019/08/26.
 *
 */
class HomeDelegate : BaseDelegate() {

    override fun setLayout(): Any {
        return R.layout.delegate_home
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
    }
}