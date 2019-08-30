package com.sukaidev.mine.service.impl

import com.sukaidev.core.ext.convert
import com.sukaidev.mine.data.repository.UploadRepository
import com.sukaidev.mine.service.UploadService
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
class UploadServiceImpl @Inject constructor() : UploadService {

    @Inject
    lateinit var repository: UploadRepository

    override fun getUploadToken(): Observable<String> {
        return repository.getUploadToken().convert()
    }
}