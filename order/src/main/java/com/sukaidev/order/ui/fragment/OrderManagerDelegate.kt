package com.sukaidev.order.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.order.R
import com.sukaidev.order.common.OrderConstant
import com.sukaidev.order.common.OrderStatus
import com.sukaidev.order.ui.adapter.OrderVpAdapter
import kotlinx.android.synthetic.main.delegate_order_manager.*

/**
 * Created by sukaidev on 2019/08/23.
 * 订单管理页面.
 */
class OrderManagerDelegate : ProxyDelegate() {

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

        mOrderTab.tabMode = TabLayout.MODE_FIXED
        mOrderVp.adapter = OrderVpAdapter(arrayOf("全部", "待付款", "待收货", "已完成","已取消"), fragmentManager!!)
        mOrderTab.setupWithViewPager(mOrderVp)

        // 根据订单状态来设置当前页面
        mOrderVp.currentItem = if (arguments == null) {
            OrderStatus.ORDER_ALL
        } else {
            arguments!!.getInt(OrderConstant.KEY_ORDER_STATUS)
        }

    }
}