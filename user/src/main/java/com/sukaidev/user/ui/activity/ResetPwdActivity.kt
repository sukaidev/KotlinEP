package com.sukaidev.user.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.sukaidev.common.ext.enable
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ui.activity.BaseMvpActivity
import com.sukaidev.user.R
import com.sukaidev.user.injection.component.DaggerUserComponent
import com.sukaidev.user.injection.module.UserModule
import com.sukaidev.user.presenter.ResetPwdPresenter
import com.sukaidev.user.presenter.view.IResetPwdView
import kotlinx.android.synthetic.main.activity_reset_pwd.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/12.
 * 重设密码界面.
 */
class ResetPwdActivity : BaseMvpActivity<ResetPwdPresenter>(), IResetPwdView,
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

    override fun setLayout(): Int {
        return R.layout.activity_reset_pwd
    }

    override fun onBindView(savedInstanceState: Bundle?) {
        mConfirmBtn.enable(mPwdEt) { isBtnEnable() }
        mConfirmBtn.enable(mPwdConfirmEt) { isBtnEnable() }
        mConfirmBtn.onClick(this)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onResetPwdResult(result: String) {
        toast(result)
        // 回到登录界面并清顶
        startActivity(intentFor<LoginActivity>().singleTop().clearTop())
    }

    override fun onClick(v: View) {
        when (v) {
            mConfirmBtn -> {
                if (mPwdEt.text.toString() != mPwdConfirmEt.text.toString()) {
                    toast("密码不一致")
                    return
                }
                mPresenter.resetPwd(intent.getStringExtra("mobile"), mPwdEt.text.toString())
            }
        }
    }

    private fun isBtnEnable(): Boolean {
        return mPwdEt.text.isNullOrEmpty().not()
                && mPwdConfirmEt.text.isNullOrEmpty().not()
    }
}