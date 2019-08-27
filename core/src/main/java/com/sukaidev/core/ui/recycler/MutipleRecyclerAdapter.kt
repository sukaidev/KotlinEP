package com.sukaidev.core.ui.recycler

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.sukaidev.core.R
import com.sukaidev.core.ui.banner.BannerCreator
import com.youth.banner.Banner

/**
 * Created by sukaidev on 2019/08/27.
 *
 */
class MutipleRecyclerAdapter constructor(data: MutableList<MultipleItemEntity>) :
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
        fun create(data: MutableList<MultipleItemEntity>): MultipleRecyclerAdapter {
            return MultipleRecyclerAdapter(data)
        }

        fun create(converter: DataConverter): MultipleRecyclerAdapter {
            return MultipleRecyclerAdapter(converter.convert())
        }
    }

    init {
        // 设置不同的布局
        addItemType(ItemType.TEXT, R.layout.item_multiple_text)
        addItemType(ItemType.IMAGE_TEXT, R.layout.item_multiple_image_text)
        addItemType(ItemType.BANNER, R.layout.item_multiple_banner)
        // 设置宽度监听
        setSpanSizeLookup(this)
        openLoadAnimation()
        // 多次执行动画
        isFirstOnly(false)
    }

    override fun convert(holder: MultipleViewHolder, entity: MultipleItemEntity?) {
        val text: String?
        val imageUrl: String?
        val imageView: AppCompatImageView?
        val bannerImages: ArrayList<String>?
        when (holder.itemViewType) {
            ItemType.TEXT -> {
                text = entity?.getField(MultipleFields.TEXT)
                holder.setText(R.id.text_single, text)
            }
            ItemType.IMAGE_TEXT -> {
                text = entity?.getField(MultipleFields.TEXT)
                imageUrl = entity?.getField(MultipleFields.IMAGE_URL)
                imageView = holder.getView(R.id.img_multiple)
                Glide.with(mContext)
                    .load(imageUrl)
                    .apply(options)
                    .into(imageView)
                holder.setText(R.id.tv_multiple_, text)
            }
            ItemType.BANNER -> {
                if (!mIsInitBanner) {
                    bannerImages = entity?.getField(MultipleFields.BANNERS)
                    val homeBanner = holder.getView<Banner>(R.id.mHomeBanner)
                    BannerCreator.setDefault(homeBanner, bannerImages!!, this)
                    mIsInitBanner = true
                }
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