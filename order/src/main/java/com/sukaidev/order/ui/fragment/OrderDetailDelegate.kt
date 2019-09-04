package com.sukaidev.order.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.core.utils.MoneyConverter
import com.sukaidev.order.R
import com.sukaidev.order.data.protocol.Order
import com.sukaidev.order.injection.component.DaggerOrderComponent
import com.sukaidev.order.injection.module.OrderModule
import com.sukaidev.order.presenter.OrderDetailPresenter
import com.sukaidev.order.presenter.view.OrderDetailView
import com.sukaidev.order.ui.adapter.OrderGoodsAdapter
import com.sukaidev.provider.common.ProviderConstant
import kotlinx.android.synthetic.main.delegate_order_detail.*

/**
 * Created by sukaidev on 2019/08/23.
 * 订单详情页.
 */
class OrderDetailDelegate : BaseMvpDelegate<OrderDetailPresenter>(), OrderDetailView {

    private lateinit var mAdapter: OrderGoodsAdapter

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
        return R.layout.delegate_order_detail
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initView()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        loadData()
    }

    private fun initView() {

        mHeaderBar.getLeftIv().onClick {
            supportDelegate.pop()
        }

        mOrderGoodsRv.layoutManager = LinearLayoutManager(context)
        mAdapter = OrderGoodsAdapter(null)
        mOrderGoodsRv.adapter = mAdapter
    }

    private fun loadData() {
        arguments?.let {
            mPresenter.getOrderById(it.getInt(ProviderConstant.KEY_ORDER_ID, -1))
        }
    }

    override fun onGetOrderByIdResult(result: Order) {
        mShipNameTv.setContentText(result.shipAddress!!.shipUserName)
        mShipMobileTv.setContentText(result.shipAddress!!.shipUserMobile)
        mShipAddressTv.setContentText(result.shipAddress!!.shipAddress)
        mTotalPriceTv.setContentText(MoneyConverter.changeF2YWithUnit(result.totalPrice))

        mAdapter.setNewData(result.orderGoodsList)
    }
}