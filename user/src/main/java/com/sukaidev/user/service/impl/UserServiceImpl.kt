package com.sukaidev.user.service.impl

import com.sukaidev.common.data.protocol.BaseResp
import com.sukaidev.common.ext.convertBoolean
import com.sukaidev.common.rx.BaseException
import com.sukaidev.common.rx.BaseFuncBoolean
import com.sukaidev.user.data.repository.UserRepository
import com.sukaidev.user.service.UserService
import rx.Observable
import rx.functions.Func1
import javax.inject.Inject

/**
 * Created by sukai on 2019/08/10.
 *
 */
class UserServiceImpl @Inject constructor() : UserService {

    @Inject
    lateinit var repository: UserRepository

    override fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean> {

        return repository.register(mobile, pwd, verifyCode)
            .convertBoolean()
    }
}