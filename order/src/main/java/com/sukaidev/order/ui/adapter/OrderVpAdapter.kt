package com.sukaidev.order.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.order.ui.fragment.OrderDelegate

/**
 * Created by sukaidev on 2019/09/04.
 *
 */
class OrderVpAdapter(private val titles: Array<String>, private var fragments: ArrayList<OrderDelegate>, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val mCount: Int = fragments.size

    override fun getItem(position: Int): Fragment {
/*        val fragment = OrderDelegate()
        val bundle = Bundle()
        bundle.putInt(OrderConstant.KEY_ORDER_STATUS, position)
        fragment.arguments = bundle*/
        return fragments[position]
    }

    override fun getCount(): Int {
        return mCount
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getItemId(position: Int): Long {
        // 这个地方的重写非常关键，super中是返回position，
        // 如果不重写，还是会继续找到FragmentManager中缓存的Fragment
        return fragments[position].hashCode().toLong()
    }

    override fun getItemPosition(`object`: Any): Int {
        // 不在数据集合里面的话，return POSITION_NONE，进行item的重建
        val index = fragments.indexOf(`object`)
        return if (index == -1) {
            PagerAdapter.POSITION_NONE
        } else {
            fragments.indexOf(`object`)
        }
    }
}