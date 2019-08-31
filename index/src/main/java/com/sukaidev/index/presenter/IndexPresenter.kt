package com.sukaidev.index.presenter

import com.sukaidev.core.ext.execute
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.index.presenter.view.IndexView
import com.sukaidev.index.service.IndexService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/27.
 *
 */
class IndexPresenter @Inject constructor() : BasePresenter<IndexView>() {

    @Inject
    lateinit var service: IndexService

    /**
     * 获取首页数据
     */
    fun getIndexData() {
        if (!checkNetWork()) {
            return
        }
        service.getIndexData().execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onGetIndexDataResult(t)
            }
        }, lifecycleProvider)
    }

}