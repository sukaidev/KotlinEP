package com.sukaidev.goods.service

import com.sukaidev.goods.data.protocol.CartGoods
import rx.Observable

/**
 * Created by sukaidev on 2019/08/16.
 *
 */
interface CartService {
    /**
     * 获取购物车列表
     */
    fun getCartList(): Observable<MutableList<CartGoods>?>

    /**
     * 添加商品到购物车
     */
    fun addCart(
        goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
        goodsCount: Int, goodsSku: String
    ): Observable<Int>

    /**
     * 删除购物车商品
     */
    fun deleteCartList(list: List<Int>): Observable<Boolean>

    /**
     * 提交购物车商品
     */
    fun submitCart(list: MutableList<CartGoods>, totalPrice: Long): Observable<Int>
}