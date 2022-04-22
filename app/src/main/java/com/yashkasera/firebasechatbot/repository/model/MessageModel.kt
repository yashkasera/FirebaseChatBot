package com.yashkasera.firebasechatbot.repository.model

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author yashkasera
 * Created 22/04/22 at 10:47 PM
 */
data class MessageModel(
    val message: String,
    val sender: String? = null,
    val timestamp: Long
) {
    @SuppressLint("SimpleDateFormat")
    fun getTime(): String =
        SimpleDateFormat("hh:mm a").format(Date(timestamp))
}
