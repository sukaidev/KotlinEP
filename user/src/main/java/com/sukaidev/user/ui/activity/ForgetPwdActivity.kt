package com.sukaidev.user.ui.activity

import android.os.Bundle
import android.view.View
import com.sukaidev.common.ext.enable
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ui.activity.BaseMvpActivity
import com.sukaidev.user.R
import com.sukaidev.user.injection.component.DaggerUserComponent
import com.sukaidev.user.injection.module.UserModule
import com.sukaidev.user.presenter.ForgetPwdPresenter
import com.sukaidev.user.presenter.view.ForgetPwdView
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/12.
 * 忘记密码界面.
 */
class ForgetPwdActivity : BaseMvpActivity<ForgetPwdPresenter>(), ForgetPwdView,
    View.OnClickListener {

    override fun injectComponent() {
        DaggerUserComponent
            .builder()
            .activityComponent(activityComponent)
            .userModule(UserModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Int {
        return R.layout.activity_forget_pwd
    }

    override fun onBindView(savedInstanceState: Bundle?) {
        mNextBtn.enable(mMobileEt) { isBtnEnable() }
        mNextBtn.enable(mVerifyCodeEt) { isBtnEnable() }
        mVerifyCodeBtn.onClick(this)
        mNextBtn.onClick(this)
    }

    override fun onForgetPwdResult(result: String) {
        startActivity<ResetPwdActivity>("mobile" to mMobileEt.text.toString())
    }

    override fun onClick(v: View) {
        when (v) {
            mVerifyCodeBtn -> {
                mVerifyCodeBtn.requestSendVerifyNumber()
                toast("发送验证码成功")
            }
            mNextBtn -> {
                mPresenter.forgetPwd(mMobileEt.text.toString(), mVerifyCodeEt.text.toString())
            }
        }
    }

    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not()
                && mVerifyCodeEt.text.isNullOrEmpty().not()
    }
}