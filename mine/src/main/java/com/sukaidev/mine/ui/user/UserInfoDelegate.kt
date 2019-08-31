package com.sukaidev.mine.ui.user

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.TResult
import com.qiniu.android.storage.UploadManager
import com.sukaidev.core.common.BaseConstant
import com.sukaidev.core.ext.onClick
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.core.utils.AppPrefsUtils
import com.sukaidev.core.utils.DateUtils
import com.sukaidev.core.utils.GlideUtils
import com.sukaidev.provider.common.ProviderConstant
import com.sukaidev.mine.R
import com.sukaidev.mine.data.protocol.UserInfo
import com.sukaidev.mine.injection.component.DaggerUserComponent
import com.sukaidev.mine.injection.module.UserModule
import com.sukaidev.mine.presenter.UserInfoPresenter
import com.sukaidev.mine.presenter.view.UserInfoView
import com.sukaidev.mine.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.delegate_user_info.*
import org.jetbrains.anko.toast
import java.io.File

/**
 * Created by sukaidev on 2019/08/30.
 * 用户信息界面.
 */
class UserInfoDelegate : BaseMvpDelegate<UserInfoPresenter>(), UserInfoView,
    TakePhoto.TakeResultListener {

    private lateinit var mTakePhoto: TakePhoto

    private lateinit var mTempFile: File

    private val mUploadManager: UploadManager by lazy {
        UploadManager()
    }

    private var mLocalFileUri: String? = null
    private var mRemoteFileUrl: String? = null

    private var mUserIcon: String? = null
    private var mUserName: String? = null
    private var mUserMobile: String? = null
    private var mUserGender: String? = null
    private var mUserSign: String? = null

    override fun injectComponent() {
        DaggerUserComponent
            .builder()
            .activityComponent(mActivityComponent)
            .userModule(UserModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Any {
        return R.layout.delegate_user_info
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

        mTakePhoto = TakePhotoImpl(this, this)
        mTakePhoto.onCreate(savedInstanceState)

        initData()
        initView()

    }

    /**
     * 初始化用户数据
     */
    private fun initData() {
        mUserIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
        mUserName = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        mUserMobile = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_MOBILE)
        mUserGender = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_GENDER)
        mUserSign = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)

        mRemoteFileUrl = mUserIcon

        if (mUserIcon != "") {
            GlideUtils.loadUrlImage(context!!, mUserIcon!!, mUserIconIv)
        }
        mRemoteFileUrl = mUserIcon

        if (mUserGender == "0") {
            mGenderMaleRb.isChecked = true
        } else {
            mGenderFemaleRb.isChecked = true
        }
        mUserNameEt.setText(mUserName)
        mUserSignEt.setText(mUserSign)
        mUserMobileTv.text = mUserMobile
    }

    private fun initView(){
        mUserIconView.onClick {
            checkPermission()
        }
        mHeaderBar.getLeftIv().onClick {
            pop()
        }
        mHeaderBar.getRightTv().onClick {
            context?.toast("点击了")
            mPresenter.editUser(
                mRemoteFileUrl!!,
                mUserNameEt.text?.toString() ?: "",
                if (mGenderMaleRb.isChecked) "0" else "1",
                mUserSignEt.text?.toString() ?: ""
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 显示头像Alert
     */
    private fun showAlertView() {
        AlertView(
            "设置头像",
            "从下列途径中选择一张照片",
            "取消",
            null,
            arrayOf("拍照", "相册"),
            context,
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
        mLocalFileUri = result?.image?.compressPath
        mPresenter.getUploadToken()
    }

    override fun takeCancel() {
    }

    override fun takeFail(result: TResult?, msg: String?) {
        Log.e("TakePhoto", msg)
    }

    /**
     * 创建拍照缓存文件
     */
    private fun createTempFile() {
        val tempFileName = "${DateUtils.curTime}.png"
        mTempFile = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            File(Environment.getExternalStorageDirectory(), tempFileName)
        } else {
            File(activity?.filesDir, tempFileName)
        }
    }

    /**
     * 获取相机拍摄读写权限
     */
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!, arrayOf(
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
                    context?.toast("权限被拒绝")
                }
            }
        }
    }

    override fun onGetUploadTokenResult(result: String) {
        // 将图片上传到七牛
        mUploadManager.put(
            mLocalFileUri, null, result,
            { key, info, response ->
                mRemoteFileUrl = BaseConstant.IMAGE_SERVER_ADDRESS + response?.get("hash")
                GlideUtils.loadUrlImage(context!!, mRemoteFileUrl!!, mUserIconIv)
            }, null
        )
    }

    override fun onEditUserResult(result: UserInfo) {
        context?.toast("修改成功")
        UserPrefsUtils.putUserInfo(result)
    }
}