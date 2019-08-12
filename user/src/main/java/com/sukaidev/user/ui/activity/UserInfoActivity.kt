package com.sukaidev.user.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.TResult
import com.sukaidev.common.ext.onClick
import com.sukaidev.common.ui.activity.BaseMvpActivity
import com.sukaidev.common.utils.DateUtils
import com.sukaidev.user.R
import com.sukaidev.user.injection.component.DaggerUserComponent
import com.sukaidev.user.injection.module.UserModule
import com.sukaidev.user.presenter.UserInfoPresenter
import com.sukaidev.user.presenter.view.UserInfoView
import kotlinx.android.synthetic.main.activity_user_info.*
import org.jetbrains.anko.toast
import java.io.File

/**
 * Created by sukaidev on 2019/08/10.
 * 用户信息.
 */
class UserInfoActivity : BaseMvpActivity<UserInfoPresenter>(), UserInfoView,
    TakePhoto.TakeResultListener {

    private lateinit var mTakePhoto: TakePhoto

    private lateinit var mTempFile: File

    override fun injectComponent() {
        DaggerUserComponent
            .builder()
            .activityComponent(activityComponent)
            .userModule(UserModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Int {
        return R.layout.activity_user_info
    }

    override fun onBindView(savedInstanceState: Bundle?) {

        mTakePhoto = TakePhotoImpl(this, this)
        mTakePhoto.onCreate(savedInstanceState)

        mUserIconView.onClick {
            checkPermission()
        }
    }

    private fun showAlertView() {

        AlertView(
            "设置头像",
            "从下列途径中选择一张照片",
            "取消",
            null,
            arrayOf("拍照", "相册"),
            this,
            AlertView.Style.ActionSheet,
            OnItemClickListener { _, position ->
                mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(), false)
                when (position) {
                    0 -> {
                        createTempFile()
                        mTakePhoto.onPickFromCapture(Uri.fromFile(mTempFile))
                    }
                    1 -> mTakePhoto.onPickFromGallery()
                }
            }).show()
    }

    override fun takeSuccess(result: TResult?) {
        Log.d("TakePhoto", result?.image?.originalPath)
        Log.d("TakePhoto", result?.image?.compressPath)
    }

    override fun takeCancel() {
    }

    override fun takeFail(result: TResult?, msg: String?) {
        Log.e("TakePhoto", msg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 创建拍照缓存文件
     */
    private fun createTempFile() {
        val tempFileName = "${DateUtils.curTime}.png"
        mTempFile = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            File(Environment.getExternalStorageDirectory(), tempFileName)
        } else {
            File(filesDir, tempFileName)
        }
    }

    /**
     * 获取相机拍摄读写权限
     */
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ), 1
            )
        } else {
            showAlertView()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showAlertView()
                } else {
                    toast("权限被拒绝")
                }
            }
        }
    }
}