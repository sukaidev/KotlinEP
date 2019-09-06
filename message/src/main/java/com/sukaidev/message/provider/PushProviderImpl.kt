package com.sukaidev.message.provider

import android.content.Context
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.facade.annotation.Route
import com.sukaidev.core.common.RouterPath
import com.sukaidev.core.provider.IPushProvider

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
@Route(path = RouterPath.Message.PATH_MESSAGE_PUSH)
class PushProviderImpl : IPushProvider {

    private var mContext: Context? = null

    override fun init(context: Context?) {
        mContext = context
    }

    override fun getPushId(): String {
        return JPushInterface.getRegistrationID(mContext)
    }
}
