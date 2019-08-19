package com.sukaidev.order.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ext.setVisible
import com.sukaidev.common.ui.activity.BaseMvpActivity
import com.sukaidev.common.utils.MoneyConverter
import com.sukaidev.order.R
import com.sukaidev.order.data.protocol.Order
import com.sukaidev.order.event.SelectAddressEvent
import com.sukaidev.order.injection.component.DaggerOrderComponent
import com.sukaidev.order.injection.module.OrderModule
import com.sukaidev.order.presenter.OrderConfirmPresenter
import com.sukaidev.order.presenter.view.IOrderConfirmView
import com.sukaidev.order.ui.adapter.OrderGoodsAdapter
import com.sukaidev.provider.common.ProviderConstant
import com.sukaidev.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_order_confirm.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/17.
 *
 */
@Route(path = RouterPath.Order.PATH_ORDER_CONFIRM)
class OrderConfirmActivity : BaseMvpActivity<OrderConfirmPresenter>(), IOrderConfirmView {

    lateinit var mAdapter: OrderGoodsAdapter
    private var mCurrentOrder: Order? = null

    @Autowired(name = ProviderConstant.KEY_ORDER_ID)
    @JvmField
    var mOrderId: Int = 0

    override fun injectComponent() {
        DaggerOrderComponent
            .builder()
            .activityComponent(activityComponent)
            .orderModule(OrderModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Int {
        return R.layout.activity_order_confirm
    }

    override fun onBindView(savedInstanceState: Bundle?) {

        ARouter.getInstance().inject(this)

        initView()
        initObserve()
        loadData()
    }

    private fun initView() {

        mShipView.onClick {
            startActivity<ShipAddressActivity>()
        }

        mSelectShipTv.onClick {
            startActivity<ShipAddressActivity>()
        }

        mSubmitOrderBtn.onClick {
            mCurrentOrder?.let {
                mPresenter.submitOrder(it)
            }
        }

        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter
    }

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

    private fun loadData() {
        mPresenter.getOrderById(mOrderId)
    }

    @SuppressLint("SetTextI18n")
    override fun onGetOrderByIdResult(result: Order) {
        mCurrentOrder = result
        mAdapter.setData(result.orderGoodsList)
        mTotalPriceTv.text = "合计：${MoneyConverter.changeF2YWithUnit(result.totalPrice)}"

        updateAddressView()
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    @SuppressLint("SetTextI18n")
    private fun updateAddressView() {
        mCurrentOrder?.let {
            if (it.shipAddress == null) {
                mSelectShipTv.setVisible(true)
                mShipView.setVisible(false)
            } else {
                mSelectShipTv.setVisible(false)
                mShipView.setVisible(true)

                mShipNameTv.text =
                    it.shipAddress!!.shipUserName + "  " + it.shipAddress!!.shipUserMobile
                mShipAddressTv.text = it.shipAddress!!.shipAddress
            }
        }
    }

    override fun onSubmitOrderResult(result: String) {
        toast("订单提交成功")
    }
}