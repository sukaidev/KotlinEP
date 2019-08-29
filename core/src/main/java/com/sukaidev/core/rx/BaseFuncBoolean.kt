package com.sukaidev.core.rx

import com.sukaidev.core.data.protocol.BaseResp
import com.sukaidev.common.rx.BaseException
import rx.Observable
import rx.functions.Func1

/**
 * Created by sukaidev on 2019/08/11.
 *
 */
class BaseFuncBoolean<T> : Func1<BaseResp<T>, Observable<Boolean>> {
    override fun call(t: BaseResp<T>): Observable<Boolean> {
        if (t.status != 0) {
            return Observable.error(BaseException(t.status, t.message))
        }
        return Observable.just(true)
    }
}