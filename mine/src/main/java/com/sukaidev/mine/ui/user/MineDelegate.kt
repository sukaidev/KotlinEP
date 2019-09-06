package com.sukaidev.mine.ui.user

import android.os.Bundle
import android.view.View
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.sukaidev.core.common.afterLogin
import com.sukaidev.core.event.LoginSuccessEvent
import com.sukaidev.core.event.LogoutEvent
import com.sukaidev.core.event.ToAddressDelegateEvent
import com.sukaidev.core.event.ToOrderManagerDelegateEvent
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ext.loadUrl
import com.sukaidev.core.ui.delegates.BaseDelegate
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.core.utils.AppPrefsUtils
import com.sukaidev.core.common.ProviderConstant
import com.sukaidev.core.common.ProviderConstant.Companion.KEY_SP_USER_ICON
import com.sukaidev.core.common.isLogin
import com.sukaidev.mine.R
import com.sukaidev.mine.ui.setttings.SettingsDelegate
import kotlinx.android.synthetic.main.delegate_user_info.mUserIconIv
import kotlinx.android.synthetic.main.delegate_mine.*
import org.jetbrains.anko.toast

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
        initObserve()
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

    private fun initObserve() {

        Bus.observe<LoginSuccessEvent>()
            .subscribe{
                val userIcon = AppPrefsUtils.getString(KEY_SP_USER_ICON)
                if (userIcon!!.isNotEmpty()) {
                    mUserIconIv.loadUrl(userIcon)
                }
                mUserNameTv.text = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
            }
            .registerInBus(this)

        Bus.observe<LogoutEvent>()
            .subscribe{
                mUserIconIv.setImageResource(R.drawable.icon_default_user)
                mUserNameTv.text = getString(R.string.un_login_text)
            }
            .registerInBus(this)
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
                    getParentDelegate<ProxyDelegate>().supportDelegate.start(UserInfoDelegate())
                } else {
                    getParentDelegate<ProxyDelegate>().supportDelegate.start(LoginDelegate())
                }
            }
            mWaitPayOrderTv -> {
                Bus.send(ToOrderManagerDelegateEvent(1))
//                context?.startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_PAY)
            }
            mWaitConfirmOrderTv -> {
                Bus.send(ToOrderManagerDelegateEvent(2))
//                context?.startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_CONFIRM)
            }
            mCompleteOrderTv -> {
                Bus.send(ToOrderManagerDelegateEvent(3))
//                context?.startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_COMPLETED)
            }
            mAllOrderTv -> {
                afterLogin {
                    Bus.send(ToOrderManagerDelegateEvent())
//                    context?.startActivity<OrderActivity>()
                }
            }
            mAddressTv -> afterLogin {
                Bus.send(ToAddressDelegateEvent())
            }
            mSettingTv -> getParentDelegate<ProxyDelegate>().supportDelegate.start(SettingsDelegate())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    private val WAIT_TIME = 2000L
    private var TOUCH_TIME: Long = 0

    override fun onBackPressedSupport(): Boolean {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish()
        } else {
            TOUCH_TIME = System.currentTimeMillis()
            context?.toast("再次点击退出")
        }
        return true
    }
}