package com.sukaidev.kotlinmall.ui.fragment

import android.os.Bundle
import android.view.View
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.goods.ui.category.CategoryDelegate
import com.sukaidev.index.ui.fragment.IndexDelegate
import com.sukaidev.kotlinmall.R
import com.sukaidev.mine.ui.user.MineDelegate
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
    private val mMsgFragment by lazy { EmptyDelegate() }
    private val mCartFragment by lazy { EmptyDelegate() }
    private val mUserFragment by lazy { MineDelegate() }


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