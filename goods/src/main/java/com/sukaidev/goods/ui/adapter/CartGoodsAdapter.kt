package com.sukaidev.goods.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eightbitlab.rxbus.Bus
import com.sukaidev.common.ext.getEditText
import com.sukaidev.common.ext.loadUrl
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ui.adapter.BaseRecyclerViewAdapter
import com.sukaidev.common.utils.MoneyConverter
import com.sukaidev.common.widget.DefaultTextWatcher
import com.sukaidev.goods.R
import com.sukaidev.goods.data.protocol.CartGoods
import com.sukaidev.goods.event.CartAllCheckedEvent
import com.sukaidev.goods.event.UpdateTotalPriceEvent
import kotlinx.android.synthetic.main.layout_cart_goods_item.view.*

/**
 * Created by sukaidev on 2019/08/16.
 * 购物车数据适配器.
 */
class CartGoodsAdapter(context: Context) : BaseRecyclerViewAdapter<CartGoods, CartGoodsAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(
                R.layout.layout_cart_goods_item,
                parent,
                false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        // 是否选中
        holder.itemView.mCheckedCb.isChecked = model.isSelected
        // 加载商品图片
        holder.itemView.mGoodsIconIv.loadUrl(model.goodsIcon)
        // 商品描述
        holder.itemView.mGoodsDescTv.text = model.goodsDesc
        // 商品SKU
        holder.itemView.mGoodsSkuTv.text = model.goodsSku
        // 商品价格
        holder.itemView.mGoodsPriceTv.text = MoneyConverter.changeF2YWithUnit(model.goodsPrice)
        // 商品数量
        holder.itemView.mGoodsCountBtn.setCurrentNumber(model.goodsCount)
        // 选中按钮事件
        holder.itemView.mCheckedCb.onClick {
            model.isSelected = holder.itemView.mCheckedCb.isChecked
            val isAllChecked = dataList.all {it.isSelected }
            Bus.send(CartAllCheckedEvent(isAllChecked))
            notifyDataSetChanged()
        }

        // 商品数量变化监听
        holder.itemView.mGoodsCountBtn.getEditText().addTextChangedListener(object:
            DefaultTextWatcher(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                model.goodsCount = s.toString().toInt()
                Bus.send(UpdateTotalPriceEvent())
            }
        })

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}