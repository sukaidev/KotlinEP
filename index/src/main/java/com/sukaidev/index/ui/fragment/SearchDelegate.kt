package com.sukaidev.index.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sukaidev.core.common.GoodsConstant
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ext.setVisible
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.core.utils.AppPrefsUtils
import com.sukaidev.index.ui.adapter.SearchHistoryAdapter
import kotlinx.android.synthetic.main.delegate_search.*
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/29.
 * 搜索页面.
 */
class SearchDelegate : ProxyDelegate(), View.OnClickListener {

    private lateinit var mAdapter: SearchHistoryAdapter

    override fun setLayout(): Any {
        return com.sukaidev.index.R.layout.delegate_search
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mLeftIv.onClick(this)
        mSearchTv.onClick(this)
        mClearBtn.onClick(this)
        //RecyclerView适配器
        mAdapter = SearchHistoryAdapter()
        mSearchHistoryRv.layoutManager = LinearLayoutManager(context)
        mSearchHistoryRv.adapter = mAdapter
        // item点击事件
        mAdapter.setOnItemClickListener { adapter, view, position ->
            enterGoodsList(adapter.getItem(position) as String)
        }
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    /**
     * 从SharedPreferences中读取数据
     */
    private fun loadData() {
        val set = AppPrefsUtils.getStringSet(GoodsConstant.SP_SEARCH_HISTORY)
        mNoDataTv.setVisible(set.isEmpty())
        mDataView.setVisible(set.isNotEmpty())
        if (set.isNotEmpty()) {
            val list = mutableListOf<String>()
            list.addAll(set)
            mAdapter.setNewData(list)
        }
    }

    /**
     * 搜索
     */
    private fun doSearch() {
        if (mKeywordEt.text.isNullOrEmpty()) {
            context?.toast("请输入需要搜索的关键字")
        } else {
            val inputValue = mKeywordEt.text.toString()
            AppPrefsUtils.putStringSet(GoodsConstant.SP_SEARCH_HISTORY, mutableSetOf(inputValue))
            enterGoodsList(inputValue)
        }
    }

    /**
     * 跳转到商品列表界面
     */
    private fun enterGoodsList(value: String) {
        value.let {
            context?.toast(value)
        }
        //输入不为空，进入商品列表
//        startActivity<GoodsActivity>(
//            GoodsConstant.KEY_SEARCH_GOODS_TYPE to GoodsConstant.SEARCH_GOODS_TYPE_KEYWORD,
//            GoodsConstant.KEY_GOODS_KEYWORD to value
//        )
    }

    override fun onClick(v: View?) {
        when (v) {
            mLeftIv -> {
                // 先将键盘隐藏再将fragment出栈
                val inputMethodManager =
                    getProxyActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(mSearchTv.windowToken, 0)
                supportDelegate.pop()
            }
            mSearchTv -> doSearch()
            mClearBtn -> {
                AppPrefsUtils.remove(GoodsConstant.SP_SEARCH_HISTORY)
                loadData()
            }
        }
    }
}