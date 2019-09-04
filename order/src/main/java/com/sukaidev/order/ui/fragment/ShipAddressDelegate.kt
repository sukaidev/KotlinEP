package com.sukaidev.order.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ext.startWithNewBundle
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.order.R
import com.sukaidev.order.common.OrderConstant
import com.sukaidev.order.data.protocol.ShipAddress
import com.sukaidev.order.event.EditAddressSuccessEvent
import com.sukaidev.order.event.SelectAddressEvent
import com.sukaidev.order.injection.component.DaggerShipAddressComponent
import com.sukaidev.order.injection.module.ShipAddressModule
import com.sukaidev.order.presenter.ShipAddressPresenter
import com.sukaidev.order.presenter.view.ShipAddressView
import com.sukaidev.order.ui.adapter.ShipAddressAdapter
import kotlinx.android.synthetic.main.delegate_ship_address.*
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/17.
 * 收货管理地址页面.
 */
class ShipAddressDelegate : BaseMvpDelegate<ShipAddressPresenter>(), ShipAddressView {

    private lateinit var mAdapter: ShipAddressAdapter

    override fun injectComponent() {
        DaggerShipAddressComponent
            .builder()
            .activityComponent(mActivityComponent)
            .shipAddressModule(ShipAddressModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Any {
        return R.layout.delegate_ship_address
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initView()
        initObserve()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        loadData()
    }

    private fun initView() {

        mHeaderBar.getLeftIv().onClick {
            supportDelegate.pop()
        }

        mAddressRv.layoutManager = LinearLayoutManager(context)
        mAdapter = ShipAddressAdapter(null)
        mAddressRv.adapter = mAdapter

        mAdapter.setOnOptClickListener(object : ShipAddressAdapter.OnOptClickListener {

            override fun onSetDefault(address: ShipAddress) {
                mPresenter.setDefaultAddress(address)
            }

            override fun onEdit(address: ShipAddress) {
                // 开启编辑界面
                supportDelegate.startWithNewBundle<ShipAddressEditDelegate>(OrderConstant.KEY_SHIP_ADDRESS to address)
            }

            override fun onDelete(address: ShipAddress) {
                AlertView(
                    "删除",
                    "确定删除该地址？",
                    "取消",
                    null,
                    arrayOf("确定"),
                    context,
                    AlertView.Style.Alert,
                    OnItemClickListener { _, position ->
                        if (position == 0) {
                            mPresenter.deleteShipAddress(address.id)
                        }
                    }
                ).show()
            }
        })

        mAdapter.setOnItemClickListener { adapter, _, position ->
            Bus.send(SelectAddressEvent(adapter.getItem(position) as ShipAddress))
            supportDelegate.pop()
        }

        mAddAddressBtn.onClick {
            supportDelegate.start(ShipAddressEditDelegate())
        }
    }

    private fun initObserve() {
        Bus.observe<EditAddressSuccessEvent>()
            .subscribe {
                loadData()
            }
            .registerInBus(this)
    }

    private fun loadData() {
        mPresenter.getShipAddressList()
    }

    override fun onGetShipAddressListResult(result: MutableList<ShipAddress>?) {
        result?.let {
            mAdapter.setNewData(it)
        }
    }

    override fun onSetDefaultResult(result: Boolean) {
        context?.toast("设置默认成功")
        loadData()
    }

    override fun onDeleteDefaultResult(result: Boolean) {
        context?.toast("删除成功")
        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}