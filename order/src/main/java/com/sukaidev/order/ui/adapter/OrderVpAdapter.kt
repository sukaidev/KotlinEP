package com.sukaidev.order.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sukaidev.order.common.OrderConstant
import com.sukaidev.order.ui.fragment.OrderFragment

/**
 * Created by sukaidev on 2019/08/18.
 * 订单Tab对应ViewPager适配器.
 */
class OrderVpAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val titles = arrayOf("全部", "待付款", "待收货", "已完成", "已取消")

    override fun getItem(position: Int): Fragment {
        val fragment = OrderFragment()
        val bundle = Bundle()
        bundle.putInt(OrderConstant.KEY_ORDER_STATUS, position)
        fragment.arguments = bundle
        return fragment

    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}