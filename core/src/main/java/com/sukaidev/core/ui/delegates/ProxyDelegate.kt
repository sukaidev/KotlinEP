package com.sukaidev.core.ui.delegates

/**
 * Created by sukaidev on 2019/08/26.
 *
 */
abstract class ProxyDelegate : BaseDelegate() {

    fun <T : BaseDelegate> getParentDelegate(): T {
        return parentFragment as T
    }
}