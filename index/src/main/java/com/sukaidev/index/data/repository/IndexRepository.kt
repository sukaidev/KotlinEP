package com.sukaidev.index.data.repository

import com.sukaidev.core.data.protocol.BaseResp
import com.sukaidev.index.data.api.IndexApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/29.
 *
 */
class IndexRepository @Inject constructor() {

    val baseUrl = "https://www.sukaidev.top/api/"

    /**
     * 获取首页数据
     */
    fun getIndexData(): Observable<String>? {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(IndexApi::class.java)
            .getIndexData()
    }
}