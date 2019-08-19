package com.sukaidev.order.ui.activity

import android.net.Uri
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.eightbitlab.rxbus.Bus
import com.jph.takephoto.compress.CompressConfig
import com.kennyc.view.MultiStateView
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ext.setVisible
import com.sukaidev.common.ext.startLoading
import com.sukaidev.common.ui.activity.BaseMvpActivity
import com.sukaidev.common.ui.adapter.BaseRecyclerViewAdapter
import com.sukaidev.order.R
import com.sukaidev.order.common.OrderConstant
import com.sukaidev.order.data.protocol.ShipAddress
import com.sukaidev.order.event.SelectAddressEvent
import com.sukaidev.order.injection.component.DaggerShipAddressComponent
import com.sukaidev.order.injection.module.ShipAddressModule
import com.sukaidev.order.presenter.ShipAddressPresenter
import com.sukaidev.order.presenter.view.IShipAddressView
import com.sukaidev.order.ui.adapter.ShipAddressAdapter
import kotlinx.android.synthetic.main.activity_address.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/17.
 *
 */
class ShipAddressActivity : BaseMvpActivity<ShipAddressPresenter>(), IShipAddressView {

    private lateinit var mAdapter: ShipAddressAdapter

    override fun injectComponent() {
        DaggerShipAddressComponent
            .builder()
            .activityComponent(activityComponent)
            .shipAddressModule(ShipAddressModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Int {
        return R.layout.activity_address
    }

    override fun onBindView(savedInstanceState: Bundle?) {
        mAddressRv.layoutManager = LinearLayoutManager(this)
        mAdapter = ShipAddressAdapter(this)
        mAddressRv.adapter = mAdapter

        mAdapter.mOptClickListener = object : ShipAddressAdapter.OnOptClickListener {
            override fun onSetDefault(address: ShipAddress) {
                mPresenter.setDefaultAddress(address)
            }

            override fun onEdit(address: ShipAddress) {
                startActivity<ShipAddressEditActivity>(OrderConstant.KEY_SHIP_ADDRESS to address)
            }

            override fun onDelete(address: ShipAddress) {
                AlertView(
                    "删除",
                    "确定删除该地址？",
                    "取消",
                    null,
                    arrayOf("确定"),
                    this@ShipAddressActivity,
                    AlertView.Style.Alert,
                    OnItemClickListener { _, position ->
                        if (position == 0) {
                            mPresenter.deleteShipAddress(address.id)
                        }
                    }
                ).show()
            }
        }

        mAdapter.setOnItemClickListener(object :
            BaseRecyclerViewAdapter.OnItemClickListener<ShipAddress> {
            override fun onItemClick(item: ShipAddress, position: Int) {
                Bus.send(SelectAddressEvent(item))
                this@ShipAddressActivity.finish()
            }
        })

        mAddAddressBtn.onClick {
            startActivity<ShipAddressEditActivity>()
        }
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        mPresenter.getShipAddressList()
    }


    override fun onGetShipAddressListResult(result: MutableList<ShipAddress>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.ViewState.CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.ViewState.EMPTY
        }
    }

    override fun onSetDefaultResult(result: Boolean) {
        toast("设置默认成功")
        loadData()
    }

    override fun onDeleteDefaultResult(result: Boolean) {
        toast("删除成功")
        loadData()
    }
}