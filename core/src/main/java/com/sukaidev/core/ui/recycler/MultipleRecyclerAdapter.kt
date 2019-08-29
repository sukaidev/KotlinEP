package com.sukaidev.core.ui.recycler

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.sukaidev.core.R
import com.sukaidev.core.ui.banner.BannerCreator
import kotlinx.android.synthetic.main.item_goods_image_text.view.*
import kotlinx.android.synthetic.main.item_index_discount.view.*
import kotlinx.android.synthetic.main.item_multiple_banner.view.*
import kotlinx.android.synthetic.main.item_multiple_news.view.*
import kotlin.math.log

/**
 * Created by sukaidev on 2019/08/27.
 *
 */
open class MultipleRecyclerAdapter (data: ArrayList<MultipleItemEntity>) :
    BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>(data),
    BaseQuickAdapter.SpanSizeLookup,
    BaseQuickAdapter.OnItemClickListener {

    // 确保初始化一次banner，防止重复加载
    private var mIsInitBanner = false

    private val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .dontAnimate()
        .centerCrop()

    companion object {
        fun create(data: ArrayList<MultipleItemEntity>): MultipleRecyclerAdapter {
            return MultipleRecyclerAdapter(data)
        }

        fun create(converter: DataConverter): MultipleRecyclerAdapter {
            return MultipleRecyclerAdapter(converter.convert())
        }
    }

    init {
        init()
    }

    private fun init() {
        // 设置不同的布局
        addItemType(ItemType.BANNER, R.layout.item_multiple_banner)
        addItemType(ItemType.NEWS, R.layout.item_multiple_news)
        addItemType(ItemType.DISCOUNT, R.layout.item_index_discount)
        addItemType(ItemType.GOODS, R.layout.item_goods_image_text)
        // 设置宽度监听
        setSpanSizeLookup(this)
        openLoadAnimation()
        // 多次执行动画
        isFirstOnly(false)
    }

    override fun convert(holder: MultipleViewHolder, entity: MultipleItemEntity?) {
        val text: String?
        val news: ArrayList<String>?
        val imageUrl: String?
        val bannerImages: ArrayList<String>?
        when (holder.itemViewType) {
            ItemType.BANNER -> {
                if (!mIsInitBanner) {
                    bannerImages = entity?.getField(MultipleFields.BANNERS)
                    val bannerView = holder.itemView.mIndexBanner
                    BannerCreator.setDefault(bannerView, bannerImages!!, this)
                    mIsInitBanner = true
                }
            }
            ItemType.NEWS -> {
                news = entity?.getField(MultipleFields.NEWS)
                holder.itemView.mNewsFlipperView.setData(news)
            }
            ItemType.DISCOUNT -> {
                imageUrl = entity?.getField(MultipleFields.DISCOUNT_URL)
                val discountBefore: String? = entity?.getField(MultipleFields.DISCOUNT_BEFORE)
                val discountAfter: String? = entity?.getField(MultipleFields.DISCOUNT_AFTER)
                Glide.with(mContext)
                    .load(imageUrl)
                    .apply(options)
                    .into(holder.itemView.mGoodsIconIv)
                holder.itemView.mDiscountBeforeTv.text = discountBefore
                holder.itemView.mDiscountAfterTv.text = discountAfter
            }
            ItemType.GOODS -> {
                imageUrl = entity?.getField(MultipleFields.IMAGE_URL)
                text = entity?.getField(MultipleFields.IMAGE_TEXT)
                Glide.with(mContext)
                    .load(imageUrl)
                    .apply(options)
                    .into(holder.itemView.mGoodsIv)
                holder.itemView.mTitleTv.text = text
            }
        }
    }

    override fun getSpanSize(p0: GridLayoutManager?, p1: Int): Int {
        return data[p1].getField(MultipleFields.SPAN_SIZE)
    }

    override fun createBaseViewHolder(view: View): MultipleViewHolder {
        return MultipleViewHolder.create(view)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
    }

}