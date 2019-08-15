package com.sukaidev.kotlinmall.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sukaidev.common.ui.fragment.BaseFragment
import com.sukaidev.common.widget.BannerImageLoader
import com.sukaidev.kotlinmall.R
import com.sukaidev.kotlinmall.common.*
import com.sukaidev.kotlinmall.ui.adapter.HomeDiscountAdapter
import com.sukaidev.kotlinmall.ui.adapter.TopicAdapter
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_home.*
import me.crosswall.lib.coverflow.CoverFlow


/**
 * Created by sukaidev on 2019/08/13.
 * 主页.
 */
class HomeFragment : BaseFragment() {

    override fun setLayout(): Any {
        return R.layout.fragment_home
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initBanner()
        initNews()
        initDiscount()
        initTopic()
    }

    private fun initBanner() {
        // 设置图片加载器
        mHomeBanner.setImageLoader(BannerImageLoader())
            .setImages(
                listOf(
                    HOME_BANNER_ONE,
                    HOME_BANNER_TWO,
                    HOME_BANNER_THREE,
                    HOME_BANNER_FOUR
                )
            ) // 设置图片集合
            .setBannerAnimation(Transformer.Accordion) // 设置动画
            .setDelayTime(2000) // 设置轮播时间
            .setIndicatorGravity(BannerConfig.CENTER) // 设置指示器位置
            .start()
    }

    private fun initNews() {
        mNewsFlipperView.setData(arrayOf("夏日炎炎，第一波福利还有30秒到达战场", "新用户立领1000元优惠卷"))
    }

    private fun initDiscount() {
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        mHomeDiscountRv.layoutManager = manager

        val discountAdapter = HomeDiscountAdapter(activity as Context)
        mHomeDiscountRv.adapter = discountAdapter
        discountAdapter.setData(
            mutableListOf(
                HOME_DISCOUNT_ONE, HOME_DISCOUNT_TWO,
                HOME_DISCOUNT_THREE, HOME_DISCOUNT_FOUR, HOME_DISCOUNT_FIVE
            )
        )
    }

    private fun initTopic() {
        // 话题
        mTopicPager.adapter = TopicAdapter(
            context!!, listOf(
                HOME_TOPIC_ONE, HOME_TOPIC_TWO,
                HOME_TOPIC_THREE, HOME_TOPIC_FOUR, HOME_TOPIC_FIVE
            )
        )
        mTopicPager.currentItem = 1
        mTopicPager.offscreenPageLimit = 5

        CoverFlow.Builder()
            .with(mTopicPager)
            .scale(0.3f)
            .pagerMargin(-30.0f)
            .spaceSize(0.0f)
            .build()
    }

    override fun onStart() {
        super.onStart()
        //开始轮播
        mHomeBanner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        //结束轮播
        mHomeBanner.stopAutoPlay()
    }
}