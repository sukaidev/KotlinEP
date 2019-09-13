package com.sukaidev.core.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.CheckResult
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import com.sukaidev.core.ui.delegates.BaseDelegate
import com.trello.rxlifecycle.LifecycleProvider
import com.trello.rxlifecycle.android.ActivityEvent
import me.yokeyword.fragmentation.*
import com.sukaidev.core.R
import com.trello.rxlifecycle.LifecycleTransformer
import com.trello.rxlifecycle.RxLifecycle
import com.trello.rxlifecycle.android.RxLifecycleAndroid
import rx.Observable
import rx.subjects.BehaviorSubject

/**
 * Created by sukaidev on 2019/08/25.
 * 自定义SupportActivity.
 * 由于单继承的限制，手动实现了LifecycleProvider接口来支持RxLifecycle.
 */
abstract class ProxyActivity : SupportActivity(), LifecycleProvider<ActivityEvent> {

    companion object {
        lateinit var instance: AppCompatActivity
    }

    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    @NonNull
    @CheckResult
    override fun lifecycle(): Observable<ActivityEvent> {
        return lifecycleSubject.asObservable()
    }

    @NonNull
    @CheckResult
    override fun <T> bindUntilEvent(@NonNull event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    @NonNull
    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject)
    }

    abstract fun setRootDelegate(): BaseDelegate

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(ActivityEvent.CREATE)
        initContainer(savedInstanceState)
        instance = this
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("android:support:fragments", null)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    @CallSuper
    override fun onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    @CallSuper
    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
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

}