package com.sukaidev.order.ui.activity

import android.os.Bundle
import com.sukaidev.common.ext.enable
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ui.activity.BaseMvpActivity
import com.sukaidev.order.R
import com.sukaidev.order.data.protocol.ShipAddress
import com.sukaidev.order.injection.component.DaggerShipAddressComponent
import com.sukaidev.order.injection.module.ShipAddressModule
import com.sukaidev.order.presenter.EditShipAddressPresenter
import com.sukaidev.order.presenter.view.IEditShipAddressView
import kotlinx.android.synthetic.main.activity_edit_address.*
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/17.
 *
 */
class ShipAddressEditActivity : BaseMvpActivity<EditShipAddressPresenter>(), IEditShipAddressView {


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
        return R.layout.activity_edit_address
    }

    override fun onBindView(savedInstanceState: Bundle?) {
        initView()
        loadData()
    }

    private fun initView() {
        mSaveBtn.enable(mShipNameEt) { isBtnEnable() }
        mSaveBtn.enable(mShipMobileEt) { isBtnEnable() }
        mSaveBtn.enable(mShipAddressEt) { isBtnEnable() }

        mSaveBtn.onClick {
            mPresenter.addShipAddress(
                mShipNameEt.text.toString(),
                mShipMobileEt.text.toString(),
                mShipAddressEt.text.toString()
            )
        }
    }

    private fun loadData() {

    }

    private fun isBtnEnable(): Boolean {
        return mShipNameEt.text.isNullOrEmpty().not()
                && mShipMobileEt.text.isNullOrEmpty().not()
                && mShipAddressEt.text.isNullOrEmpty().not()
    }

    override fun onAddShipAddressResult(result: Boolean) {
        toast("添加地址成功！")
        finish()
    }

    override fun onDeleteShipAddressResult(result: Boolean) {

    }

    override fun onEditShipAddress(result: Boolean) {

    }
}