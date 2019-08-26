package com.sukaidev.core.ui.activities

import android.os.Bundle
import com.sukaidev.core.ui.delegates.FastSupportDelegate
import com.sukaidev.core.ui.delegates.LauncherDelegate
import com.sukaidev.core.ui.launcher.ILauncherListener

/**
 * Created by sukaidev on 2019/08/26.
 *
 */
class ExampleActivity : ProxyActivity(), ILauncherListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }

    /**
     * 设置根Fragment
     * 此处为启动页
     */
    override fun setRootDelegate(): FastSupportDelegate {
        return LauncherDelegate()
    }

    override fun onLauncherFinished() {
        startWithPop(BottomDelegate())
    }
}