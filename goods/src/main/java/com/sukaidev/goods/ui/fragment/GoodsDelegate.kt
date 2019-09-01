package com.sukaidev.goods.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.sukaidev.core.common.GoodsConstant
import com.sukaidev.core.ext.setViewStateEmpty
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.goods.R
import com.sukaidev.goods.data.protocol.Goods
import com.sukaidev.goods.injection.component.DaggerGoodsComponent
import com.sukaidev.goods.injection.module.GoodsModule
import com.sukaidev.goods.presenter.GoodsListPresenter
import com.sukaidev.goods.presenter.view.GoodsListView
import com.sukaidev.goods.ui.adapter.GoodsAdapter
import kotlinx.android.synthetic.main.delegate_goods.*

/**
 * Created by sukaidev on 2019/08/15.
 * 商品详细列表页面.
 */
class GoodsDelegate : BaseMvpDelegate<GoodsListPresenter>(), GoodsListView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private lateinit var mGoodsAdapter: GoodsAdapter
    private var mCurrentPage: Int = 1
    private var mMaxPage: Int = 1

    // 搜索商品类型
    private var searchGoodsType: Int? = 0
    // 商品关键字
    private var keyWord: String? = null
    // 商品分类ID
    private var keyCategoryId: Int? = 0

    override fun injectComponent() {
        DaggerGoodsComponent
            .builder()
            .activityComponent(mActivityComponent)
            .goodsModule(GoodsModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Any {
        return R.layout.delegate_goods
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initRefreshLayout()

        searchGoodsType = arguments?.getInt(GoodsConstant.KEY_SEARCH_GOODS_TYPE)
        keyWord = arguments?.getString(GoodsConstant.KEY_GOODS_KEYWORD)
        keyCategoryId = arguments?.getInt(GoodsConstant.KEY_CATEGORY_ID)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        loadData()
    }

    /**
     * 初始化下拉刷新与上拉加载
     */
    private fun initRefreshLayout() {
        mRefreshLayout.setDelegate(this)
        val viewHolder = BGANormalRefreshViewHolder(context, true)
        viewHolder.setLoadMoreBackgroundColorRes(R.color.common_bg)
        viewHolder.setRefreshViewBackgroundColorRes(R.color.common_bg)
        mRefreshLayout.setRefreshViewHolder(viewHolder)
    }

    /**
     * 初始化视图
     */
    private fun initView(data: MutableList<Goods>) {
        mGoodsRv.layoutManager = GridLayoutManager(context, 2)
        mGoodsAdapter = GoodsAdapter(R.layout.item_goods, data)
        mGoodsRv.adapter = mGoodsAdapter
        mGoodsAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
        }
    }

    /**
     * 加载数据
     */
    private fun loadData() {
        if (searchGoodsType != 0) {
            keyWord?.let {
                mPresenter.getGoodsListByKeyword(it, mCurrentPage)
            }
        } else {
            keyCategoryId?.let {
                mPresenter.getGoodsList(it, mCurrentPage)
            }
        }
    }

    /**
     * 获取商品列表回调
     */
    override fun onGetGoodsListResult(result: MutableList<Goods>?) {
        mRefreshLayout.endLoadingMore()
        mRefreshLayout.endRefreshing()
        if (result != null && result.size > 0) {
            if (mGoodsRv.visibility == View.GONE) {
                mGoodsRv.visibility = View.VISIBLE
            }
            mMaxPage = result[0].maxPage
            if (mCurrentPage == 1) {
                initView(result)
            } else {
                mGoodsAdapter.addData(result)
                mGoodsAdapter.notifyDataSetChanged()
            }
        } else {
            setViewStateEmpty(this, mRefreshLayout, R.id.stub_goods_empty, R.id.tv_stub_content_empty, "暂时没有商品")
        }
    }


    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        return if (mCurrentPage < mMaxPage) {
            mCurrentPage++
            loadData()
            true
        } else {
            false
        }
    }

    /**
     * 加载第一页
     */
    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
        mCurrentPage = 1
        loadData()
    }
}