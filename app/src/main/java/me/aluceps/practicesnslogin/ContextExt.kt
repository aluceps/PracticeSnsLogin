package me.aluceps.practicesnslogin

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String, length: Int) {
    Toast.makeText(this, message, length).show()
}