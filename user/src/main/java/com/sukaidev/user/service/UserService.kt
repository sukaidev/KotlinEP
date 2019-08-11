package com.sukaidev.user.service

import rx.Observable

/**
 * Created by sukai on 2019/08/10.
 *
 */
interface UserService {

    fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean>
}