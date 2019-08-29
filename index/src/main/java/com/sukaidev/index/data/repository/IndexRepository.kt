package com.sukaidev.index.data.repository

import com.sukaidev.index.data.data
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/29.
 *
 */
class IndexRepository @Inject constructor() {

    /**
     * 获取首页数据，模拟网络请求
     */
    fun getIndexData(): Observable<String> {
        return Observable.just(data)
    }
}