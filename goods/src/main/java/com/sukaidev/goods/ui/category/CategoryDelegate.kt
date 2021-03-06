package com.sukaidev.goods.ui.category

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.sukaidev.core.common.GoodsConstant
import com.sukaidev.core.ext.setViewStateEmpty
import com.sukaidev.core.ext.startWithNewBundle
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.core.ui.delegates.ProxyMvpDelegate
import com.sukaidev.goods.R
import com.sukaidev.goods.data.protocol.Category
import com.sukaidev.goods.injection.component.DaggerCategoryComponent
import com.sukaidev.goods.injection.module.CategoryModule
import com.sukaidev.goods.presenter.CategoryPresenter
import com.sukaidev.goods.presenter.view.CategoryView
import com.sukaidev.goods.ui.goods.GoodsListDelegate
import kotlinx.android.synthetic.main.delegate_category.*

/**
 * Created by sukaidev on 2019/08/15.
 * 分类页面.
 */
class CategoryDelegate : ProxyMvpDelegate<CategoryPresenter>(), CategoryView {

    // 列表数据适配器
    private lateinit var mListAdapter: CategoryListAdapter
    // 内容数据适配器
    private lateinit var mContentAdapter: CategoryContentAdapter

    override fun injectComponent() {
        DaggerCategoryComponent
            .builder()
            .activityComponent(mActivityComponent)
            .categoryModule(CategoryModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Any {
        return R.layout.delegate_category
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initView()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        loadData()
    }

    private fun initView() {
        mListRv.layoutManager = LinearLayoutManager(context)
        mListAdapter = CategoryListAdapter(null)
        mListRv.adapter = mListAdapter
        mListAdapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
                if (position != (adapter as CategoryListAdapter).mPrePosition) {
                    val itemId = (adapter.getItem(position) as Category).id
                    for (category in adapter.data) {
                        val categoryId = (category as Category).id
                        category.isSelected = itemId == categoryId
                    }
                    adapter.notifyItemChanged(adapter.mPrePosition)
                    adapter.notifyItemChanged(position)
                    adapter.mPrePosition = position
                    loadData(itemId)
                }
            }

        mContentRv.layoutManager = GridLayoutManager(context, 3)
        mContentAdapter = CategoryContentAdapter(null)
        mContentRv.adapter = mContentAdapter
        mContentAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val categoryId = (adapter.getItem(position) as Category).id
            getParentDelegate<ProxyDelegate>()
                .supportDelegate.startWithNewBundle<GoodsListDelegate>(GoodsConstant.KEY_CATEGORY_ID to categoryId)
        }
    }

    /**
     * 加载数据
     */
    private fun loadData(parentId: Int = 0) {
        mPresenter.getCategory(parentId)
    }

    /**
     * 初始化recycler
     */
    private fun loadRecycler(data: MutableList<Category>?) {
        data?.let {
            if (it[0].parentId == 0) {
                it[0].isSelected = true
                mListAdapter.setNewData(it)
                mPresenter.getCategory(it[0].id)
            } else {
                mContentAdapter.setNewData(it)
            }
        }
    }

    override fun onGetCategoryResult(result: MutableList<Category>?) {
        if (result.isNullOrEmpty()) {
/*            if (mContentRv.visibility != View.GONE) {
                // ViewStub填充后会变为null 所以每次都需初始化
                mViewStub = view?.findViewById(R.id.view_stub_empty)
                val stubView = mViewStub?.inflate()
                val tvShowEmpty = stubView?.find<AppCompatTextView>(R.id.tv_stub_content_empty)
                tvShowEmpty?.setOnClickListener {

                }
                mContentRv.visibility = View.GONE
            }*/
            setViewStateEmpty(this, mContentRv, R.id.view_stub_empty, R.id.tv_stub_content_empty, "这里还没有商品")
        } else {
            if (mContentRv.visibility == View.GONE) {
                mContentRv.visibility = View.VISIBLE
            }
            loadRecycler(result)
        }
    }


}