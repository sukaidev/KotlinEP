package com.sukaidev.goods.ui.goods

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sukaidev.core.ext.loadUrl
import com.sukaidev.core.utils.MoneyConverter
import com.sukaidev.goods.data.protocol.Goods
import kotlinx.android.synthetic.main.item_goods.view.*

/**
 * Created by sukaidev on 2019/09/01.
 *
 */
class GoodsAdapter(layoutId: Int, data: MutableList<Goods>) : BaseQuickAdapter<Goods, BaseViewHolder>(layoutId, data) {

    override fun convert(holder: BaseViewHolder, item: Goods) {
        // 商品图标
        holder.itemView.mGoodsIconIv.loadUrl(item.goodsDefaultIcon)
        // 商品描述
        holder.itemView.mGoodsDescTv.text = item.goodsDesc
        //商品价格
        holder.itemView.mGoodsPriceTv.text =
            MoneyConverter.changeF2YWithUnit(item.goodsDefaultPrice)
        //商品销量及库存
        holder.itemView.mGoodsSalesStockTv.text =
            "销量${item.goodsSalesCount}件     库存${item.goodsStockCount}"
    }
}