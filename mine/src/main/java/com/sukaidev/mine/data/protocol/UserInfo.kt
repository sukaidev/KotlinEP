package com.sukaidev.mine.data.protocol

/**
 * Created by sukaidev on 2019/08/12.
 * 用户实体类.
 */
data class UserInfo(
    val id: String,
    val userIcon: String,
    val userName: String,
    val userGender: String,
    val userMobile: String,
    val userSign: String
)