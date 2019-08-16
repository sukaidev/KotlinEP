package com.sukaidev.provider.common

import com.alibaba.android.arouter.launcher.ARouter
import com.sukaidev.common.common.BaseApplication
import com.sukaidev.common.common.BaseConstant
import com.sukaidev.common.utils.AppPrefsUtils
import com.sukaidev.provider.router.RouterPath
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
        BaseApplication.context.toast("请先登录")
        ARouter.getInstance().build(RouterPath.User.PATH_LOGIN).navigation()
    }
}