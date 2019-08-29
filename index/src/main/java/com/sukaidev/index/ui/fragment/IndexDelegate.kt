package com.sukaidev.index.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.core.ui.recycler.BaseDecoration
import com.sukaidev.core.ui.recycler.MultipleRecyclerAdapter
import com.sukaidev.index.R
import com.sukaidev.index.data.data
import com.sukaidev.index.injection.component.DaggerIndexComponent
import com.sukaidev.index.injection.module.IndexModule
import com.sukaidev.index.presenter.IndexPresenter
import com.sukaidev.index.presenter.view.IndexView
import com.sukaidev.index.ui.adapter.IndexDataConverter
import kotlinx.android.synthetic.main.delegate_index.*
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/26.
 * 主页Fragment.
 */
class IndexDelegate : BaseMvpDelegate<IndexPresenter>(), IndexView {

    private lateinit var mAdapter: MultipleRecyclerAdapter

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
    }

    private fun initRecyclerView() {
        val manager = GridLayoutManager(context, 4)
        mIndexRv.layoutManager = manager
        mIndexRv.addItemDecoration(
            BaseDecoration.create(
                ContextCompat.getColor(
                    context!!,
                    R.color.white
                ), 10
            )
        )
        mAdapter =
            MultipleRecyclerAdapter.create(IndexDataConverter().setJsonData(data))
        mIndexRv.adapter = mAdapter
//        mPresenter.getIndexData()
    }

    override fun onGetIndexDataResult(result: String?) {
        mAdapter =
            MultipleRecyclerAdapter.create(IndexDataConverter().setJsonData(result!!).convert())
        mIndexRv.adapter = mAdapter
    }
//    private fun initBanner() {
//        // 设置图片加载器
//        mIndexBanner.setImageLoader(BannerImageLoader())
//            .setImages(
//                listOf(
//                    HOME_BANNER_ONE,
//                    HOME_BANNER_TWO,
//                    HOME_BANNER_THREE,
//                    HOME_BANNER_FOUR,
//                    HOME_BANNER_FIVE
//                )
//            ) // 设置图片集合
//            .setBannerAnimation(Transformer.Default) // 设置动画
//            .setDelayTime(2000) // 设置轮播时间
//            .setIndicatorGravity(BannerConfig.CENTER) // 设置指示器位置
//            .start()
//    }
//
//    private fun initNews() {
//        mNewsFlipperView.setData(arrayOf("夏日炎炎，第一波福利还有30秒到达战场", "新用户立领1000元优惠卷"))
//    }
//
//    private fun initDiscount() {
//        val manager = LinearLayoutManager(context)
//        manager.orientation = LinearLayoutManager.HORIZONTAL
//        mIndexDiscountRv.layoutManager = manager
//
//        mIndexDiscountRv.adapter = IndexDiscountAdapter(
//            mutableListOf(
//                HOME_DISCOUNT_ONE,
//                HOME_DISCOUNT_TWO,
//                HOME_DISCOUNT_THREE,
//                HOME_DISCOUNT_FOUR,
//                HOME_DISCOUNT_FIVE
//            )
//        )
//    }
//
//    private fun initGoods() {
//        val manager = LinearLayoutManager(context)
//        manager.orientation = LinearLayoutManager.VERTICAL
//        mIndexCardRv.layoutManager = manager
//        mIndexCardRv.adapter = IndexGoodsAdapter(MAP)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        mIndexBanner.startAutoPlay()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        mIndexBanner.stopAutoPlay()
//    }
}