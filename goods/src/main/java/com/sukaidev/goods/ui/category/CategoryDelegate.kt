package com.sukaidev.goods.ui.category

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.orhanobut.logger.Logger
import com.sukaidev.core.ui.delegates.ProxyMvpDelegate
import com.sukaidev.core.ui.recycler.MultipleFields
import com.sukaidev.core.ui.recycler.MultipleItemEntity
import com.sukaidev.goods.R
import com.sukaidev.goods.data.protocol.Category
import com.sukaidev.goods.injection.component.DaggerCategoryComponent
import com.sukaidev.goods.injection.module.CategoryModule
import com.sukaidev.goods.presenter.CategoryPresenter
import com.sukaidev.goods.presenter.view.CategoryView
import kotlinx.android.synthetic.main.delegate_category.*
import org.jetbrains.anko.toast

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
        initRecycler()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        loadData()
    }

    /**
     * 初始化视图
     */
    private fun initRecycler() {
        mListRv.layoutManager = LinearLayoutManager(context)
        mContentRv.layoutManager = GridLayoutManager(context, 3)
    }

    /**
     * 加载数据
     */
    private fun loadData(parentId: Int = 0) {
        mPresenter.getCategory(parentId)
    }

    override fun onGetCategoryResult(result: MutableList<Category>?) {
        result?.let {
            if (it[0].parentId == 0) {
                it[0].isSelected = true
                mListAdapter =
                    CategoryListAdapter.create(CategoryDataConverter().setListData(it).convert())
                mListRv.adapter = mListAdapter
                mListAdapter.onItemClickListener =
                    BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
                        val itemId =
                            (adapter?.getItem(position) as MultipleItemEntity).getField<Int>(
                                MultipleFields.ID
                            )
                        for (category in adapter.data) {
                            val categoryId =
                                (category as MultipleItemEntity).getField<Int>(MultipleFields.ID)
                            category.setField(MultipleFields.IS_SELECTED, itemId == categoryId)
                        }
                        adapter.notifyDataSetChanged()

                        loadData(itemId)
                    }
                mPresenter.getCategory(it[0].id)
            } else {
                mContentAdapter =
                    CategoryContentAdapter(CategoryDataConverter().setListData(it).convert())
                mContentRv.adapter = mContentAdapter
            }
        }
    }
}