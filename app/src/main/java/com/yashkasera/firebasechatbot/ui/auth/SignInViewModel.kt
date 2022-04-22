package com.yashkasera.firebasechatbot.ui.auth

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

/**
 * @author yashkasera
 * Created 22/04/22 at 8:11 PM
 */
class SignInViewModel : ViewModel() {

    val isLoading = ObservableBoolean(false)
    val isOtpSent = ObservableBoolean(false)
}