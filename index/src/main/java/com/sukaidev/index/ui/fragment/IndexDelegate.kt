package com.sukaidev.index.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ui.delegates.BaseDelegate
import com.sukaidev.core.ui.delegates.ProxyMvpDelegate
import com.sukaidev.core.ui.recycler.BaseDecoration
import com.sukaidev.core.ui.recycler.MultipleRecyclerAdapter
import com.sukaidev.index.R
import com.sukaidev.index.injection.component.DaggerIndexComponent
import com.sukaidev.index.injection.module.IndexModule
import com.sukaidev.index.presenter.IndexPresenter
import com.sukaidev.index.presenter.view.IndexView
import com.sukaidev.index.ui.adapter.IndexDataConverter
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.delegate_index.*

/**
 * Created by sukaidev on 2019/08/26.
 * 主页Fragment.
 */
class IndexDelegate : ProxyMvpDelegate<IndexPresenter>(), IndexView {

    private lateinit var mAdapter: MultipleRecyclerAdapter

    private var mBanner: Banner? = null

    override fun injectComponent() {
        DaggerIndexComponent
            .builder()
            .activityComponent(mActivityComponent)
            .indexModule(IndexModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Any {
        return R.layout.delegate_index
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initRecyclerView()
        mSearchTv.onClick { getParentDelegate<BaseDelegate>().supportDelegate.start(SearchDelegate())
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        mPresenter.getIndexData()
    }

    private fun initRecyclerView() {
        val manager = GridLayoutManager(context, 4)
        mIndexRv.layoutManager = manager
        mIndexRv.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(context!!, R.color.white), 10))
    }

    override fun onGetIndexDataResult(result: String) {
        mAdapter = MultipleRecyclerAdapter.create(IndexDataConverter().setJsonData(result))
        mIndexRv.adapter = mAdapter
        mBanner = mAdapter.getViewByPosition(0, R.id.mIndexBanner) as Banner
    }

    override fun onResume() {
        super.onResume()
        mBanner?.startAutoPlay()
    }

    override fun onPause() {
        super.onPause()
        mBanner?.stopAutoPlay()

    }
}