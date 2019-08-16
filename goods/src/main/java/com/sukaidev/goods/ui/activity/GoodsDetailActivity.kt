package com.sukaidev.goods.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.google.android.material.tabs.TabLayout
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ui.activity.BaseActivity
import com.sukaidev.goods.R
import com.sukaidev.goods.event.AddCartEvent
import com.sukaidev.goods.ui.adapter.GoodsDetailVpAdapter
import com.sukaidev.provider.common.afterLogin
import com.sukaidev.provider.common.isLogin
import com.sukaidev.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_goods_detail.*
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/16.
 * 商品详情页面.
 */
class GoodsDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)

        initView()
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
    }
}