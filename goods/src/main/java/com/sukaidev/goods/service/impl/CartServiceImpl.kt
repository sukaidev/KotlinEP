package com.sukaidev.goods.service.impl

import com.sukaidev.common.ext.convert
import com.sukaidev.goods.data.protocol.CartGoods
import com.sukaidev.goods.data.repository.CartRepository
import com.sukaidev.goods.service.ICartService
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/16.
 *
 */
class CartServiceImpl @Inject constructor() : ICartService {

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

    override fun deleteCartList(list: List<Int>): Observable<String> {
        return repository.deleteCartList(list).convert()
    }

    override fun submitCart(list: MutableList<CartGoods>, totalPrice: Long): Observable<Int> {
        return repository.submitCart(list, totalPrice).convert()
    }
}