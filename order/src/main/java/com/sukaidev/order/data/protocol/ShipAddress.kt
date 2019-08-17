package com.sukaidev.order.data.protocol

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by sukaidev on 2019/08/17.
 * 收货地址实体.
 */
@Parcelize
data class ShipAddress(
    val id: Int,
    var shipUserName: String,
    var shipUserMobile: String,
    var shipAddress: String,
    var shipIsDefault: Int
):Parcelable