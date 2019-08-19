package com.sukaidev.order.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.kennyc.view.MultiStateView
import com.sukaidev.common.ui.adapter.BaseRecyclerViewAdapter
import com.sukaidev.common.ui.fragment.BaseMvpFragment
import com.sukaidev.order.R
import com.sukaidev.order.common.OrderConstant
import com.sukaidev.order.data.protocol.Order
import com.sukaidev.order.injection.component.DaggerOrderComponent
import com.sukaidev.order.injection.module.OrderModule
import com.sukaidev.order.presenter.OrderListPresenter
import com.sukaidev.order.presenter.view.IOrderListView
import com.sukaidev.order.ui.activity.OrderDetailActivity
import com.sukaidev.order.ui.adapter.OrderAdapter
import com.sukaidev.provider.common.ProviderConstant
import kotlinx.android.synthetic.main.fragment_order.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/18.
 *
 */
class OrderFragment : BaseMvpFragment<OrderListPresenter>(), IOrderListView {

    private lateinit var mAdapter: OrderAdapter

    override fun injectComponent() {
        DaggerOrderComponent
            .builder()
            .activityComponent(activityComponent)
            .orderModule(OrderModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Any {
        return R.layout.fragment_order
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initView()
        loadData()
    }

    private fun initView() {
        mOrderRv.layoutManager = LinearLayoutManager(activity)
        mAdapter = OrderAdapter(activity as Context)
        mOrderRv.adapter = mAdapter

        mAdapter.listener = object : OrderAdapter.OnOptClickListener {
            override fun onOptClick(optType: Int, order: Order) {
                when (optType) {
                    OrderConstant.OPT_ORDER_PAY -> {

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

        mAdapter.setOnItemClickListener(object:BaseRecyclerViewAdapter.OnItemClickListener<Order>{
            override fun onItemClick(item: Order, position: Int) {
                context?.startActivity<OrderDetailActivity>(ProviderConstant.KEY_ORDER_ID to item.id)
            }
        })
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
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.ViewState.CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.ViewState.EMPTY
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