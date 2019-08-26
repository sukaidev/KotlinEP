package com.sukaidev.order.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sukaidev.common.ui.activity.BaseMvpActivity
import com.sukaidev.common.utils.MoneyConverter
import com.sukaidev.order.R
import com.sukaidev.order.data.protocol.Order
import com.sukaidev.order.injection.component.DaggerOrderComponent
import com.sukaidev.order.injection.module.OrderModule
import com.sukaidev.order.presenter.OrderDetailPresenter
import com.sukaidev.order.presenter.view.IOrderDetailView
import com.sukaidev.order.ui.adapter.OrderGoodsAdapter
import com.sukaidev.provider.common.ProviderConstant
import kotlinx.android.synthetic.main.activity_order_detail.*

/**
 * Created by sukaidev on 2019/08/19.
 * 订单详情页.
 */
class OrderDetailActivity : BaseMvpActivity<OrderDetailPresenter>(), IOrderDetailView {

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

    override fun setLayout(): Int {
        return R.layout.activity_order_detail
    }

    override fun onBindView(savedInstanceState: Bundle?) {
        initView()
        loadData()
    }

    private fun initView() {
        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter
    }

    private fun loadData() {
        mPresenter.getOrderById(intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, -1))
    }

    override fun onGetOrderByIdResult(result: Order) {
        mShipNameTv.setContentText(result.shipAddress!!.shipUserName)
        mShipMobileTv.setContentText(result.shipAddress!!.shipUserMobile)
        mShipAddressTv.setContentText(result.shipAddress!!.shipAddress)
        mTotalPriceTv.setContentText(MoneyConverter.changeF2YWithUnit(result.totalPrice))

        mAdapter.setData(result.orderGoodsList)
    }
}