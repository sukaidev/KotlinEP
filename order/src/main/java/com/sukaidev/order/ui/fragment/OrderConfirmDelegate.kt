package com.sukaidev.order.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.sukaidev.core.event.OrderPayEvent
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ext.setVisible
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.core.utils.MoneyConverter
import com.sukaidev.order.R
import com.sukaidev.order.data.protocol.Order
import com.sukaidev.order.event.SelectAddressEvent
import com.sukaidev.order.injection.component.DaggerOrderComponent
import com.sukaidev.order.injection.module.OrderModule
import com.sukaidev.order.presenter.OrderConfirmPresenter
import com.sukaidev.order.presenter.view.OrderConfirmView
import com.sukaidev.order.ui.adapter.OrderGoodsAdapter
import com.sukaidev.core.common.ProviderConstant
import kotlinx.android.synthetic.main.delegate_order_confirm.*
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/21.
 * 订单确认页面.
 */
class OrderConfirmDelegate : BaseMvpDelegate<OrderConfirmPresenter>(), OrderConfirmView {

    // 订单Id
    private var mOrderId: Int = 0

    private lateinit var mAdapter: OrderGoodsAdapter
    private var mCurrentOrder: Order? = null

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
        return R.layout.delegate_order_confirm
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initView()
        initObserve()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        arguments?.let {
            mOrderId = arguments!!.getInt(ProviderConstant.KEY_ORDER_ID)
        }
        loadData()
    }

    /**
     * 初始化视图
     */
    private fun initView() {

        mHeaderBar.getLeftIv().onClick{
            supportDelegate.pop()
        }

        mShipView.onClick {
            //            startActivity<ShipAddressActivity>()
            supportDelegate.start(ShipAddressDelegate())
        }
        mSelectShipTv.onClick {
            //            startActivity<ShipAddressActivity>()
            supportDelegate.start(ShipAddressDelegate())
        }

        mSubmitOrderBtn.onClick {
            mCurrentOrder?.let {
                mPresenter.submitOrder(it)
            }
        }

        //订单中商品列表
        mOrderGoodsRv.layoutManager = LinearLayoutManager(context)
        mAdapter = OrderGoodsAdapter(null)
        mOrderGoodsRv.adapter = mAdapter
    }

    /**
     * 加载数据
     */
    private fun loadData() {
        mPresenter.getOrderById(mOrderId)
    }

    /**
     * 初始化事件监听
     */
    private fun initObserve() {
        Bus.observe<SelectAddressEvent>()
            .subscribe { t: SelectAddressEvent ->
                run {
                    mCurrentOrder?.let {
                        it.shipAddress = t.address
                    }
                    updateAddressView()
                }
            }
            .registerInBus(this)
    }

    /**
     * 更新收货人信息
     */
    @SuppressLint("SetTextI18n")
    private fun updateAddressView() {
        mCurrentOrder?.let {
            if (it.shipAddress == null) {
                mSelectShipTv.setVisible(true)
                mShipView.setVisible(false)
            } else {
                mSelectShipTv.setVisible(false)
                mShipView.setVisible(true)

                mShipNameTv.text = it.shipAddress!!.shipUserName + "  " +
                        it.shipAddress!!.shipUserMobile
                mShipAddressTv.text = it.shipAddress!!.shipAddress
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onGetOrderByIdResult(result: Order) {
        mCurrentOrder = result
        mAdapter.setNewData(result.orderGoodsList)
        mTotalPriceTv.text = "合计：${MoneyConverter.changeF2YWithUnit(result.totalPrice)}"

        updateAddressView()
    }

    override fun onSubmitOrderResult(result: String) {
        context?.toast(result)
        supportDelegate.pop()
        Bus.send(OrderPayEvent(mCurrentOrder!!.id,mCurrentOrder!!.totalPrice))
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}