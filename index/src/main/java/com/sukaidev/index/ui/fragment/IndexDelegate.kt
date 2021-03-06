package com.sukaidev.index.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.sukaidev.core.common.PermissionConstant
import com.sukaidev.core.event.GoodsClickedEvent
import com.sukaidev.core.event.RequestPermissionSuccess
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ext.toast
import com.sukaidev.core.ui.delegates.ProxyDelegate
import com.sukaidev.core.ui.delegates.ProxyMvpDelegate
import com.sukaidev.core.ui.recycler.BaseDecoration
import com.sukaidev.core.ui.recycler.MultipleFields
import com.sukaidev.core.ui.recycler.MultipleItemEntity
import com.sukaidev.core.ui.recycler.MultipleRecyclerAdapter
import com.sukaidev.index.R
import com.sukaidev.index.injection.component.DaggerIndexComponent
import com.sukaidev.index.injection.module.IndexModule
import com.sukaidev.index.presenter.IndexPresenter
import com.sukaidev.index.presenter.view.IndexView
import com.sukaidev.index.ui.adapter.IndexDataConverter
import com.sukaidev.index.ui.adapter.SearchGoodsDelegate
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.delegate_index.*
import org.jetbrains.anko.toast

/**
 * Created by sukaidev on 2019/08/26.
 * 主页Fragment.
 */
class IndexDelegate : ProxyMvpDelegate<IndexPresenter>(), IndexView {

    private lateinit var mAdapter: MultipleRecyclerAdapter

    private var mBanner: Banner? = null

    override fun injectComponent() {
        DaggerIndexComponent
            .builder()
            .activityComponent(mActivityComponent)
            .indexModule(IndexModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Any {
        return R.layout.delegate_index
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        initRecyclerView()
        mSearchTv.onClick {
            getParentDelegate<ProxyDelegate>().supportDelegate.start(SearchGoodsDelegate())
        }

        mScanIv.onClick {
            checkPermission()
        }

        Bus.observe<RequestPermissionSuccess>()
            .subscribe{
                if (it.requestCode == PermissionConstant.QRCODE_SCANNER_PERMISSION_REQUEST_CODE)
                    getParentDelegate<ProxyDelegate>().supportDelegate.start(ScannerDelegate())
            }
            .registerInBus(this)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        mPresenter.getIndexData()
    }

    private fun initRecyclerView() {
        val manager = GridLayoutManager(context, 4)
        mIndexRv.layoutManager = manager
        mIndexRv.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(context!!, R.color.white), 10))
    }

    override fun onGetIndexDataResult(result: String) {
        mAdapter = MultipleRecyclerAdapter.create(IndexDataConverter().setJsonData(result))
        mIndexRv.adapter = mAdapter
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val itemId: Int = (adapter.getItem(position) as MultipleItemEntity).getField(MultipleFields.ID)
            if (itemId > 0) {
                Bus.send(GoodsClickedEvent(itemId))
//                supportDelegate.startWithNewBundle<GoodsDetailDelegate>(GoodsConstant.KEY_GOODS_ID to itemId)
            }
        }
        mBanner = mAdapter.getViewByPosition(0, R.id.mIndexBanner) as Banner
    }

    override fun onResume() {
        super.onResume()
        mBanner?.startAutoPlay()
    }

    override fun onPause() {
        super.onPause()
        mBanner?.stopAutoPlay()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity!!, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ), PermissionConstant.QRCODE_SCANNER_PERMISSION_REQUEST_CODE
            )
        } else {
            getParentDelegate<ProxyDelegate>().supportDelegate.start(ScannerDelegate())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}