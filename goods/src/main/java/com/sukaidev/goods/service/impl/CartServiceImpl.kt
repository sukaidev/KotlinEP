package com.sukaidev.goods.service.impl

import com.sukaidev.core.ext.convert
import com.sukaidev.core.ext.convertBoolean
import com.sukaidev.goods.data.protocol.CartGoods
import com.sukaidev.goods.data.repository.CartRepository
import com.sukaidev.goods.service.CartService
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/16.
 *
 */
class CartServiceImpl @Inject constructor() : CartService {

    @Inject
    lateinit var repository: CartRepository

    override fun getCartList(): Observable<MutableList<CartGoods>?> {
        return repository.getCartList().convert()
    }

    override fun addCart(
        goodsId: Int,
        goodsDesc: String,
        goodsIcon: String,
        goodsPrice: Long,
        goodsCount: Int,
        goodsSku: String
    ): Observable<Int> {
        return repository.addCart(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku)
            .convert()
    }

    override fun deleteCartList(list: List<Int>): Observable<Boolean> {
        return repository.deleteCartList(list).convertBoolean()
    }

    override fun submitCart(list: MutableList<CartGoods>, totalPrice: Long): Observable<Int> {
        return repository.submitCart(list, totalPrice).convert()
    }
}