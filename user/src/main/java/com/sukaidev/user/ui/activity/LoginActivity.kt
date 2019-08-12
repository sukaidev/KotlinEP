package com.sukaidev.user.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import com.sukaidev.common.ext.enable
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ui.activity.BaseMvpActivity
import com.sukaidev.user.R
import com.sukaidev.user.data.protocol.UserInfo
import com.sukaidev.user.injection.component.DaggerUserComponent
import com.sukaidev.user.injection.module.UserModule
import com.sukaidev.user.presenter.LoginPresenter
import com.sukaidev.user.presenter.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/12.
 * 登录.
 */
class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView, View.OnClickListener {

    override fun onLoginResult(result: UserInfo) {
        toast("登录成功")
        startActivity<UserInfoActivity>()
    }

    override fun setLayout(): Int {
        return R.layout.activity_login
    }

    override fun onBindView(savedInstanceState: Bundle?) {
        mLoginBtn.enable(mMobileEt) { isBtnEnable() }
        mLoginBtn.enable(mPwdEt) { isBtnEnable() }
        mLoginBtn.onClick(this)
        mHeaderBar.getRightTv().onClick(this)
        mForgetPwdTv.onClick(this)
    }

    override fun injectComponent() {
        DaggerUserComponent
            .builder()
            .activityComponent(activityComponent)
            .userModule(UserModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onClick(v: View) {
        when (v) {
            mLoginBtn -> {
                mPresenter.login(mMobileEt.text.toString(), mPwdEt.text.toString(), "")
            }
            mHeaderBar.getRightTv() -> {
                startActivity<RegisterActivity>()
            }
            mForgetPwdTv -> {
                startActivity<ForgetPwdActivity>()
            }
        }
    }

    /**
     * 检测登录按钮是否可用.
     */
    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not()
                && mPwdEt.text.isNullOrEmpty().not()
    }
}