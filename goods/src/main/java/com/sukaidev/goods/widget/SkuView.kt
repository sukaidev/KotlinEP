package com.sukaidev.goods.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.eightbitlab.rxbus.Bus
import com.sukaidev.core.common.GoodsConstant
import com.sukaidev.goods.R
import com.sukaidev.goods.data.protocol.GoodsSku
import com.sukaidev.goods.event.SkuChangedEvent
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.layout_sku_view.view.*

/**
 * Created by sukaidev on 2019/08/16.
 * 单个SKU视图.
 */
class SkuView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var mGoodsSku: GoodsSku

    // 当前选中的Tag
    internal var clickedPosition: Int = -1

    init {
        View.inflate(context, R.layout.layout_sku_view, this)
    }

    /**
     * 动态设置SKU数据
     */
    fun setSkuData(goodsSku: GoodsSku) {
        mGoodsSku = goodsSku
        mSkuTitleTv.text = goodsSku.skuTitle

        // FlowLayout设置数据
        mSkuContentView.adapter = object : TagAdapter<String>(goodsSku.skuContent) {
            override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.layout_sku_item, parent, false) as TextView
                view.text = t
                return view
            }
        }
        mSkuContentView.adapter.setSelectedList(0)
        clickedPosition = 0

        mSkuContentView.setOnTagClickListener { _, position, _ ->
            if (clickedPosition != position) {
                clickedPosition = position
                Bus.send(SkuChangedEvent())
                true
            } else {
                clickedPosition = -1
                true
            }
        }
    }

    /**
     * 获取选中的SKU
     */
    fun getSkuInfo(): String {
/*        if (mSkuContentView.selectedList.isEmpty()) {
            return ""
        }*/
        if (clickedPosition == -1){
            return mSkuTitleTv.text.toString() + GoodsConstant.SKU_SEPARATOR + "未选择"
        }
        return mSkuTitleTv.text.toString() + GoodsConstant.SKU_SEPARATOR + mGoodsSku.skuContent[mSkuContentView.selectedList.first()]
    }
}