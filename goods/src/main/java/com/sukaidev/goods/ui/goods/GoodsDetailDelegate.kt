package com.sukaidev.goods.ui.goods

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import cn.sharesdk.onekeyshare.OnekeyShare
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.google.android.material.appbar.AppBarLayout
import com.sukaidev.core.common.GoodsConstant
import com.sukaidev.core.common.afterLogin
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ext.startWithNewBundle
import com.sukaidev.core.ui.banner.BannerImageLoader
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.core.utils.AppPrefsUtils
import com.sukaidev.goods.R
import com.sukaidev.goods.data.protocol.Goods
import com.sukaidev.goods.event.AddCartEvent
import com.sukaidev.goods.event.UpdateDetailCartSizeEvent
import com.sukaidev.goods.injection.component.DaggerGoodsComponent
import com.sukaidev.goods.injection.module.GoodsModule
import com.sukaidev.goods.presenter.GoodsDetailPresenter
import com.sukaidev.goods.presenter.view.GoodsDetailView
import com.sukaidev.goods.ui.cart.ShopCartDelegate
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.delegate_goods_detail.*
import kotlinx.android.synthetic.main.layout_goods_detail_bottom.*
import q.rorbin.badgeview.QBadgeView

/**
 * Created by sukaidev on 2019/08/16.
 * 商品详情页面.
 */
class GoodsDetailDelegate : BaseMvpDelegate<GoodsDetailPresenter>(), AppBarLayout.OnOffsetChangedListener, GoodsDetailView {

    // 购物车数量显示控件
    private lateinit var mCartBadge: QBadgeView

    // 商品ID
    private var goodsId: Int? = -1

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
        return R.layout.delegate_goods_detail
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mCollapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context!!, R.color.app_main))
        mAppBarLayout.addOnOffsetChangedListener(this)

        initView()
        initObserve()
        loadCartSize()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        arguments?.let {
            goodsId = arguments!!.getInt(GoodsConstant.KEY_GOODS_ID, -1)
        }
        loadData()
    }

    /**
     * 初始化视图
     */
    private fun initView() {

        mAddCartBtn.onClick {
            afterLogin {
                Bus.send(AddCartEvent())
            }
        }

        mEnterCartTv.onClick {
            // 开启独立购物车界面
            supportDelegate.startWithNewBundle<ShopCartDelegate>(GoodsConstant.ARG_STARTED_FROM_DETAIL to true)
        }

        mLeftIv.onClick {
            supportDelegate.pop()
        }

        mShareTv.onClick {
            showShare()
        }

        mCartBadge = QBadgeView(context)
    }

    /**
     * 加载商品信息数据
     */
    private fun loadData() {
        mPresenter.getGoodsDetail(goodsId!!)
    }

    /**
     * 加载购物车数量
     */
    private fun loadCartSize() {
        setCartBadge()
    }

    /**
     * 监听购物车数量变化
     */
    private fun initObserve() {
        Bus.observe<UpdateDetailCartSizeEvent>()
            .subscribe {
                setCartBadge()
            }.registerInBus(this)
    }

    /**
     * 设置购物车数量标签
     */
    private fun setCartBadge() {
        mCartBadge.badgeGravity = Gravity.END or Gravity.TOP
        mCartBadge.setGravityOffset(22f, -2f, true)
        mCartBadge.setBadgeTextSize(6f, true)
        mCartBadge.bindTarget(mEnterCartTv).badgeNumber = AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE)
    }

    override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
/*        if (activity?.actionBar?.height!! - p0?.height!! == p1) {// 如果AppBar完全折叠
            // do something
            context?.toast("闭合")
        }*/
    }

    override fun onGetGoodsDetail(result: Goods) {

        mGoodsBanner.setImageLoader(BannerImageLoader())
            .setImages(result.goodsBanner.split(","))
            .setBannerAnimation(Transformer.Default)
            .setDelayTime(3000)
            //设置指示器位置（当banner模式中有指示器时）
            .setIndicatorGravity(BannerConfig.RIGHT)
            .start()

        val goodsDetailOne = result.goodsDetailOne
        val goodsDetailTwo = result.goodsDetailTwo
        val goodsInfoDelegate = GoodsInfoDelegate.create(result)
        supportDelegate.loadRootFragment(R.id.goods_info_container, goodsInfoDelegate)
        supportDelegate.loadRootFragment(R.id.goods_image_container, GoodsImageDelegate.create(arrayOf(goodsDetailOne, goodsDetailTwo)))
    }

    // 显示分享信息
    private fun showShare(){
        val oks = OnekeyShare()
        // title标题
        oks.setTitle(getString(R.string.ssdk_oks_share))
        // titleUrl
        oks.setTitleUrl("http://sharesdk.cn")
        // 分享文本
        oks.text = "这件商品不错，快来看看吧~"
        // imagePath是图片的本地路径，确保SDcard下面存在此张图片
//        oks.setImagePath("/sdcard/test.jpg")
        // url在微信、Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn")
        // 启动分享GUI
        oks.show(context)

    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}