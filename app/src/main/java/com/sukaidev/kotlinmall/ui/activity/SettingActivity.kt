package com.sukaidev.kotlinmall.ui.activity

import android.os.Bundle
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ext.setVisible
import com.sukaidev.common.ui.activity.BaseActivity
import com.sukaidev.kotlinmall.R
import com.sukaidev.provider.common.isLogin
import com.sukaidev.user.ui.activity.LoginActivity
import com.sukaidev.user.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.startActivity

/**
 * Created by sukaidev on 2019/08/14.
 *
 */
class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        if (!isLogin()) {
            mLogoutBtn.setVisible(false)
        } else {
            mLogoutBtn.setVisible(true)
            mLogoutBtn.onClick {
                UserPrefsUtils.putUserInfo(null)
                finish()
            }
        }
    }
}