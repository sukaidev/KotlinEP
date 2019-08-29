package com.sukaidev.index.service.impl

import com.sukaidev.index.data.repository.IndexRepository
import com.sukaidev.index.service.IndexService
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/29.
 *
 */
class IndexServiceImpl @Inject constructor() : IndexService {

    @Inject
    lateinit var repository: IndexRepository

    override fun getIndexData(): Observable<String>? {
        return repository.getIndexData()
    }
}