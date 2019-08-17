package com.sukaidev.order.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sukaidev.common.ext.loadUrl
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ext.setVisible
import com.sukaidev.common.ui.adapter.BaseRecyclerViewAdapter
import com.sukaidev.common.utils.MoneyConverter
import com.sukaidev.order.R
import com.sukaidev.order.common.OrderConstant
import com.sukaidev.order.common.OrderStatus
import com.sukaidev.order.data.protocol.Order
import kotlinx.android.synthetic.main.layout_order_item.view.*
import org.jetbrains.anko.dip

/**
 * Created by sukaidev on 2019/08/18.
 * 订单页面数据适配器.
 */
class OrderAdapter(context: Context) :
    BaseRecyclerViewAdapter<Order, OrderAdapter.ViewHolder>(context) {

    var listener: OnOptClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.layout_order_item,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        var mTotalCount = 0
        if (model.orderGoodsList.size == 1) {
            holder.itemView.mSingleGoodsView.setVisible(true)
            holder.itemView.mMultiGoodsView.setVisible(false)// 单个商品隐藏多个商品视图
            val orderGoods = model.orderGoodsList[0]
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
            for (orderGoods in model.orderGoodsList) {// 动态添加商品视图
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
            "合计${mTotalCount}件商品，总价${MoneyConverter.changeF2YWithUnit(model.totalPrice)}"

        when (model.orderStatus) {// 根据订单状态设置颜色、文案及对应操作按钮
            OrderStatus.ORDER_WAIT_PAY -> {
                holder.itemView.mOrderStatusNameTv.text = "待支付"
                holder.itemView.mOrderStatusNameTv.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.common_red
                    )
                )
                setOptVisible(
                    confirmVisible = false,
                    waitPayVisible = true,
                    cancelVisible = true,
                    holder = holder
                )
            }
            OrderStatus.ORDER_WAIT_CONFIRM -> {
                holder.itemView.mOrderStatusNameTv.text = "待收货"
                holder.itemView.mOrderStatusNameTv.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.common_blue
                    )
                )
                setOptVisible(
                    confirmVisible = true,
                    waitPayVisible = false,
                    cancelVisible = true,
                    holder = holder
                )
            }

            OrderStatus.ORDER_COMPLETED -> {
                holder.itemView.mOrderStatusNameTv.text = "已完成"
                holder.itemView.mOrderStatusNameTv.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.common_yellow
                    )
                )
                setOptVisible(
                    confirmVisible = false,
                    waitPayVisible = false,
                    cancelVisible = false,
                    holder = holder
                )
            }

            OrderStatus.ORDER_CANCELED -> {
                holder.itemView.mOrderStatusNameTv.text = "已取消"
                holder.itemView.mOrderStatusNameTv.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.common_gray
                    )
                )
                setOptVisible(false, waitPayVisible = false, cancelVisible = false, holder = holder)
            }
        }

        // 设置确认收货点击事件
        holder.itemView.mConfirmBtn.onClick {
            listener?.onOptClick(OrderConstant.OPT_ORDER_CONFIRM, model)
        }

        // 设置支付订单点击事件
        holder.itemView.mPayBtn.onClick {
            listener?.onOptClick(OrderConstant.OPT_ORDER_PAY, model)
        }

        // 设置取消订单点击事件
        holder.itemView.mCancelBtn.onClick {
            listener?.onOptClick(OrderConstant.OPT_ORDER_CANCEL, model)
        }
    }

    /**
     * 设置操作按钮显示或隐藏
     */
    private fun setOptVisible(
        confirmVisible: Boolean,
        waitPayVisible: Boolean,
        cancelVisible: Boolean,
        holder: ViewHolder
    ) {
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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}