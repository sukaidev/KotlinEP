package com.sukaidev.order.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sukaidev.common.ext.loadUrl
import com.sukaidev.common.ui.adapter.BaseRecyclerViewAdapter
import com.sukaidev.common.utils.MoneyConverter
import com.sukaidev.order.R
import com.sukaidev.order.data.protocol.OrderGoods
import kotlinx.android.synthetic.main.layout_order_goods_item.view.*

/**
 * Created by sukaidev on 2019/08/18.
 * 订单中商品列表数据适配.
 */
class OrderGoodsAdapter(context: Context) :
    BaseRecyclerViewAdapter<OrderGoods, OrderGoodsAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.layout_order_goods_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        holder.itemView.mGoodsIconIv.loadUrl(model.goodsIcon)
        holder.itemView.mGoodsDescTv.text = model.goodsDesc
        holder.itemView.mGoodsSkuTv.text = model.goodsSku
        holder.itemView.mGoodsPriceTv.text = MoneyConverter.changeF2YWithUnit(model.goodsPrice)
        holder.itemView.mGoodsCountTv.text = "x${model.goodsCount}"
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}