package com.sukaidev.order.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.eightbitlab.rxbus.Bus
import com.sukaidev.core.event.OrderPayEvent
import com.sukaidev.core.event.ToOrderDetailEvent
import com.sukaidev.core.ext.startWithNewBundle
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.order.R
import com.sukaidev.order.common.OrderConstant
import com.sukaidev.order.data.protocol.Order
import com.sukaidev.order.injection.component.DaggerOrderComponent
import com.sukaidev.order.injection.module.OrderModule
import com.sukaidev.order.presenter.OrderListPresenter
import com.sukaidev.order.presenter.view.OrderListView
import com.sukaidev.order.ui.adapter.OrderAdapter
import com.sukaidev.provider.common.ProviderConstant
import kotlinx.android.synthetic.main.delegate_order.*
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/09/04.
 * 订单列表页面.
 */
class OrderDelegate : BaseMvpDelegate<OrderListPresenter>(), OrderListView {

    private lateinit var mAdapter: OrderAdapter

    override fun injectComponent() {
        DaggerOrderComponent
            .builder()
            .activityComponent(mActivityComponent)
            .orderModule(OrderModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Any {
        return R.layout.delegate_order
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initView()
        loadData()
    }

    private fun initView() {
        mOrderRv.layoutManager = LinearLayoutManager(activity)
        mAdapter = OrderAdapter(null)
        mOrderRv.adapter = mAdapter

        /**
         * 订单对应操作
         */
        mAdapter.listener = object : OrderAdapter.OnOptClickListener {
            override fun onOptClick(optType: Int, order: Order) {
                when (optType) {
                    OrderConstant.OPT_ORDER_PAY -> {
/*                        ARouter.getInstance().build(RouterPath.PaySDK.PATH_PAY)
                            .withInt(ProviderConstant.KEY_ORDER_ID, order.id)
                            .withLong(ProviderConstant.KEY_ORDER_PRICE, order.totalPrice)
                            .navigation()*/
                        Bus.send(OrderPayEvent(order.id, order.totalPrice))
                    }
                    OrderConstant.OPT_ORDER_CONFIRM -> {
                        mPresenter.confirmOrder(order.id)
                    }
                    OrderConstant.OPT_ORDER_CANCEL -> {
                        showCancelDialog(order)
                    }
                }
            }
        }

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            // 进入订单详情页
            val itemId = (adapter.data[position] as Order).id
            Bus.send(ToOrderDetailEvent(itemId))
//            supportDelegate.startWithNewBundle<OrderDetailDelegate>(ProviderConstant.KEY_ORDER_ID to itemId)
        }
    }

    private fun loadData() {
        arguments?.getInt(OrderConstant.KEY_ORDER_STATUS, -1)?.let { mPresenter.getOrderList(it) }
    }

    private fun showCancelDialog(order: Order) {
        AlertView(
            "取消订单",
            "确定取消订单？",
            "取消",
            null,
            arrayOf("确定"),
            activity,
            AlertView.Style.Alert,
            OnItemClickListener { _, position ->
                if (position == 0) {
                    mPresenter.cancelOrder(order.id)
                }
            }).show()
    }

    override fun onGetOrderListResult(result: MutableList<Order>?) {
        result?.let {
            mAdapter.setNewData(result)
        }
    }

    override fun onConfirmOrderResult(result: Boolean) {
        context?.toast("确认收货成功")
        loadData()
    }

    override fun onCancelOrderResult(result: Boolean) {
        context?.toast("取消订单成功")
        loadData()
    }
}