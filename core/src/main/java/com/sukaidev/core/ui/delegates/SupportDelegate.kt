package com.sukaidev.core.ui.delegates

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.CheckResult
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.trello.rxlifecycle.LifecycleProvider
import com.trello.rxlifecycle.LifecycleTransformer
import com.trello.rxlifecycle.RxLifecycle
import com.trello.rxlifecycle.android.FragmentEvent
import com.trello.rxlifecycle.android.RxLifecycleAndroid
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment
import rx.Observable
import rx.subjects.BehaviorSubject

/**
 * Created by sukaidev on 2019/08/25.
 * 自定义SupportFragment.
 * 由于单继承的限制，手动实现了LifecycleProvider接口来支持RxLifecycle.
 */
open class SupportDelegate : SwipeBackFragment(), LifecycleProvider<FragmentEvent> {

    private val lifecycleSubject = BehaviorSubject.create<FragmentEvent>()

    @NonNull
    @CheckResult
    override fun lifecycle(): Observable<FragmentEvent> {
        return lifecycleSubject.asObservable()
    }

    @NonNull
    @CheckResult
    override fun <T : Any?> bindUntilEvent(event: FragmentEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    @NonNull
    @CheckResult
    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleSubject.onNext(FragmentEvent.ATTACH)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW)
    }

    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(FragmentEvent.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(FragmentEvent.RESUME)
    }

    override fun onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP)
        super.onStop()
    }

    override fun onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
        super.onDestroyView()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY)
        super.onDestroy()
    }

    override fun onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH)
        super.onDetach()
    }
}