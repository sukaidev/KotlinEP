package com.sukaidev.order.ui.fragment

import android.os.Bundle
import android.view.View
import com.eightbitlab.rxbus.Bus
import com.sukaidev.core.ext.enable
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.order.R
import com.sukaidev.order.common.OrderConstant
import com.sukaidev.order.data.protocol.ShipAddress
import com.sukaidev.order.event.EditAddressSuccessEvent
import com.sukaidev.order.injection.component.DaggerShipAddressComponent
import com.sukaidev.order.injection.module.ShipAddressModule
import com.sukaidev.order.presenter.EditShipAddressPresenter
import com.sukaidev.order.presenter.view.EditShipAddressView
import kotlinx.android.synthetic.main.delegate_edit_address.*
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/09/04.
 *
 */
class ShipAddressEditDelegate : BaseMvpDelegate<EditShipAddressPresenter>(), EditShipAddressView {

    private var mAddress: ShipAddress? = null

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
        return R.layout.delegate_edit_address
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initData()
        initView()
    }

    private fun initView() {
        mSaveBtn.enable(mShipNameEt) { isBtnEnable() }
        mSaveBtn.enable(mShipMobileEt) { isBtnEnable() }
        mSaveBtn.enable(mShipAddressEt) { isBtnEnable() }

        mSaveBtn.onClick {
            if (mAddress == null) {
                mPresenter.addShipAddress(
                    mShipNameEt.text.toString(),
                    mShipMobileEt.text.toString(),
                    mShipAddressEt.text.toString()
                )
            } else {
                mAddress!!.shipUserName = mShipNameEt.text.toString()
                mAddress!!.shipUserMobile = mShipMobileEt.text.toString()
                mAddress!!.shipAddress = mShipAddressEt.text.toString()
                mPresenter.editShipAddress(mAddress!!)
            }
        }
    }

    private fun initData() {
        arguments?.let {
            mAddress = arguments!!.getParcelable(OrderConstant.KEY_SHIP_ADDRESS)
        }
        mAddress?.let {
            mShipNameEt.setText(it.shipUserName)
            mShipMobileEt.setText(it.shipUserMobile)
            mShipAddressEt.setText(it.shipAddress)
        }
    }

    private fun isBtnEnable(): Boolean {
        return mShipNameEt.text.isNullOrEmpty().not()
                && mShipMobileEt.text.isNullOrEmpty().not()
                && mShipAddressEt.text.isNullOrEmpty().not()
    }

    override fun onAddShipAddressResult(result: Boolean) {
        context?.toast("添加地址成功！")
        Bus.send(EditAddressSuccessEvent())
        supportDelegate.pop()
    }

    override fun onEditShipAddress(result: Boolean) {
        context?.toast("添加地址成功！")
        Bus.send(EditAddressSuccessEvent())
        supportDelegate.pop()
    }
}