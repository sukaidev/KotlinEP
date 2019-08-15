package com.sukaidev.goods.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.sukaidev.common.ext.startLoading
import com.sukaidev.common.ui.adapter.BaseRecyclerViewAdapter
import com.sukaidev.common.ui.fragment.BaseMvpFragment
import com.sukaidev.goods.R
import com.sukaidev.goods.common.GoodsConstant
import com.sukaidev.goods.data.protocol.Category
import com.sukaidev.goods.injection.component.DaggerCategoryComponent
import com.sukaidev.goods.injection.module.CategoryModule
import com.sukaidev.goods.presenter.CategoryPresenter
import com.sukaidev.goods.presenter.view.ICategoryView
import com.sukaidev.goods.ui.activity.GoodsActivity
import com.sukaidev.goods.ui.adapter.SecondCategoryAdapter
import com.sukaidev.goods.ui.adapter.TopCategoryAdapter
import kotlinx.android.synthetic.main.fragment_category.*
import org.jetbrains.anko.startActivity

/**
 * Created by sukaidev on 2019/08/15.
 * 分类页面.
 */
class CategoryFragment : BaseMvpFragment<CategoryPresenter>(), ICategoryView {

    // 一级分类Adapter
    lateinit var mTopAdapter: TopCategoryAdapter
    // 二级分类Adapter
    lateinit var mSecondAdapter: SecondCategoryAdapter

    override fun injectComponent() {
        DaggerCategoryComponent
            .builder()
            .activityComponent(activityComponent)
            .categoryModule(CategoryModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Any {
        return R.layout.fragment_category
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initView()
        loadData()
    }


    private fun initView() {
        mTopCategoryRv.layoutManager = LinearLayoutManager(context)
        mTopAdapter = TopCategoryAdapter(context!!)
        mTopCategoryRv.adapter = mTopAdapter
        //单项点击事件
        mTopAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {
                for (category in mTopAdapter.dataList) {
                    category.isSelected = item.id == category.id
                }
                mTopAdapter.notifyDataSetChanged()

                loadData(item.id)
            }
        })

        mSecondCategoryRv.layoutManager = GridLayoutManager(context, 3)
        mSecondAdapter = SecondCategoryAdapter(context!!)
        mSecondCategoryRv.adapter = mSecondAdapter
        mSecondAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {
                context!!.startActivity<GoodsActivity>(GoodsConstant.KEY_CATEGORY_ID to item.id)
            }
        })
    }

    private fun loadData(parentId: Int = 0) {
        if (parentId != 0) {
            mMultiStateView.startLoading()
        }
        mPresenter.getCategory(parentId)
    }

    override fun onGetCategoryResult(result: MutableList<Category>?) {
        result?.let {
            if (it[0].parentId == 0) {
                it[0].isSelected = true
                mTopAdapter.setData(it)
                mPresenter.getCategory(it[0].id)
            } else {
                mSecondAdapter.setData(it)
                mMultiStateView.viewState = MultiStateView.ViewState.CONTENT
            }
        }
    }
}