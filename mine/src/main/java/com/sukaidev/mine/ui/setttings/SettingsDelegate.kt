package com.sukaidev.mine.ui.setttings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.eightbitlab.rxbus.Bus
import com.sukaidev.core.event.LogoutEvent
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ext.setVisible
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.provider.common.isLogin
import com.sukaidev.mine.R
import com.sukaidev.mine.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.delegate_settings.*

/**
 * Created by sukaidev on 2019/08/30.
 * 设置页面.
 */
class SettingsDelegate : ProxyDelegate() {

    override fun setLayout(): Any {
        return R.layout.delegate_settings
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

        mHeaderBar.getLeftIv().onClick {
            supportDelegate.pop()
        }

        mFeedBackTv.onClick {
            val uri = Uri.parse("https://github.com/sukaidev/KotlinMall/issues/new")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        mAboutTv.onClick {
            start(AboutDelegate())
        }

        if (!isLogin()) {
            mLogoutBtn.setVisible(false)
        } else {
            mLogoutBtn.setVisible(true)
            mLogoutBtn.onClick {
                UserPrefsUtils.putUserInfo(null)
                Bus.send(LogoutEvent())
                supportDelegate.pop()
            }
        }
    }
}