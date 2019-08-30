package com.sukaidev.user.data.repository

import com.sukaidev.core.data.net.RetrofitFactory
import com.sukaidev.core.data.protocol.BaseResp
import com.sukaidev.user.data.api.UploadApi
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
class UploadRepository @Inject constructor() {

    fun getUploadToken(): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UploadApi::class.java)
            .getUploadToken()
    }
}