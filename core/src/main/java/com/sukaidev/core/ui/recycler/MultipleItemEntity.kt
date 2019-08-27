package com.sukaidev.core.ui.recycler

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.lang.ref.ReferenceQueue
import java.lang.ref.SoftReference
import kotlin.collections.LinkedHashMap

/**
 * Created by sukaidev on 2019/08/27.
 *
 */
class MultipleItemEntity(fields: LinkedHashMap<Any?, Any?>) : MultiItemEntity {

    private val itemQueue: ReferenceQueue<LinkedHashMap<Any?, Any?>> = ReferenceQueue()
    private val multipleFields: LinkedHashMap<Any?, Any?> = LinkedHashMap()
    private val fieldsReference: SoftReference<LinkedHashMap<Any?, Any?>> =
        SoftReference(multipleFields, itemQueue)

    companion object {
        fun builder(): MultipleItemEntityBuilder {
            return MultipleItemEntityBuilder()
        }
    }

    init {
        fieldsReference.get()?.putAll(fields)
    }

    override fun getItemType(): Int {
        return fieldsReference.get()?.get(MultipleFields.ITEM_TYPE) as Int
    }

    fun <T> getField(key: Any): T {
        return fieldsReference.get()?.get(key) as T
    }

    fun getFields(): LinkedHashMap<*, *> {
        return fieldsReference.get() as LinkedHashMap<*, *>
    }

    fun setField(key: Any, value: Any): MultiItemEntity {
        fieldsReference.get()?.put(key, value)
        return this
    }
}