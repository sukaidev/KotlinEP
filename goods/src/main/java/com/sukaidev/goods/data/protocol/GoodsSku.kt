package com.sukaidev.goods.data.protocol

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by sukaidev on 2019/08/15.
 * 商品SKU数据类.
 */
@Parcelize
data class GoodsSku(
    val id: Int,
    val skuTitle: String,//SKU标题
    val skuContent: List<String>//SKU内容
): Parcelable