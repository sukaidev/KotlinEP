package com.sukaidev.mine.ui.user

import android.os.Bundle
import android.view.View
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ext.loadUrl
import com.sukaidev.core.ui.delegates.BaseDelegate
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.core.utils.AppPrefsUtils
import com.sukaidev.provider.common.ProviderConstant
import com.sukaidev.provider.common.ProviderConstant.Companion.KEY_SP_USER_ICON
import com.sukaidev.provider.common.isLogin
import com.sukaidev.mine.R
import com.sukaidev.mine.ui.setttings.SettingsDelegate
import kotlinx.android.synthetic.main.delegate_user_info.mUserIconIv
import kotlinx.android.synthetic.main.delegate_mine.*

/**
 * Created by sukaidev on 2019/08/14.
 * 用户页面.
 */
class MineDelegate : ProxyDelegate(), View.OnClickListener {

    override fun setLayout(): Any {
        return R.layout.delegate_mine
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
                    getParentDelegate<BaseDelegate>().supportDelegate.start(UserInfoDelegate())
                } else {
                    getParentDelegate<BaseDelegate>().supportDelegate.start(LoginDelegate())
                }
            }
            mWaitPayOrderTv -> {
//                context?.startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_PAY)
            }
            mWaitConfirmOrderTv -> {
//                context?.startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_CONFIRM)
            }
            mCompleteOrderTv -> {
//                context?.startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_COMPLETED)
            }
            mAllOrderTv -> {
/*                afterLogin {
                    context?.startActivity<OrderActivity>()
                }*/
            }
/*            mAddressTv -> afterLogin { context?.startActivity<ShipAddressActivity>() }*/
            mSettingTv -> getParentDelegate<BaseDelegate>().supportDelegate.start(SettingsDelegate())
        }
    }

/*    private val WAIT_TIME = 2000L
    private var TOUCH_TIME: Long = 0

    override fun onBackPressedSupport(): Boolean {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish()
        } else {
            TOUCH_TIME = System.currentTimeMillis()
            context?.toast("双击退出")
        }
        return true
    }*/
}