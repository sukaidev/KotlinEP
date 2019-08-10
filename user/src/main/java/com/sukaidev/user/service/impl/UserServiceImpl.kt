package com.sukaidev.user.service.impl

import com.sukaidev.user.service.UserService
import rx.Observable

/**
 * Created by sukai on 2019/08/10.
 *
 */
class UserServiceImpl : UserService {
    override fun register(mobile: String, verifyCode: String, pwd: String): Observable<Boolean> {
        return Observable.just(true)
    }

}