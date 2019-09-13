package com.sukaidev.order.ui.fragment

import android.os.Bundle
import android.view.View
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.order.R

/**
 * Created by sukaidev on 2019/09/11.
 *
 */
class EmptyPlaceHolderDelegate :ProxyDelegate(){

    override fun setLayout(): Any {
        return R.layout.delegate_empty
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
    }
}