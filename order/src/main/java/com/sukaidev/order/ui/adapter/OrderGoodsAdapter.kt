package com.sukaidev.order.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sukaidev.core.ext.loadUrl
import com.sukaidev.core.utils.MoneyConverter
import com.sukaidev.order.R
import com.sukaidev.order.data.protocol.OrderGoods
import kotlinx.android.synthetic.main.item_order_goods.view.*

/**
 * Created by sukaidev on 2019/08/21.
 * 订单商品数据适配.
 */
class OrderGoodsAdapter(data: MutableList<OrderGoods>?) : BaseQuickAdapter<OrderGoods, BaseViewHolder>(R.layout.item_order_goods, data) {

    override fun convert(holder: BaseViewHolder, item: OrderGoods) {
        holder.itemView.mGoodsIconIv.loadUrl(item.goodsIcon)
        holder.itemView.mGoodsTitle.text = item.goodsTitle
        holder.itemView.mGoodsSkuTv.text = item.goodsSku
        holder.itemView.mGoodsPriceTv.text = MoneyConverter.changeF2YWithUnit(item.goodsPrice)
        holder.itemView.mGoodsCountTv.text = "x${item.goodsCount}"
    }
}