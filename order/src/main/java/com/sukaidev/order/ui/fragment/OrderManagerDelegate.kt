package com.sukaidev.order.ui.fragment

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.core.ui.delegates.SupportDelegate
import com.sukaidev.order.R
import com.sukaidev.order.common.OrderConstant
import com.sukaidev.order.common.OrderStatus
import com.sukaidev.order.ui.adapter.OrderVpAdapter
import kotlinx.android.synthetic.main.delegate_order_manager.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by sukaidev on 2019/08/23.
 * 订单管理页面.
 */
class OrderManagerDelegate : ProxyDelegate() {

    // OrderDelegate
    private var mFragmentList : ArrayList<OrderDelegate>? = ArrayList()
    private val orderAll: OrderDelegate by lazy { initFragment(OrderStatus.ORDER_ALL) }
    private val orderWaitPay: OrderDelegate by lazy { initFragment(OrderStatus.ORDER_WAIT_PAY) }
    private val orderWaitConfirm: OrderDelegate by lazy { initFragment(OrderStatus.ORDER_WAIT_CONFIRM) }
    private val orderCompleted: OrderDelegate by lazy { initFragment(OrderStatus.ORDER_COMPLETED) }
    private val orderCanceled: OrderDelegate by lazy { initFragment(OrderStatus.ORDER_CANCELED) }

    override fun setLayout(): Any {
        return R.layout.delegate_order_manager
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initView()
    }

    private fun initView() {

        mHeaderBar.getLeftIv().onClick {
            supportDelegate.pop()
        }

        mFragmentList?.let {
            it.add(orderAll)
            it.add(orderWaitPay)
            it.add(orderWaitConfirm)
            it.add(orderCompleted)
            it.add(orderCanceled)
        }

        mOrderTab.tabMode = TabLayout.MODE_FIXED
        mOrderVp.adapter = OrderVpAdapter(arrayOf("全部", "待付款", "待收货", "已完成", "已取消"), mFragmentList, fragmentManager!!)
        mOrderTab.setupWithViewPager(mOrderVp)

        // 根据订单状态来设置当前页面
        mOrderVp.currentItem = if (arguments == null) {
            OrderStatus.ORDER_ALL
        } else {
            arguments!!.getInt(OrderConstant.KEY_ORDER_STATUS)
        }
    }

    private fun initFragment(status: Int): OrderDelegate {
        val args = Bundle()
        args.putInt(OrderConstant.KEY_ORDER_STATUS, status)
        val delegate = OrderDelegate()
        delegate.arguments = args
        return delegate
    }

    override fun onDestroy() {
        super.onDestroy()
        mFragmentList?.clear()
        mFragmentList = null
    }
}