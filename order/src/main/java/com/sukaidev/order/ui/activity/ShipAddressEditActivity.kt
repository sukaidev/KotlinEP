package com.sukaidev.order.ui.activity

import android.os.Bundle
import com.sukaidev.common.ext.enable
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ui.activity.BaseMvpActivity
import com.sukaidev.order.R
import com.sukaidev.order.common.OrderConstant
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

    private var mAddress: ShipAddress? = null

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
        mAddress = intent.getParcelableExtra<ShipAddress>(OrderConstant.KEY_SHIP_ADDRESS)
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
        toast("添加地址成功！")
        finish()
    }

    override fun onEditShipAddress(result: Boolean) {
        toast("修改地址成功")
        finish()
    }
}