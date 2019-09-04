package com.sukaidev.order.data.api

import com.sukaidev.core.data.protocol.BaseResp
import com.sukaidev.order.data.protocol.AddShipAddressReq
import com.sukaidev.order.data.protocol.DeleteShipAddressReq
import com.sukaidev.order.data.protocol.EditShipAddressReq
import com.sukaidev.order.data.protocol.ShipAddress
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/**
 * Created by sukaidev on 2019/08/17.
 * 地址管理相关API.
 */
interface ShipAddressApi {

    /**
     * 添加收货地址
     */
    @POST("shipAddress/add")
    fun addShipAddress(@Body req: AddShipAddressReq): Observable<BaseResp<String>>

    /**
     * 删除收货地址
     */
    @POST("shipAddress/delete")
    fun deleteShipAddress(@Body req: DeleteShipAddressReq): Observable<BaseResp<String>>

    /**
     * 修改收货地址
     */
    @POST("shipAddress/modify")
    fun editShipAddress(@Body req: EditShipAddressReq): Observable<BaseResp<String>>

    /**
     * 查询收货地址列表
     */
    @POST("shipAddress/getList")
    fun getShipAddressList(): Observable<BaseResp<MutableList<ShipAddress>?>>
}