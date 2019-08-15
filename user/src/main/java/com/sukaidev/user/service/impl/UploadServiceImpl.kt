package com.sukaidev.user.service.impl

import com.sukaidev.common.ext.convert
import com.sukaidev.user.data.repository.UploadRepository
import com.sukaidev.user.service.IUploadService
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
class UploadServiceImpl @Inject constructor() : IUploadService {

    @Inject
    lateinit var repository: UploadRepository

    override fun getUploadToken(): Observable<String> {
        return repository.getUploadToken().convert()
    }
}