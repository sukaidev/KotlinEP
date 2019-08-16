package com.sukaidev.goods.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.goods.event.GoodsDetailImageEvent
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ui.fragment.BaseMvpFragment
import com.sukaidev.common.utils.MoneyConverter
import com.sukaidev.common.widget.BannerImageLoader
import com.sukaidev.goods.R
import com.sukaidev.goods.common.GoodsConstant
import com.sukaidev.goods.data.protocol.Goods
import com.sukaidev.goods.event.SkuChangedEvent
import com.sukaidev.goods.injection.component.DaggerGoodsComponent
import com.sukaidev.goods.injection.module.GoodsModule
import com.sukaidev.goods.presenter.GoodsDetailPresenter
import com.sukaidev.goods.presenter.view.IGoodsDetailView
import com.sukaidev.goods.ui.activity.GoodsDetailActivity
import com.sukaidev.goods.widget.GoodsSkuView
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_goods_detail_tab_one.*
import org.jetbrains.anko.contentView

/**
 * Created by sukaidev on 2019/08/16.
 * 商品详情页第一个Tab.
 */
class GoodsDetailTabOneFragment : BaseMvpFragment<GoodsDetailPresenter>(), IGoodsDetailView {

    private lateinit var mSkuPop: GoodsSkuView
    private lateinit var mAnimationStart: Animation
    private lateinit var mAnimationEnd: Animation

    override fun injectComponent() {
        DaggerGoodsComponent
            .builder()
            .activityComponent(activityComponent)
            .goodsModule(GoodsModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Any {
        return R.layout.fragment_goods_detail_tab_one
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initView()
        initAnim()
        initSkuView()
        loadData()
        initObserver()
    }

    private fun initView() {
        mGoodsDetailBanner.setImageLoader(BannerImageLoader())
        mGoodsDetailBanner.setBannerAnimation(Transformer.Accordion)
        mGoodsDetailBanner.setDelayTime(2000)
        // 设置指示器（当banner模式中有指示器时）
        mGoodsDetailBanner.setIndicatorGravity(BannerConfig.RIGHT)

        mSkuView.onClick {
            mSkuPop.showAtLocation(
                (activity as GoodsDetailActivity).contentView,
                Gravity.BOTTOM and Gravity.CENTER_HORIZONTAL, 0, 0
            )
            (activity as GoodsDetailActivity).contentView!!.startAnimation(mAnimationStart)
        }
    }

    private fun initSkuView() {
        mSkuPop = GoodsSkuView(activity as GoodsDetailActivity)
        mSkuPop.setOnDismissListener {
            (activity as GoodsDetailActivity).contentView!!.startAnimation(mAnimationEnd)

        }
    }

    private fun loadData() {
        mPresenter.getGoodsDetail(activity!!.intent.getIntExtra(GoodsConstant.KEY_GOODS_ID, -1))
    }

    override fun onGetGoodsDetail(result: Goods) {
        mGoodsDetailBanner.setImages(result.goodsBanner.split(","))
        mGoodsDetailBanner.start()

        mGoodsDescTv.text = result.goodsDesc
        mGoodsDescTv.text = MoneyConverter.changeF2YWithUnit(result.goodsDefaultPrice)
        mSkuSelectedTv.text = result.goodsDefaultSku

        Bus.send(GoodsDetailImageEvent(result.goodsDetailOne, result.goodsDetailTwo))

        loadPopData(result)
    }

    private fun loadPopData(result: Goods) {
        mSkuPop.setGoodsIcon(result.goodsDefaultIcon)
        mSkuPop.setGoodsCode(result.goodsCode)
        mSkuPop.setGoodsPrice(result.goodsDefaultPrice)

        mSkuPop.setSkuData(result.goodsSku)
    }

    @SuppressLint("SetTextI18n")
    private fun initObserver() {
        Bus.observe<SkuChangedEvent>()
            .subscribe {
                mSkuSelectedTv.text =
                    mSkuPop.getSelectSku() + GoodsConstant.SKU_SEPARATOR + mSkuPop.getSelectCount() + "件"
            }.registerInBus(this)
    }

    private fun initAnim() {
        mAnimationStart = ScaleAnimation(
            1f, 0.95f, 1f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        mAnimationStart.duration = 500
        mAnimationStart.fillAfter = true

        mAnimationEnd = ScaleAnimation(
            0.95f, 1f, 0.95f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        mAnimationEnd.duration = 500
        mAnimationEnd.fillAfter = true
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}