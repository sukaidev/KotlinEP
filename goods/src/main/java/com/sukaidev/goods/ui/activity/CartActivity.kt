package com.sukaidev.goods.ui.activity

import android.os.Bundle
import com.sukaidev.common.ui.activity.BaseActivity
import com.sukaidev.goods.R
import com.sukaidev.goods.ui.fragment.CartFragment

/**
 * Created by sukaidev on 2019/08/17.
 * 独立购物车界面.
 */
class CartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val fragment: CartFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_cart) as CartFragment
        fragment.setBackVisible(true)
    }

}