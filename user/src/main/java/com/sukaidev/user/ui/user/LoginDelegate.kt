package com.sukaidev.user.ui.user

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.eightbitlab.rxbus.Bus
import com.sukaidev.core.event.LoginSuccessEvent
import com.sukaidev.core.ext.enable
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.provider.IPushProvider
import com.sukaidev.provider.router.RouterPath
import com.sukaidev.user.R
import com.sukaidev.user.data.protocol.UserInfo
import com.sukaidev.user.injection.component.DaggerUserComponent
import com.sukaidev.user.injection.module.UserModule
import com.sukaidev.user.presenter.LoginPresenter
import com.sukaidev.user.presenter.view.LoginView
import com.sukaidev.user.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.delegate_login.*
import org.jetbrains.anko.toast


/**
 * Created by sukaidev on 2019/08/30.
 * 登录页面.
 */
@Route(path = RouterPath.User.PATH_LOGIN)
class LoginDelegate : BaseMvpDelegate<LoginPresenter>(), LoginView, View.OnClickListener {

    @Autowired(name = RouterPath.Message.PATH_MESSAGE_PUSH)
    @JvmField
    var mIPushProvider: IPushProvider? = null

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
        return R.layout.delegate_login
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mLoginBtn.enable(mMobileEt) { isBtnEnable() }
        mLoginBtn.enable(mPwdEt) { isBtnEnable() }
        mLoginBtn.onClick(this)
        mHeaderBar.getLeftIv().onClick(this)
        mHeaderBar.getRightTv().onClick(this)
        mForgetPwdTv.onClick(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            mHeaderBar.getLeftIv() -> {
                pop()
            }
            mHeaderBar.getRightTv() -> {
                start(RegisterDelegate())
            }
            mLoginBtn -> {
                mPresenter.login(
                    mMobileEt.text.toString(),
                    mPwdEt.text.toString(),
                    mIPushProvider?.getPushId() ?: ""
                )
            }
            mForgetPwdTv -> {
                start(ForgetPwdDelegate())
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

    override fun onLoginResult(result: UserInfo) {
        context?.toast("登录成功")
        UserPrefsUtils.putUserInfo(result)
        Bus.send(LoginSuccessEvent())
        supportDelegate.pop()
    }
}