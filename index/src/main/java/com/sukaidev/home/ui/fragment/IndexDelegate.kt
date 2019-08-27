package com.sukaidev.home.ui.fragment

import android.os.Bundle
import android.view.View
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.home.R
import com.sukaidev.home.injection.component.DaggerIndexComponent
import com.sukaidev.home.presenter.IndexPresenter
import com.sukaidev.home.presenter.view.IndexView

/**
 * Created by sukaidev on 2019/08/26.
 * 主页Fragment.
 */
class IndexDelegate : BaseMvpDelegate<IndexPresenter>(), IndexView {

    override fun injectComponent() {
        DaggerIndexComponent
            .builder()
            .activityComponent(mActivityComponent)
            .build()
            .inject(this)
    }

    override fun setLayout(): Any {
        return R.layout.delegate_index
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }
}