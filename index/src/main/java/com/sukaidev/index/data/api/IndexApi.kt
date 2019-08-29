package com.sukaidev.index.data.api

import retrofit2.http.GET
import rx.Observable

/**
 * Created by sukaidev on 2019/08/29.
 *
 */
interface IndexApi {

    @GET("FastEC/kotlin_index.php")
    fun getIndexData(): Observable<String>

}