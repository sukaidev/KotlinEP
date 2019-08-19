package com.sukaidev.kotlinmall.ui.fragment

import android.os.Bundle
import android.view.View
import com.sukaidev.common.ext.loadUrl
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ui.fragment.BaseFragment
import com.sukaidev.common.utils.AppPrefsUtils
import com.sukaidev.kotlinmall.R
import com.sukaidev.kotlinmall.ui.activity.SettingActivity
import com.sukaidev.order.common.OrderConstant
import com.sukaidev.order.common.OrderStatus
import com.sukaidev.order.ui.activity.OrderActivity
import com.sukaidev.order.ui.activity.ShipAddressActivity
import com.sukaidev.provider.common.ProviderConstant
import com.sukaidev.provider.common.ProviderConstant.Companion.KEY_SP_USER_ICON
import com.sukaidev.provider.common.afterLogin
import com.sukaidev.provider.common.isLogin
import com.sukaidev.user.ui.activity.LoginActivity
import com.sukaidev.user.ui.activity.UserInfoActivity
import kotlinx.android.synthetic.main.fragment_mine.*
import org.jetbrains.anko.startActivity

/**
 * Created by sukaidev on 2019/08/14.
 * "我的"页面.
 */
class MineFragment : BaseFragment(), View.OnClickListener {

    override fun setLayout(): Any {
        return R.layout.fragment_mine
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun initView() {
        mUserIconIv.onClick(this)
        mUserNameTv.onClick(this)
        mWaitPayOrderTv.onClick(this)
        mWaitConfirmOrderTv.onClick(this)
        mCompleteOrderTv.onClick(this)
        mAllOrderTv.onClick(this)
        mAddressTv.onClick(this)
        mSettingTv.onClick(this)
    }

    private fun loadData() {
        if (isLogin()) {
            val userIcon = AppPrefsUtils.getString(KEY_SP_USER_ICON)
            if (userIcon!!.isNotEmpty()) {
                mUserIconIv.loadUrl(userIcon)
            }
            mUserNameTv.text = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        } else {
            mUserIconIv.setImageResource(R.drawable.icon_default_user)
            mUserNameTv.text = getString(R.string.un_login_text)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            mUserIconIv, mUserNameTv -> {
                if (isLogin()) {
                    context?.startActivity<UserInfoActivity>()
                } else {
                    context?.startActivity<LoginActivity>()
                }
            }
            mWaitPayOrderTv -> {
                context?.startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_PAY)
            }
            mWaitConfirmOrderTv -> {
                context?.startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_CONFIRM)
            }
            mCompleteOrderTv -> {
                context?.startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_COMPLETED)
            }
            mAllOrderTv -> {
                afterLogin {
                    context?.startActivity<OrderActivity>()
                }
            }
            mAddressTv -> afterLogin { context?.startActivity<ShipAddressActivity>() }
            mSettingTv -> context?.startActivity<SettingActivity>()
        }
    }
}