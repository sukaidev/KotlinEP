package com.sukaidev.provider.router

/**
 * Created by sukaidev on 2019/08/16.
 *
 */
object RouterPath {
    class User {
        companion object {
            const val PATH_LOGIN = "/user/login"
        }
    }

    class Order {
        companion object {
            const val PATH_ORDER_CONFIRM = "/order/confirm"
        }
    }

    class Pay {
        companion object{
            const val PATH_PAY = "/pay/pay"
        }
    }
}