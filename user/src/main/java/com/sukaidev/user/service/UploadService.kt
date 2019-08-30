package com.sukaidev.user.service

import rx.Observable

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
interface UploadService {

    fun getUploadToken(): Observable<String>

}