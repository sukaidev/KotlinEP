package com.sukaidev.kotlinmall.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.sukaidev.core.ui.delegates.BaseDelegate
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.index.ui.fragment.IndexDelegate
import com.sukaidev.kotlinmall.R
import kotlinx.android.synthetic.main.delegate_bottom.*
import me.yokeyword.fragmentation.SupportFragment
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by sukaidev on 2019/08/26.
 *
 */
class BottomDelegate : ProxyDelegate() {

    private val mDelegates = ArrayList<ProxyDelegate>()
    private val mHomeFragment by lazy { IndexDelegate() }
    private val mCategoryFragment by lazy { EmptyDelegate() }
    private val mMsgFragment by lazy { EmptyDelegate() }
    private val mCartFragment by lazy { EmptyDelegate() }
    private val mMineFragment by lazy { EmptyDelegate() }


    override fun setLayout(): Any {
        return R.layout.delegate_bottom
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initFragment()
        initBottomNav()
    }

    private fun initFragment() {
/*        val manager = fragmentManager?.beginTransaction()
        manager?.add(R.id.mContainer, mHomeFragment)
        manager?.add(R.id.mContainer, mCategoryFragment)
        manager?.add(R.id.mContainer, mMsgFragment)
        manager?.add(R.id.mContainer, mCartFragment)
        manager?.add(R.id.mContainer, mMineFragment)
        manager?.commit()*/

        mDelegates.add(mHomeFragment)
        mDelegates.add(mCategoryFragment)
        mDelegates.add(mMsgFragment)
        mDelegates.add(mCartFragment)
        mDelegates.add(mMineFragment)

        loadMultipleRootFragment(
            R.id.mContainer,
            0,
            mHomeFragment,
            mCategoryFragment,
            mMsgFragment,
            mCartFragment,
            mMineFragment
        )

    }

    private fun initBottomNav() {
        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {

            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabSelected(position: Int) {
                changeFragment(position)
            }
        })
    }

    private fun changeFragment(position: Int) {
        val manager = fragmentManager?.beginTransaction()
        for (fragment in mDelegates) {
            manager?.hide(fragment)
        }
        manager?.show(mDelegates[position])
        manager?.commit()
    }
}