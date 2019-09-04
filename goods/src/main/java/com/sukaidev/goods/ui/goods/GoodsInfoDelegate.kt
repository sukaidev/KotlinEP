package com.sukaidev.goods.ui.goods

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.sukaidev.core.common.GoodsConstant
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.core.utils.MoneyConverter
import com.sukaidev.goods.R
import com.sukaidev.goods.data.protocol.Goods
import com.sukaidev.goods.event.AddCartEvent
import com.sukaidev.goods.event.SkuChangedEvent
import com.sukaidev.goods.event.UpdateDetailCartSizeEvent
import com.sukaidev.goods.injection.component.DaggerCartComponent
import com.sukaidev.goods.injection.module.CartModule
import com.sukaidev.goods.presenter.GoodsInfoPresenter
import com.sukaidev.goods.presenter.view.GoodsInfoView
import com.sukaidev.goods.widget.GoodsSkuView
import kotlinx.android.synthetic.main.delegate_goods_info.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/09/02.
 * 商品基本信息.
 */
class GoodsInfoDelegate : BaseMvpDelegate<GoodsInfoPresenter>(), GoodsInfoView {

    private lateinit var mSkuPop: GoodsSkuView
    //SKU弹层出场动画
    private lateinit var mAnimationStart: Animation
    //SKU弹层退场动画
    private lateinit var mAnimationEnd: Animation

    // 当前商品对象
    private lateinit var mCurGoods: Goods

    companion object {
        // 通过传参构建GoodsInfoDelegate对象
        fun create(goods: Goods): GoodsInfoDelegate {
            val args = Bundle()
            args.putParcelable(GoodsConstant.ARG_GOODS_OBJECT, goods)
            val delegate = GoodsInfoDelegate()
            delegate.arguments = args
            return delegate
        }
    }

    override fun injectComponent() {
        DaggerCartComponent
            .builder()
            .activityComponent(mActivityComponent)
            .cartModule(CartModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Any {
        return R.layout.delegate_goods_info
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        arguments?.let {
            mCurGoods = it.getParcelable(GoodsConstant.ARG_GOODS_OBJECT)!!
        }

        initView()
        initAnim()
        initSkuPop()
        initObserve()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        loadPopData(mCurGoods)
    }

    /**
     * 初始化视图
     */
    private fun initView() {
/*        mGoodsBanner.setImageLoader(BannerImageLoader())
            .setImages(mCurGoods.goodsBanner.split(","))
            .setBannerAnimation(Transformer.Accordion)
            .setDelayTime(2000)
            //设置指示器位置（当banner模式中有指示器时）
            .setIndicatorGravity(BannerConfig.RIGHT)
            .start()*/

        mGoodsTitleTv.text = mCurGoods.goodsTitle
        mGoodsDescTv.text = mCurGoods.goodsDesc
        mGoodsPriceTv.text = MoneyConverter.changeF2YWithUnit(mCurGoods.goodsDefaultPrice)
        mSkuSelectedTv.text = mCurGoods.goodsDefaultSku

        //sku弹层
        mSkuView.onClick {
            mSkuPop.showAtLocation(
                activity?.contentView, Gravity.BOTTOM and Gravity.CENTER_HORIZONTAL,
                0, 0
            )
            activity?.contentView?.startAnimation(mAnimationStart)
        }
    }

    /**
     * 初始化缩放动画
     */
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

    /**
     * 初始化Sku弹层
     */
    private fun initSkuPop() {
        mSkuPop = GoodsSkuView(activity!!)
        mSkuPop.setOnDismissListener {
            activity?.contentView?.startAnimation(mAnimationEnd)
        }
    }

    /**
     * 监听SKU变化及加入购物车事件
     */
    @SuppressLint("SetTextI18n")
    private fun initObserve() {
        Bus.observe<SkuChangedEvent>()
            .subscribe {
                mSkuSelectedTv.text = mSkuPop.getSelectSku() + GoodsConstant.SKU_SEPARATOR + mSkuPop.getSelectCount() + "件"
            }.registerInBus(this)

        Bus.observe<AddCartEvent>()
            .subscribe {
                addCart()
            }.registerInBus(this)
    }

    /**
     * 加载SKU数据
     */
    private fun loadPopData(result: Goods) {
        mSkuPop.setGoodsIcon(result.goodsDefaultIcon)
        mSkuPop.setGoodsTitle(result.goodsTitle)
        mSkuPop.setGoodsCode(result.goodsCode)
        mSkuPop.setGoodsPrice(result.goodsDefaultPrice)
        mSkuPop.setSkuData(result.goodsSku)
    }

    /**
     * 加入购物车
     */
    private fun addCart() {
        mPresenter.addCart(
            mCurGoods.id,
            mCurGoods.goodsTitle,
            mCurGoods.goodsDesc,
            mCurGoods.goodsDefaultIcon,
            mCurGoods.goodsDefaultPrice,
            mSkuPop.getSelectCount(),
            mSkuPop.getSelectSku()
        )
    }

    override fun onAddCartResult(result: Int) {
        context?.toast("加入购物车成功！")
        Bus.send(UpdateDetailCartSizeEvent())
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}