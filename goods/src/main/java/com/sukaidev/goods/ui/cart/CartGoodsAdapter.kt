package com.sukaidev.goods.ui.cart

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.eightbitlab.rxbus.Bus
import com.sukaidev.core.ext.getEditText
import com.sukaidev.core.ext.loadUrl
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.utils.MoneyConverter
import com.sukaidev.core.widget.DefaultTextWatcher
import com.sukaidev.goods.R
import com.sukaidev.goods.data.protocol.CartGoods
import com.sukaidev.goods.event.CartAllCheckedEvent
import com.sukaidev.goods.event.UpdateTotalPriceEvent
import kotlinx.android.synthetic.main.item_shop_cart.view.*

/**
 * Created by sukaidev on 2019/08/16.
 * 购物车数据适配.
 */
class CartGoodsAdapter(data: MutableList<CartGoods>?) : BaseQuickAdapter<CartGoods, BaseViewHolder>(R.layout.item_shop_cart, data) {

    override fun convert(holder: BaseViewHolder, item: CartGoods) {
        // 是否选中
        holder.itemView.mCheckedCb.isChecked = item.isSelected
        // 加载商品图片
        holder.itemView.mGoodsIconIv.loadUrl(item.goodsIcon)
        // 商品描述
        holder.itemView.mGoodsTitleTv.text = item.goodsTitle
        // 商品SKU
        holder.itemView.mGoodsSkuTv.text = item.goodsSku
        // 商品价格
        holder.itemView.mGoodsPriceTv.text = MoneyConverter.changeF2YWithUnit(item.goodsPrice)
        // 商品数量
        holder.itemView.mGoodsCountBtn.setCurrentNumber(item.goodsCount)
        // 选中按钮事件
        holder.itemView.mCheckedCb.onClick {
            item.isSelected = holder.itemView.mCheckedCb.isChecked
            val isAllChecked = data.all { it.isSelected }
            Bus.send(CartAllCheckedEvent(isAllChecked))
            notifyDataSetChanged()
        }
        // 商品数量变化监听
        holder.itemView.mGoodsCountBtn.getEditText().addTextChangedListener(object : DefaultTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                item.goodsCount = s.toString().toInt()
                Bus.send(UpdateTotalPriceEvent())
            }
        })
    }
}