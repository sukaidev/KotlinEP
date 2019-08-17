package com.sukaidev.goods.ui.activity

import android.os.Bundle
import android.view.Gravity
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.google.android.material.tabs.TabLayout
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ui.activity.BaseActivity
import com.sukaidev.common.utils.AppPrefsUtils
import com.sukaidev.goods.R
import com.sukaidev.goods.common.GoodsConstant
import com.sukaidev.goods.event.AddCartEvent
import com.sukaidev.goods.event.UpdateCartSizeEvent
import com.sukaidev.goods.ui.adapter.GoodsDetailVpAdapter
import com.sukaidev.provider.common.afterLogin
import com.sukaidev.provider.common.isLogin
import com.sukaidev.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_goods_detail.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import q.rorbin.badgeview.QBadgeView

/**
 * Created by sukaidev on 2019/08/16.
 * 商品详情页面.
 */
class GoodsDetailActivity : BaseActivity() {

    private lateinit var mCartBadge: QBadgeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)

        initView()
        initObserve()
        loadCartSize()
    }

    private fun initView() {
        mGoodsDetailTab.tabMode = TabLayout.MODE_FIXED
        mGoodsDetailVp.adapter = GoodsDetailVpAdapter(supportFragmentManager, this)
        mGoodsDetailTab.setupWithViewPager(mGoodsDetailVp)

        mAddCartBtn.onClick {
            afterLogin {
                Bus.send(AddCartEvent())
            }
        }

        mEnterCartTv.onClick {
            startActivity<CartActivity>()
        }

        mLeftIv.onClick {
            finish()
        }

        mCartBadge = QBadgeView(this)
    }

    private fun initObserve() {
        // 订阅AddCartEvent事件
        Bus.observe<UpdateCartSizeEvent>()
            .subscribe {
                setCartBadge()
            }.registerInBus(this)
    }

    private fun setCartBadge() {
        mCartBadge.badgeGravity = Gravity.END or Gravity.TOP
        mCartBadge.setGravityOffset(22f, -2f, true)
        mCartBadge.setBadgeTextSize(6f, true)
        mCartBadge.bindTarget(mEnterCartTv).badgeNumber =
            AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE)
    }

    private fun loadCartSize() {
        setCartBadge()
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}