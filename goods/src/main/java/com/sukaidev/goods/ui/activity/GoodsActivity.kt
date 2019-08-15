package com.sukaidev.goods.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.kennyc.view.MultiStateView
import com.sukaidev.common.ext.startLoading
import com.sukaidev.common.ui.activity.BaseMvpActivity
import com.sukaidev.common.ui.adapter.BaseRecyclerViewAdapter
import com.sukaidev.goods.R
import com.sukaidev.goods.common.GoodsConstant
import com.sukaidev.goods.data.protocol.Goods
import com.sukaidev.goods.injection.component.DaggerGoodsComponent
import com.sukaidev.goods.injection.module.GoodsModule
import com.sukaidev.goods.presenter.GoodsListPresenter
import com.sukaidev.goods.presenter.view.IGoodsListView
import com.sukaidev.goods.ui.adapter.GoodsAdapter
import kotlinx.android.synthetic.main.activity_goods.*

/**
 * Created by sukaidev on 2019/08/15.
 * 商品列表页面.
 */
class GoodsActivity : BaseMvpActivity<GoodsListPresenter>(), IGoodsListView,
    BGARefreshLayout.BGARefreshLayoutDelegate {

    private lateinit var mGoodsAdapter: GoodsAdapter
    private var mCurrentPage: Int = 1
    private var mMaxPage: Int = 1

    override fun injectComponent() {
        DaggerGoodsComponent
            .builder()
            .activityComponent(activityComponent)
            .goodsModule(GoodsModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Int {
        return R.layout.activity_goods
    }

    override fun onBindView(savedInstanceState: Bundle?) {
        initView()
        initRefreshLayout()
        loadData()
    }

    /**
     * 初始化视图
     */
    private fun initView() {
        mGoodsRv.layoutManager = GridLayoutManager(this, 2)
        mGoodsAdapter = GoodsAdapter(this)
        mGoodsRv.adapter = mGoodsAdapter

        mGoodsAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<Goods> {
            override fun onItemClick(item: Goods, position: Int) {

            }
        })
    }

    /**
     * 初始化下拉刷新与上拉加载
     */
    private fun initRefreshLayout() {
        mRefreshLayout.setDelegate(this)
        val viewHolder = BGANormalRefreshViewHolder(this, true)
        viewHolder.setLoadMoreBackgroundColorRes(R.color.common_bg)
        viewHolder.setRefreshViewBackgroundColorRes(R.color.common_bg)
        mRefreshLayout.setRefreshViewHolder(viewHolder)
    }

    /**
     * 加载数据
     */
    private fun loadData() {
        if (intent.getIntExtra(GoodsConstant.KEY_SEARCH_GOODS_TYPE, 0) != 0) {
            mMultiStateView.startLoading()
            mPresenter.getGoodsListByKeyword(
                intent.getStringExtra(GoodsConstant.KEY_GOODS_KEYWORD),
                mCurrentPage
            )
        } else {
            mMultiStateView.startLoading()
            mPresenter.getGoodsList(
                intent.getIntExtra(GoodsConstant.KEY_CATEGORY_ID, 1),
                mCurrentPage
            )
        }
    }

    /**
     * 获取列表回调
     */
    override fun onGetGoodsListResult(result: MutableList<Goods>?) {
        mRefreshLayout.endLoadingMore()
        mRefreshLayout.endRefreshing()
        if (result != null && result.size > 0) {
            mMaxPage = result[0].maxPage
            if (mCurrentPage == 1) {
                mGoodsAdapter.setData(result)
            } else {
                mGoodsAdapter.dataList.addAll(result)
                mGoodsAdapter.notifyDataSetChanged()
            }
            mMultiStateView.viewState = MultiStateView.ViewState.CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.ViewState.EMPTY
        }
    }

    /**
     * 下拉加载第一页
     */
    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
        mCurrentPage = 1
        loadData()
    }

    /**
     * 上拉加载更多
     */
    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        return if (mCurrentPage < mMaxPage) {
            mCurrentPage++
            loadData()
            true
        } else {
            false
        }
    }
}
