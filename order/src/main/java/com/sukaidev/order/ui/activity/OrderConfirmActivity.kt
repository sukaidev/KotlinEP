package com.sukaidev.order.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ui.activity.BaseMvpActivity
import com.sukaidev.common.utils.MoneyConverter
import com.sukaidev.order.R
import com.sukaidev.order.data.protocol.Order
import com.sukaidev.order.injection.component.DaggerOrderComponent
import com.sukaidev.order.injection.module.OrderModule
import com.sukaidev.order.presenter.OrderConfirmPresenter
import com.sukaidev.order.presenter.view.IOrderConfirmView
import com.sukaidev.order.ui.adapter.OrderGoodsAdapter
import com.sukaidev.provider.common.ProviderConstant
import com.sukaidev.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_order_confirm.*
import org.jetbrains.anko.startActivity

/**
 * Created by sukaidev on 2019/08/17.
 *
 */
@Route(path = RouterPath.Order.PATH_ORDER_CONFIRM)
class OrderConfirmActivity : BaseMvpActivity<OrderConfirmPresenter>(), IOrderConfirmView {

    lateinit var mAdapter: OrderGoodsAdapter

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
        loadData()
    }

    private fun initView() {
        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter

        mSelectShipTv.onClick {
            startActivity<ShipAddressActivity>()
        }
    }

    private fun loadData() {
        mPresenter.getOrderById(mOrderId)
    }

    override fun onGetOrderByIdResult(result: Order) {

        mAdapter.setData(result.orderGoodsList)
        mTotalPriceTv.text = "合计：${MoneyConverter.changeF2YWithUnit(result.totalPrice)}"
    }
}