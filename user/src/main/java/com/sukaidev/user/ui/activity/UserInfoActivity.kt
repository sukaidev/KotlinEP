package com.sukaidev.user.ui.activity

import android.os.Bundle
import android.view.View
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.sukaidev.common.common.AppManager
import com.sukaidev.common.ext.enable
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ui.activity.BaseMvpActivity
import com.sukaidev.common.widget.VerifyButton
import com.sukaidev.user.R
import com.sukaidev.user.injection.component.DaggerUserComponent
import com.sukaidev.user.injection.module.UserModule
import com.sukaidev.user.presenter.RegisterPresenter
import com.sukaidev.user.presenter.UserInfoPresenter
import com.sukaidev.user.presenter.view.RegisterView
import com.sukaidev.user.presenter.view.UserInfoView
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_user_info.*
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/10.
 * 用户信息.
 */
class UserInfoActivity : BaseMvpActivity<UserInfoPresenter>(), UserInfoView, View.OnClickListener {

    override fun injectComponent() {
    }

    override fun setLayout(): Int {
        return R.layout.activity_user_info
    }

    override fun onBindView(savedInstanceState: Bundle?) {
        mUserIconView.onClick {
            showAlertView()
        }
    }

    private fun showAlertView() {
        AlertView(
            "设置头像",
            "从下列途径中选择一张照片",
            "取消",
            null,
            arrayOf("拍照", "相册"),
            this,
            AlertView.Style.ActionSheet,
            OnItemClickListener { _, position ->
                when (position) {
                    0 -> toast("拍照")
                    10 -> toast("相册")
                }
            }).show()
    }

    override fun onClick(v: View?) {
    }

}