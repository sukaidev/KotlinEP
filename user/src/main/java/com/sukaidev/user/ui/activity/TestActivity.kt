package com.sukaidev.user.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sukaidev.user.R
import org.jetbrains.anko.toast

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        toast("${intent.getIntExtra("id", -1)}")
    }
}
