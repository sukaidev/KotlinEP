package com.sukaidev.order.data.protocol

/**
 * Created by sukaidev on 2019/08/17.
 * 修改收货地址请求实体.
 */
data class EditShipAddressReq(val id:Int,val shipUserName:String,val shipUserMobile:String,val shipAddress:String,val shipIsDefault:Int)