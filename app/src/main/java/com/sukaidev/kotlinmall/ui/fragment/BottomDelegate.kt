package com.sukaidev.kotlinmall.ui.fragment

import android.os.Bundle
import android.view.View
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.sukaidev.core.common.GoodsConstant
import com.sukaidev.core.event.*
import com.sukaidev.core.ext.startWithNewBundle
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.core.utils.AppPrefsUtils
import com.sukaidev.goods.event.UpdateBottomCartSizeEvent
import com.sukaidev.goods.event.UpdateDetailCartSizeEvent
import com.sukaidev.goods.ui.cart.ShopCartDelegate
import com.sukaidev.goods.ui.category.CategoryDelegate
import com.sukaidev.goods.ui.goods.GoodsDetailDelegate
import com.sukaidev.goods.ui.goods.GoodsListDelegate
import com.sukaidev.index.ui.fragment.IndexDelegate
import com.sukaidev.kotlinmall.R
import com.sukaidev.message.ui.fragment.MessageDelegate
import com.sukaidev.mine.ui.user.LoginDelegate
import com.sukaidev.mine.ui.user.MineDelegate
import com.sukaidev.order.common.OrderConstant
import com.sukaidev.order.ui.fragment.OrderConfirmDelegate
import com.sukaidev.order.ui.fragment.OrderDetailDelegate
import com.sukaidev.order.ui.fragment.OrderManagerDelegate
import com.sukaidev.order.ui.fragment.ShipAddressDelegate
import com.sukaidev.pay.ui.fragment.CashRegisterDelegate
import com.sukaidev.core.common.ProviderConstant
import kotlinx.android.synthetic.main.delegate_bottom.*
import kotlin.collections.ArrayList

/**
 * Created by sukaidev on 2019/08/26.
 *
 */
class BottomDelegate : ProxyDelegate() {

    // 记录当前显示Fragment坐标
    private var mCurrentDelegate = 0

    private val mDelegates = ArrayList<ProxyDelegate>()
    private val mHomeFragment by lazy { IndexDelegate() }
    private val mCategoryFragment by lazy { CategoryDelegate() }
    private val mMsgFragment by lazy { MessageDelegate() }
    private val mCartFragment by lazy { ShopCartDelegate() }
    private val mUserFragment by lazy { MineDelegate() }


    override fun setLayout(): Any {
        return R.layout.delegate_bottom
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initFragment()
        initBottomNav()
        initObserve()
    }

    /**
     * 初始化Fragment
     */
    private fun initFragment() {
/*        val manager = fragmentManager?.beginTransaction()
        manager?.add(R.id.mContainer, mHomeFragment)
        manager?.add(R.id.mContainer, mCategoryFragment)
        manager?.add(R.id.mContainer, mMsgFragment)
        manager?.add(R.id.mContainer, mCartFragment)
        manager?.add(R.id.mContainer, mUserFragment)
        manager?.commit()*/

        mDelegates.add(mHomeFragment)
        mDelegates.add(mCategoryFragment)
        mDelegates.add(mMsgFragment)
        mDelegates.add(mCartFragment)
        mDelegates.add(mUserFragment)

        loadMultipleRootFragment(
            R.id.mContainer,
            0,
            mHomeFragment,
            mCategoryFragment,
            mMsgFragment,
            mCartFragment,
            mUserFragment
        )

    }

    private fun initBottomNav() {

        mBottomNavBar.checkCartBadge(AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE))
        mBottomNavBar.checkMsgBadge(AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME) != "")

        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {

            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabSelected(position: Int) {
                if (mCurrentDelegate != position) {
                    showHideFragment(mDelegates[position], mDelegates[mCurrentDelegate])
                    mCurrentDelegate = position
                }
            }
        })
    }

    private fun initObserve() {
        // 订阅登录事件
        Bus.observe<LoginEvent>()
            .subscribe {
               supportDelegate.start(LoginDelegate())
            }
            .registerInBus(this)
        // 订阅搜索商品事件
        Bus.observe<SearchGoodsEvent>()
            .subscribe {
                supportDelegate.startWithNewBundle<GoodsListDelegate>(
                    GoodsConstant.KEY_SEARCH_GOODS_TYPE to GoodsConstant.SEARCH_GOODS_TYPE_KEYWORD,
                    GoodsConstant.KEY_GOODS_KEYWORD to it.keyWord
                )
            }
            .registerInBus(this)
        // 订阅首页商品点击事件
        Bus.observe<GoodsClickedEvent>()
            .subscribe {
                supportDelegate.startWithNewBundle<GoodsDetailDelegate>(GoodsConstant.KEY_GOODS_ID to it.goodsId)
            }
            .registerInBus(this)
        // 订阅更新购物车数据事件
        Bus.observe<UpdateDetailCartSizeEvent>()
            .subscribe {
                mBottomNavBar.checkCartBadge(AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE))
            }
            .registerInBus(this)
        // 订阅更新购物车数据事件
        Bus.observe<UpdateBottomCartSizeEvent>()
            .subscribe {
                mBottomNavBar.checkCartBadge(AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE))
            }
            .registerInBus(this)
        // 订阅提交购物车事件
        Bus.observe<SubmitCartEvent>()
            .subscribe {
                supportDelegate.startWithNewBundle<OrderConfirmDelegate>(ProviderConstant.KEY_ORDER_ID to it.orderId)
            }
            .registerInBus(this)
        // 转到地址管理页面
        Bus.observe<ToAddressDelegateEvent>()
            .subscribe {
                supportDelegate.start(ShipAddressDelegate())
            }
            .registerInBus(this)
        // 转到订单页面
        Bus.observe<ToOrderManagerDelegateEvent>()
            .subscribe {
                supportDelegate.startWithNewBundle<OrderManagerDelegate>(OrderConstant.KEY_ORDER_STATUS to it.orderStatus)
            }
            .registerInBus(this)
        // 转到支付页面
        Bus.observe<OrderPayEvent>()
            .subscribe {
                supportDelegate.startWithNewBundle<CashRegisterDelegate>(
                    ProviderConstant.KEY_ORDER_ID to it.orderId,
                    ProviderConstant.KEY_ORDER_PRICE to it.totalPrice
                )
            }
        // 转到订单详情页
        Bus.observe<ToOrderDetailEvent>()
            .subscribe {
                supportDelegate.startWithNewBundle<OrderDetailDelegate>(ProviderConstant.KEY_ORDER_ID to it.orderId)
            }
            .registerInBus(this)
        // 消息Badge
        Bus.observe<MessageBadgeEvent>()
            .subscribe {
                mBottomNavBar.checkMsgBadge(it.isVisible)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

/*    */
    /**
     * 切换fragment显示
     * 注意要将原来的fragment隐藏
     *//*
    private fun changeFragment(position: Int) {
        val manager = fragmentManager?.beginTransaction()
        for (fragment in mDelegates) {
            manager?.hide(fragment)
        }
        manager?.show(mDelegates[position])
        manager?.commit()
    }*/
}