package com.sukaidev.provider.common

import com.sukaidev.common.common.BaseConstant
import com.sukaidev.common.utils.AppPrefsUtils

/**
 * Created by sukaidev on 2019/08/14.
 *
 */
fun isLogined(): Boolean {
    return AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN)!!.isNotEmpty()
}