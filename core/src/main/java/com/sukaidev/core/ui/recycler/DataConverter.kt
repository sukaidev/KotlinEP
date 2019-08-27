package com.sukaidev.core.ui.recycler

/**
 * Created by sukaidev on 2019/08/27.
 * 数据转换.
 */
abstract class DataConverter {

    protected val ENTITIES: ArrayList<MultipleItemEntity> = ArrayList()
    private var mJsonData: String? = null

    abstract fun convert(): ArrayList<MultipleItemEntity>

    fun setJsonData(json: String): DataConverter {
        this.mJsonData = json
        return this
    }

    protected fun getJsonData(): String {
        if (mJsonData.isNullOrEmpty()) {
            throw NullPointerException("DATA IS NULL!")
        }
        return mJsonData!!
    }
}