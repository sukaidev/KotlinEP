package com.sukaidev.core.ui.recycler

/**
 * Created by sukaidev on 2019/08/27.
 * 数据转换.
 */
abstract class DataConverter {

    protected val ENTITIES: ArrayList<MultipleItemEntity> = ArrayList()
    private var mJsonData: String? = null
    private var mListData: MutableList<*>? = null

    abstract fun convert(): ArrayList<MultipleItemEntity>

    /**
     * 直接设置Json字符串
     */
    fun setJsonData(json: String): DataConverter {
        this.mJsonData = json
        return this
    }

    protected fun getJsonData(): String? {
        if (mJsonData.isNullOrEmpty()) {
            throw NullPointerException("DATA IS NULL!")
        }
        return mJsonData!!
    }

    fun <T : Any> setListData(dataList: MutableList<T>): DataConverter {
        this.mListData = dataList
        return this
    }

    protected fun getListData(): MutableList<*>? {
        if (mListData.isNullOrEmpty()) {
            throw NullPointerException("DATA IS NULL!")
        }
        return mListData
    }
}