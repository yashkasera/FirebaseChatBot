package com.yashkasera.firebasechatbot.repository.model

import com.google.gson.annotations.SerializedName

/**
 * @author yashkasera
 * Created 22/04/22 at 11:45 PM
 */
data class ApiResponseModel(
    @SerializedName("cnt")
    val message: String
)