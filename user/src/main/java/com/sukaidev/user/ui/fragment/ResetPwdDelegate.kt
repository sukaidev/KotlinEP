package com.sukaidev.user.ui.fragment

import android.os.Bundle
import android.view.View
import com.sukaidev.core.ext.enable
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.user.R
import com.sukaidev.user.injection.component.DaggerUserComponent
import com.sukaidev.user.injection.module.UserModule
import com.sukaidev.user.presenter.ResetPwdPresenter
import com.sukaidev.user.presenter.view.ResetPwdView
import kotlinx.android.synthetic.main.delegate_reset_pwd.*
import kotlinx.android.synthetic.main.delegate_reset_pwd.mHeaderBar
import kotlinx.android.synthetic.main.delegate_reset_pwd.mPwdConfirmEt
import kotlinx.android.synthetic.main.delegate_reset_pwd.mPwdEt
import me.yokeyword.fragmentation.ISupportFragment
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/30.
 * 重设密码界面.
 */
class ResetPwdDelegate : BaseMvpDelegate<ResetPwdPresenter>(), ResetPwdView, View.OnClickListener {

    var mobile: String? = null

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
        return R.layout.delegate_reset_pwd
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

        mobile = arguments?.getString("mobile")

        mConfirmBtn.enable(mPwdEt) { isBtnEnable() }
        mConfirmBtn.enable(mPwdConfirmEt) { isBtnEnable() }
        mConfirmBtn.onClick(this)
        mHeaderBar.getLeftIv().onClick(this)
    }

    override fun onResetPwdResult(result: String) {
        context?.toast(result)
        // 回到登录页面，采用SingleTask模式
        start(LoginDelegate(), ISupportFragment.SINGLETASK)
    }

    override fun onClick(v: View?) {
        when (v) {
            mHeaderBar.getLeftIv() -> {
                pop()
            }
            mConfirmBtn -> {
                if (mPwdEt.text.toString() != mPwdConfirmEt.text.toString()) {
                    context?.toast("密码不一致")
                    return
                }
                mobile?.let { mPresenter.resetPwd(it, mPwdEt.text.toString()) }
            }
        }
    }

    private fun isBtnEnable(): Boolean {
        return mPwdEt.text.isNullOrEmpty().not()
                && mPwdConfirmEt.text.isNullOrEmpty().not() && mPwdEt.text?.length == mPwdConfirmEt.text?.length
    }
}