package com.sukaidev.kotlinmall.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import com.sukaidev.core.R
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.core.ui.launcher.ILauncherListener
import com.sukaidev.core.utils.timer.BaseTimeTask
import com.sukaidev.core.utils.timer.ITimerListener
import kotlinx.android.synthetic.main.delegate_launcher.*
import java.util.*

/**
 * Created by sukaidev on 2019/08/26.
 * 启动页Fragment.
 */
class LauncherDelegate : ProxyDelegate(), ITimerListener {

    private var mTimer: Timer? = null

    private var mCount = 5

    private var mILauncherListener: ILauncherListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ILauncherListener) {
            mILauncherListener = context
        }
    }

    override fun setLayout(): Any {
        return R.layout.delegate_launcher
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initTimer()

        mTvTimer.onClick {
            mTimer?.let {
                mTimer!!.cancel()
                mTimer = null
                mILauncherListener?.onLauncherFinished()
            }
        }
    }

    private fun initTimer() {
        mTimer = Timer()
        mTimer?.schedule(BaseTimeTask(this), 0, 1000)
    }

    @SuppressLint("SetTextI18n")
    override fun onTimer() {
        getProxyActivity().runOnUiThread {
            mTvTimer?.text = "跳过\n${mCount}s"
            mCount--
            if (mCount < 0) {
                mTimer?.let {
                    mTimer!!.cancel()
                    mTimer = null
                    mILauncherListener?.onLauncherFinished()
                }
            }
        }
    }
}