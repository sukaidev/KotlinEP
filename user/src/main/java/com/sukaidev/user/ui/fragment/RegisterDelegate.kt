package com.sukaidev.user.ui.fragment

import android.os.Bundle
import android.view.View
import com.sukaidev.core.ext.enable
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.user.R
import com.sukaidev.user.injection.component.DaggerUserComponent
import com.sukaidev.user.injection.module.UserModule
import com.sukaidev.user.presenter.RegisterPresenter
import com.sukaidev.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.delegate_register.*
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/30.
 * 注册页面.
 */
class RegisterDelegate : BaseMvpDelegate<RegisterPresenter>(), RegisterView, View.OnClickListener {

    override fun injectComponent() {
        DaggerUserComponent
            .builder()
            .activityComponent(mActivityComponent)
            .userModule(UserModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Any {
        return R.layout.delegate_register
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mRegisterBtn.enable(mMobileEt) { isBtnEnable() }
        mRegisterBtn.enable(mVerifyCodeEt) { isBtnEnable() }
        mRegisterBtn.enable(mPwdEt) { isBtnEnable() }
        mRegisterBtn.enable(mPwdConfirmEt) { isBtnEnable() }
        mRegisterBtn.onClick(this)
        mVerifyCodeBtn.onClick(this)
        mHeaderBar.getLeftIv().onClick(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            mHeaderBar.getLeftIv() -> {
                context?.toast("点击了")
                pop()
            }
            mVerifyCodeBtn -> {
                mVerifyCodeBtn.requestSendVerifyNumber()
                context?.toast("发送验证码成功")
            }
            mRegisterBtn ->
                mPresenter.register(
                    mMobileEt.text.toString(),
                    mPwdEt.text.toString(),
                    mVerifyCodeEt.text.toString()
                )
        }
    }

    /**
     * 检测登录按钮是否可用.
     */
    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not()
                && mVerifyCodeEt.text.isNullOrEmpty().not()
                && mPwdEt.text.isNullOrEmpty().not()
                && mPwdConfirmEt.text.isNullOrEmpty().not()
    }

    override fun onRegisterResult(result: String) {
        context?.toast("注册成功！")
        pop()
    }
}