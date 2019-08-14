package com.sukaidev.kotlinmall.ui.activity

import android.os.Bundle
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ui.activity.BaseActivity
import com.sukaidev.kotlinmall.R
import com.sukaidev.user.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * Created by sukaidev on 2019/08/14.
 *
 */
class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        mLogoutBtn.onClick {
            UserPrefsUtils.putUserInfo(null)
            finish()
        }
    }
}