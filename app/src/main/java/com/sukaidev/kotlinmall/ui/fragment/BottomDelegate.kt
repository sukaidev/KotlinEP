package com.sukaidev.kotlinmall.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.sukaidev.core.ui.delegates.BaseDelegate
import com.sukaidev.index.ui.fragment.IndexDelegate
import com.sukaidev.kotlinmall.R
import kotlinx.android.synthetic.main.delegate_bottom.*
import java.util.*

/**
 * Created by sukaidev on 2019/08/26.
 *
 */
class BottomDelegate : BaseDelegate() {

    private val mStack = Stack<Fragment>()
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
        changeFragment(0)
    }

    private fun initFragment() {
        val manager = fragmentManager?.beginTransaction()
        manager?.add(R.id.mContainer, mHomeFragment)
        manager?.add(R.id.mContainer, mCategoryFragment)
        manager?.add(R.id.mContainer, mMsgFragment)
        manager?.add(R.id.mContainer, mCartFragment)
        manager?.add(R.id.mContainer, mMineFragment)
        manager?.commit()

        mStack.add(mHomeFragment)
        mStack.add(mCategoryFragment)
        mStack.add(mMsgFragment)
        mStack.add(mCartFragment)
        mStack.add(mMineFragment)
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
        for (fragment in mStack) {
            manager?.hide(fragment)
        }
        manager?.show(mStack[position])
        manager?.commit()
    }
}