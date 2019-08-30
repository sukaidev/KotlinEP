package com.sukaidev.user.ui.user

import android.os.Bundle
import android.view.View
import com.sukaidev.core.ext.enable
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ext.startWithNewBundle
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.user.R
import com.sukaidev.user.injection.component.DaggerUserComponent
import com.sukaidev.user.injection.module.UserModule
import com.sukaidev.user.presenter.ForgetPwdPresenter
import com.sukaidev.user.presenter.view.ForgetPwdView
import kotlinx.android.synthetic.main.delegate_forget_pwd.*
import kotlinx.android.synthetic.main.delegate_forget_pwd.mHeaderBar
import kotlinx.android.synthetic.main.delegate_forget_pwd.mMobileEt
import kotlinx.android.synthetic.main.delegate_forget_pwd.mVerifyCodeBtn
import kotlinx.android.synthetic.main.delegate_forget_pwd.mVerifyCodeEt
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/30.
 * 忘记密码界面.
 */
class ForgetPwdDelegate : BaseMvpDelegate<ForgetPwdPresenter>(), ForgetPwdView,
    View.OnClickListener {

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
        return R.layout.delegate_forget_pwd
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mNextBtn.enable(mMobileEt) { isBtnEnable() }
        mNextBtn.enable(mVerifyCodeEt) { isBtnEnable() }
        mVerifyCodeBtn.onClick(this)
        mNextBtn.onClick(this)
        mHeaderBar.getLeftIv().onClick(this)
    }

    override fun onClick(v: View) {
        when (v) {
            mHeaderBar.getLeftIv() -> pop()
            mVerifyCodeBtn -> {
                mVerifyCodeBtn.requestSendVerifyNumber()
                context?.toast("发送验证码成功")
            }
            mNextBtn -> {
                mPresenter.forgetPwd(mMobileEt.text.toString(), mVerifyCodeEt.text.toString())
            }
        }
    }

    override fun onForgetPwdResult(result: String) {
/*        val args = Bundle()
        args.putString("mobile",mMobileEt.text.toString())
        val delegate = ResetPwdDelegate()
        delegate.arguments = args
        start(delegate)
        context?.toast("result")*/
        supportDelegate.startWithNewBundle(
            ResetPwdDelegate(),
            "mobile" to mMobileEt.text.toString()
        )
    }

    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not()
                && mVerifyCodeEt.text.isNullOrEmpty().not()
    }
}