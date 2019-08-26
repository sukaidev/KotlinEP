package com.sukaidev.core.utils.timer

import java.util.*

/**
 * Created by sukaidev on 2019/08/26.
 *
 */
class BaseTimeTask(private val iTimerListener: ITimerListener?) : TimerTask() {

    override fun run() {
        iTimerListener?.onTimer()
    }
}