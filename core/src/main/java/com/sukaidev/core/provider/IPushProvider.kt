package com.sukaidev.core.provider

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
interface IPushProvider : IProvider {

    fun getPushId(): String
}