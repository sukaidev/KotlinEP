package com.sukaidev.index.service

import rx.Observable

/**
 * Created by sukaidev on 2019/08/29.
 *
 */
interface IndexService {

    fun getIndexData(): Observable<String>
}