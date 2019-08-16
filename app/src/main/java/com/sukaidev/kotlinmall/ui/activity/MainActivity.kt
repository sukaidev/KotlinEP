package com.sukaidev.kotlinmall.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.sukaidev.common.ui.activity.BaseActivity
import com.sukaidev.common.utils.AppPrefsUtils
import com.sukaidev.goods.common.GoodsConstant
import com.sukaidev.goods.event.UpdateCartSizeEvent
import com.sukaidev.goods.ui.fragment.CartFragment
import com.sukaidev.goods.ui.fragment.CategoryFragment
import com.sukaidev.kotlinmall.R
import com.sukaidev.kotlinmall.ui.fragment.HomeFragment
import com.sukaidev.kotlinmall.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Created by sukaidev on 2019/7/30.
 * MainActivity.
 */
class MainActivity : BaseActivity() {

    private val mStack = Stack<Fragment>()
    private val mHomeFragment by lazy { HomeFragment() }
    private val mCategoryFragment by lazy { CategoryFragment() }
    private val mMsgFragment by lazy { HomeFragment() }
    private val mCartFragment by lazy { CartFragment() }
    private val mMineFragment by lazy { MineFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment()
        initBottomNav()
        changeFragment(0)
        loadCartSize()
        initObserve()
    }


    private fun initFragment() {
        val manager = supportFragmentManager.beginTransaction()
        manager.add(R.id.mContainer, mHomeFragment)
        manager.add(R.id.mContainer, mCategoryFragment)
        manager.add(R.id.mContainer, mMsgFragment)
        manager.add(R.id.mContainer, mCartFragment)
        manager.add(R.id.mContainer, mMineFragment)
        manager.commit()

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
        val manager = supportFragmentManager.beginTransaction()
        for (fragment in mStack) {
            manager.hide(fragment)
        }
        manager.show(mStack[position])
        manager.commit()
    }

    private fun initObserve() {
        // 订阅AddCartEvent事件
        Bus.observe<UpdateCartSizeEvent>()
            .subscribe {
                loadCartSize()
            }.registerInBus(this)
    }

    private fun loadCartSize() {
        mBottomNavBar.checkCartBadge(AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE))
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}
