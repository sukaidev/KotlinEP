package com.sukaidev.user.ui.activity

import android.os.Bundle
import android.view.View
import com.sukaidev.common.common.AppManager
import com.sukaidev.common.ext.enable
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ui.activity.BaseMvpActivity
import com.sukaidev.common.widget.VerifyButton
import com.sukaidev.user.R
import com.sukaidev.user.injection.component.DaggerUserComponent
import com.sukaidev.user.injection.module.UserModule
import com.sukaidev.user.presenter.RegisterPresenter
import com.sukaidev.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

/**
 * Created by sukai on 2019/08/10.
 * 注册Activity.
 */
class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView, View.OnClickListener {

    private var pressTime: Long = 0

    /**
     * 注册回调
     */
    override fun onRegisterResult(result: String) {
        toast("注册成功")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
    }

    private fun initView() {
        mRegisterBtn.enable(mMobileEt) { isBtnEnable() }
        mRegisterBtn.enable(mVerifyCodeEt) { isBtnEnable() }
        mRegisterBtn.enable(mPwdEt) { isBtnEnable() }
        mRegisterBtn.enable(mPwdConfirmEt) { isBtnEnable() }
        mRegisterBtn.onClick(this)
        mVerifyCodeBtn.onClick(this)
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
            mVerifyCodeBtn -> {
                mVerifyCodeBtn.requestSendVerifyNumber()
                toast("发送验证码成功")
            }
            mRegisterBtn ->
                mPresenter.register(mMobileEt.text.toString(), mPwdEt.text.toString(), mVerifyCodeEt.text.toString())
        }
    }

    /**
     * 检测注册按钮是否可用.
     */
    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not()
                && mVerifyCodeEt.text.isNullOrEmpty().not()
                && mPwdEt.text.isNullOrEmpty().not()
                && mPwdConfirmEt.text.isNullOrEmpty().not()
    }

    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if (time - pressTime > 2000) {
            toast("再按一次退出程序")
            pressTime = time
        } else {
            AppManager.INSTANCE.exitApp(this)
        }
    }
}