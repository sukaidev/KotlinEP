package com.sukaidev.core.common

import com.eightbitlab.rxbus.Bus
import com.sukaidev.core.common.BaseApplication
import com.sukaidev.core.common.BaseConstant
import com.sukaidev.core.event.LoginEvent
import com.sukaidev.core.utils.AppPrefsUtils
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/14.
 *
 */
fun isLogin(): Boolean {
    return AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN)!!.isNotEmpty()
}

fun afterLogin(method: () -> Unit) {
    if (isLogin()) {
        method()
    } else {
        BaseApplication.instance.toast("请先登录")
//        ARouter.getInstance().build(RouterPath.User.PATH_LOGIN).navigation()
        Bus.send(LoginEvent())
    }
}