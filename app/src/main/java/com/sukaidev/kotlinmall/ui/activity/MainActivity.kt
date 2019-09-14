package com.sukaidev.kotlinmall.ui.activity

import android.content.pm.PackageManager
import com.eightbitlab.rxbus.Bus
import com.sukaidev.core.common.PermissionConstant
import com.sukaidev.core.event.RequestPermissionSuccess
import com.sukaidev.core.ui.activities.ProxyActivity
import com.sukaidev.core.ui.delegates.BaseDelegate
import com.sukaidev.kotlinmall.ui.fragment.BottomDelegate
import com.sukaidev.kotlinmall.ui.fragment.LauncherDelegate
import com.sukaidev.core.ui.launcher.ILauncherListener
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/26.
 * 主Activity，也是此工程唯一Activity.
 */
class MainActivity : ProxyActivity(), ILauncherListener {

    /**
     * 设置根Fragment
     * 此处为启动页
     */
    override fun setRootDelegate(): BaseDelegate {
        return LauncherDelegate()
    }

    override fun onLauncherFinished() {
        startWithPop(BottomDelegate())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PermissionConstant.QRCODE_SCANNER_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Bus.send(RequestPermissionSuccess(PermissionConstant.QRCODE_SCANNER_PERMISSION_REQUEST_CODE))
                } else {
                    toast("权限被拒绝")
                }
            }
            PermissionConstant.USER_PROFILE_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Bus.send(RequestPermissionSuccess(PermissionConstant.USER_PROFILE_PERMISSION_REQUEST_CODE))
                } else {
                    toast("权限被拒绝")
                }
            }
        }
    }
}