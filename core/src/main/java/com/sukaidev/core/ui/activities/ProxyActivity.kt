package com.sukaidev.core.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.annotation.Nullable
import androidx.appcompat.widget.ContentFrameLayout
import com.sukaidev.core.R
import com.sukaidev.core.ui.delegates.FastSupportDelegate
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportActivity
import me.yokeyword.fragmentation.SupportActivityDelegate
import me.yokeyword.fragmentation.anim.FragmentAnimator
import me.yokeyword.fragmentation.ISupportFragment

/**
 * Created by sukaidev on 2019/08/25.
 * 自定义SupportActivity.
 */
abstract class ProxyActivity : RxAppCompatActivity(), ISupportActivity {

    private val mDelegate: SupportActivityDelegate = SupportActivityDelegate(this)

    abstract fun setRootDelegate(): FastSupportDelegate

    override fun getSupportDelegate(): SupportActivityDelegate {
        return mDelegate
    }

    /**
     * Perform some extra transactions.
     * 额外的事务：自定义Tag，添加SharedElement动画，操作非回退栈Fragment
     */
    override fun extraTransaction(): ExtraTransaction {
        return mDelegate.extraTransaction()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDelegate.onCreate(savedInstanceState)
        initContainer(savedInstanceState)
    }

    @SuppressLint("RestrictedApi")
    private fun initContainer(@Nullable savedInstanceState: Bundle?) {
        val container = ContentFrameLayout(this)
        container.id = R.id.delegate_container
        setContentView(container)
        if (savedInstanceState == null) {
            loadRootFragment(R.id.delegate_container, setRootDelegate())
        }

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDelegate.onPostCreate(savedInstanceState)
    }

    override fun onDestroy() {
        mDelegate.onDestroy()
        super.onDestroy()
        System.gc()
        System.runFinalization()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev)
    }

    /**
     * 设置Fragment内的全局动画
     */
    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator?) {
        mDelegate.fragmentAnimator = fragmentAnimator
    }

    /**
     * 获取设置的全局动画
     */
    override fun getFragmentAnimator(): FragmentAnimator {
        return mDelegate.fragmentAnimator
    }

    /**
     * Activity回退栈内Fragment的数量 小于等于1 时,finish activity
     */
    override fun onBackPressedSupport() {
        mDelegate.onBackPressedSupport()
    }

    /**
     * 构建Fragment转场动画
     * 如果是在Activity内实现,则构建的是Activity内所有Fragment的转场动画,
     * 如果是在Fragment内实现,则构建的是该Fragment的转场动画,此时优先级 大于 Activity的onCreateFragmentAnimator()
     */
    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return mDelegate.onCreateFragmentAnimator()
    }

    /**
     * 前面的事务全部执行后 执行该action
     */
    override fun post(runnable: Runnable?) {
        mDelegate.post(runnable)
    }

    private fun loadRootFragment(containerId: Int, toFragment: ISupportFragment) {
        mDelegate.loadRootFragment(containerId, toFragment)
    }

    protected fun startWithPop(toFragment: ISupportFragment){
        mDelegate.startWithPop(toFragment)
    }

}