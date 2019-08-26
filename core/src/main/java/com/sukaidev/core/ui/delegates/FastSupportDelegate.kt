package com.sukaidev.core.ui.delegates

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.trello.rxlifecycle.components.support.RxFragment
import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragmentDelegate
import me.yokeyword.fragmentation.SwipeBackLayout
import me.yokeyword.fragmentation.anim.FragmentAnimator
import me.yokeyword.fragmentation_swipeback.core.ISwipeBackFragment
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.annotation.Nullable
import me.yokeyword.fragmentation_swipeback.core.SwipeBackFragmentDelegate

/**
 * Created by sukaidev on 2019/08/25.
 * 自定义SupportFragment.
 */
open class FastSupportDelegate : RxFragment(), ISupportFragment, ISwipeBackFragment {

    private val mSupportDelegate = SupportFragmentDelegate(this)

    private val mSwipeBackDelegate = SwipeBackFragmentDelegate(this)

    protected lateinit var _mActivity: FragmentActivity

    override fun getSupportDelegate(): SupportFragmentDelegate {
        return mSupportDelegate
    }

    /**
     * Perform some extra transactions.
     * 额外的事务：自定义Tag，添加SharedElement动画，操作非回退栈Fragment
     */
    override fun extraTransaction(): ExtraTransaction {
        return mSupportDelegate.extraTransaction()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mSupportDelegate.onAttach(activity!!)
        _mActivity = mSupportDelegate.activity
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSupportDelegate.onCreate(savedInstanceState)
        mSwipeBackDelegate.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return attachToSwipeBack(super.onCreateView(inflater, container, savedInstanceState))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSwipeBackDelegate.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
        return mSupportDelegate.onCreateAnimation(transit, enter, nextAnim)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSupportDelegate.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mSupportDelegate.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mSupportDelegate.onResume()
    }

    override fun onPause() {
        super.onPause()
        mSupportDelegate.onPause()
    }

    override fun onDestroyView() {
        mSupportDelegate.onDestroyView()
        mSwipeBackDelegate.onDestroyView()
        super.onDestroyView()
    }

    override fun onDestroy() {
        mSupportDelegate.onDestroy()
        super.onDestroy()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mSupportDelegate.onHiddenChanged(hidden)
        mSwipeBackDelegate.onHiddenChanged(hidden)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mSupportDelegate.setUserVisibleHint(isVisibleToUser)
    }

    /**
     * Causes the Runnable r to be added to the action queue.
     *
     *
     * The runnable will be run after all the previous action has been run.
     *
     *
     * 前面的事务全部执行后 执行该Action
     *
     */
    override fun enqueueAction(runnable: Runnable) {
        mSupportDelegate.post(runnable)
    }

    /**
     * Causes the Runnable r to be added to the action queue.
     *
     *
     * The runnable will be run after all the previous action has been run.
     *
     *
     * 前面的事务全部执行后 执行该Action
     */
    override fun post(runnable: Runnable) {
        mSupportDelegate.post(runnable)
    }

    /**
     * Called when the enter-animation end.
     * 入栈动画 结束时,回调
     */
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        mSupportDelegate.onEnterAnimationEnd(savedInstanceState)
    }


    /**
     * Lazy initial，Called when fragment is first called.
     *
     *
     * 同级下的 懒加载 ＋ ViewPager下的懒加载  的结合回调方法
     */
    override fun onLazyInitView(@Nullable savedInstanceState: Bundle?) {
        mSupportDelegate.onLazyInitView(savedInstanceState)
    }

    /**
     * Called when the fragment is visible.
     * 当Fragment对用户可见时回调
     *
     *
     * Is the combination of  [onHiddenChanged() + onResume()/onPause() + setUserVisibleHint()]
     */
    override fun onSupportVisible() {
        mSupportDelegate.onSupportVisible()
    }

    /**
     * Called when the fragment is invivible.
     *
     *
     * Is the combination of  [onHiddenChanged() + onResume()/onPause() + setUserVisibleHint()]
     */
    override fun onSupportInvisible() {
        mSupportDelegate.onSupportInvisible()
    }

    /**
     * Return true if the fragment has been supportVisible.
     */
    override fun isSupportVisible(): Boolean {
        return mSupportDelegate.isSupportVisible
    }

    /**
     * 设定当前Fragment动画,优先级比在SupportActivity里高
     */
    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return mSupportDelegate.onCreateFragmentAnimator()
    }

    /**
     * 获取设置的全局动画 copy
     *
     * @return FragmentAnimator
     */
    override fun getFragmentAnimator(): FragmentAnimator {
        return mSupportDelegate.fragmentAnimator
    }

    /**
     * 设置Fragment内的全局动画
     */
    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator) {
        mSupportDelegate.fragmentAnimator = fragmentAnimator
    }

    /**
     * 按返回键触发,前提是SupportActivity的onBackPressed()方法能被调用
     *
     * @return false则继续向上传递, true则消费掉该事件
     */
    override fun onBackPressedSupport(): Boolean {
        return mSupportDelegate.onBackPressedSupport()
    }

    /**
     * 类似 [Activity.setResult]
     *
     *
     * Similar to [Activity.setResult]
     *
     * @see .startForResult
     */
    override fun setFragmentResult(resultCode: Int, bundle: Bundle) {
        mSupportDelegate.setFragmentResult(resultCode, bundle)
    }

    /**
     * 类似  [Activity.onActivityResult]
     *
     *
     * Similar to [Activity.onActivityResult]
     *
     * @see .startForResult
     */
    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        mSupportDelegate.onFragmentResult(requestCode, resultCode, data)
    }

    /**
     * 在start(TargetFragment,LaunchMode)时,启动模式为SingleTask/SingleTop, 回调TargetFragment的该方法
     * 类似 [Activity.onNewIntent]
     *
     *
     * Similar to [Activity.onNewIntent]
     *
     * @param args putNewBundle(Bundle newBundle)
     * @see .start
     */
    override fun onNewBundle(args: Bundle) {
        mSupportDelegate.onNewBundle(args)
    }

    /**
     * 添加NewBundle,用于启动模式为SingleTask/SingleTop时
     *
     * @see .start
     */
    override fun putNewBundle(newBundle: Bundle) {
        mSupportDelegate.putNewBundle(newBundle)
    }

    /************************  下面是 ISwipeBackFragment 接口相关实现 **************************/

    override fun getSwipeBackLayout(): SwipeBackLayout {
        return mSwipeBackDelegate.swipeBackLayout
    }

    override fun attachToSwipeBack(view: View?): View {
        return mSwipeBackDelegate.attachToSwipeBack(view)
    }

    override fun setEdgeLevel(edgeLevel: SwipeBackLayout.EdgeLevel?) {
        return mSwipeBackDelegate.setEdgeLevel(edgeLevel)
    }

    override fun setEdgeLevel(widthPixel: Int) {
        mSwipeBackDelegate.setEdgeLevel(widthPixel)
    }

    override fun setParallaxOffset(offset: Float) {
        mSwipeBackDelegate.setParallaxOffset(offset)
    }

    /**
     * 是否可滑动
     */
    override fun setSwipeBackEnable(enable: Boolean) {
        mSwipeBackDelegate.setSwipeBackEnable(enable)
    }
}