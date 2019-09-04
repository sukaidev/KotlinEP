package com.sukaidev.order.ui.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sukaidev.core.ext.loadUrl
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ext.setVisible
import com.sukaidev.core.utils.MoneyConverter
import com.sukaidev.order.R
import com.sukaidev.order.common.OrderConstant
import com.sukaidev.order.common.OrderStatus
import com.sukaidev.order.data.protocol.Order
import kotlinx.android.synthetic.main.item_order.view.*
import org.jetbrains.anko.dip

/**
 * Created by sukaidev on 2019/08/23.
 *
 */
class OrderAdapter(data: MutableList<Order>?) : BaseQuickAdapter<Order, BaseViewHolder>(R.layout.item_order, data) {

    var listener: OnOptClickListener? = null

    override fun convert(holder: BaseViewHolder, item: Order) {

        var mTotalCount = 0
        if (item.orderGoodsList.size == 1) {
            holder.itemView.mSingleGoodsView.setVisible(true)
            holder.itemView.mMultiGoodsView.setVisible(false)// 单个商品隐藏多个商品视图
            val orderGoods = item.orderGoodsList[0]
            holder.itemView.mGoodsIconIv.loadUrl(orderGoods.goodsIcon)// 商品图标
            holder.itemView.mGoodsDescTv.text = orderGoods.goodsDesc// 商品描述
            holder.itemView.mGoodsPriceTv.text =
                MoneyConverter.changeF2YWithUnit(orderGoods.goodsPrice)// 商品价格
            holder.itemView.mGoodsCountTv.text = "x${orderGoods.goodsCount}"// 商品数量
            mTotalCount = orderGoods.goodsCount
        } else {
            holder.itemView.mSingleGoodsView.setVisible(false)// 多个商品隐藏单个商品视图
            holder.itemView.mMultiGoodsView.setVisible(true)
            holder.itemView.mMultiGoodsView.removeAllViews()
            for (orderGoods in item.orderGoodsList) {// 动态添加商品视图
                val imageView = ImageView(mContext)
                val lp = ViewGroup.MarginLayoutParams(mContext.dip(60.0f), mContext.dip(60.0f))
                lp.rightMargin = mContext.dip(15f)
                imageView.layoutParams = lp
                imageView.loadUrl(orderGoods.goodsIcon)

                holder.itemView.mMultiGoodsView.addView(imageView)

                mTotalCount += orderGoods.goodsCount
            }
        }

        holder.itemView.mOrderInfoTv.text =
            "合计${mTotalCount}件商品，总价${MoneyConverter.changeF2YWithUnit(item.totalPrice)}"

        when (item.orderStatus) {// 根据订单状态设置颜色、文案及对应操作按钮
            OrderStatus.ORDER_WAIT_PAY -> {
                holder.itemView.mOrderStatusNameTv.text = "待支付"
                holder.itemView.mOrderStatusNameTv.setTextColor(ContextCompat.getColor(mContext, R.color.common_red))
                setOptVisible(confirmVisible = false, waitPayVisible = true, cancelVisible = true, holder = holder)
            }
            OrderStatus.ORDER_WAIT_CONFIRM -> {
                holder.itemView.mOrderStatusNameTv.text = "待收货"
                holder.itemView.mOrderStatusNameTv.setTextColor(ContextCompat.getColor(mContext, R.color.common_blue))
                setOptVisible(confirmVisible = true, waitPayVisible = false, cancelVisible = true, holder = holder)
            }

            OrderStatus.ORDER_COMPLETED -> {
                holder.itemView.mOrderStatusNameTv.text = "已完成"
                holder.itemView.mOrderStatusNameTv.setTextColor(ContextCompat.getColor(mContext, R.color.app_main))
                setOptVisible(confirmVisible = false, waitPayVisible = false, cancelVisible = false, holder = holder)
            }

            OrderStatus.ORDER_CANCELED -> {
                holder.itemView.mOrderStatusNameTv.text = "已取消"
                holder.itemView.mOrderStatusNameTv.setTextColor(ContextCompat.getColor(mContext, R.color.common_gray))
                setOptVisible(false, waitPayVisible = false, cancelVisible = false, holder = holder)
            }
        }

        // 设置确认收货点击事件
        holder.itemView.mConfirmBtn.onClick {
            listener?.onOptClick(OrderConstant.OPT_ORDER_CONFIRM, item)
        }

        // 设置支付订单点击事件
        holder.itemView.mPayBtn.onClick {
            listener?.onOptClick(OrderConstant.OPT_ORDER_PAY, item)
        }

        // 设置取消订单点击事件
        holder.itemView.mCancelBtn.onClick {
            listener?.onOptClick(OrderConstant.OPT_ORDER_CANCEL, item)
        }
    }

    /**
     * 设置操作按钮显示与隐藏
     */
    private fun setOptVisible(confirmVisible: Boolean, waitPayVisible: Boolean, cancelVisible: Boolean, holder: BaseViewHolder) {
        holder.itemView.mConfirmBtn.setVisible(confirmVisible)
        holder.itemView.mPayBtn.setVisible(waitPayVisible)
        holder.itemView.mCancelBtn.setVisible(cancelVisible)

        if (confirmVisible or waitPayVisible or cancelVisible) {
            holder.itemView.mBottomView.setVisible(true)
        } else {
            holder.itemView.mBottomView.setVisible(false)
        }
    }

    interface OnOptClickListener {
        fun onOptClick(optType: Int, order: Order)
    }
}