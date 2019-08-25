package com.sukaidev.kotlinmall.ui.activity

import android.os.Bundle
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ext.setVisible
import com.sukaidev.common.ui.activity.BaseActivity
import com.sukaidev.kotlinmall.R
import com.sukaidev.provider.common.isLogin
import com.sukaidev.user.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.startActivity
import android.content.Intent
import android.net.Uri


/**
 * Created by sukaidev on 2019/08/14.
 *
 */
class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        mFeedBackTv.onClick {
            val uri = Uri.parse("https://github.com/sukaidev/KotlinMall/issues/new")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        mAboutTv.onClick {
            startActivity<AboutActivity>()
        }

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