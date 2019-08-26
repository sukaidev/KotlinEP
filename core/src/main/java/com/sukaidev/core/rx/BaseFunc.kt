package com.sukaidev.core.rx

import com.sukaidev.common.data.protocol.BaseResp
import com.sukaidev.common.rx.BaseException
import com.sukaidev.core.common.ResultCode
import rx.Observable
import rx.functions.Func1

/**
 * Created by sukaidev on 2019/08/11.
 *
 */
class BaseFunc<T> : Func1<BaseResp<T>, Observable<T>> {
    override fun call(t: BaseResp<T>): Observable<T> {
        if (t.status != ResultCode.SUCCESS) {
            return Observable.error(BaseException(t.status, t.message))
        }
        return Observable.just(t.data)
    }
}