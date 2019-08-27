package com.sukaidev.core.ui.recycler

/**
 * Created by sukaidev on 2019/08/27.
 *
 */
class MultipleItemEntityBuilder {

    companion object{
        private val FIELDS :LinkedHashMap<Any?,Any?> = LinkedHashMap()
    }

    init {
        FIELDS.clear()
    }

    fun setItemType(itemType: Int): MultipleItemEntityBuilder {
        FIELDS[MultipleFields.ITEM_TYPE] = itemType
        return this
    }

    fun setField(key: Any, value: Any): MultipleItemEntityBuilder {
        FIELDS[key] = value
        return this
    }

    fun setFields(map: java.util.LinkedHashMap<*, *>): MultipleItemEntityBuilder {
        FIELDS.putAll(map)
        return this
    }

    fun build(): MultipleItemEntity {
        return MultipleItemEntity(FIELDS)
    }
}