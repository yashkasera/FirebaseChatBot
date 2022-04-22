package com.yashkasera.firebasechatbot.ui.chat_bot

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yashkasera.firebasechatbot.repository.model.MessageModel
import com.yashkasera.firebasechatbot.repository.model.RetrofitService
import com.yashkasera.firebasechatbot.ui.adapter.MessageAdapter
import kotlinx.coroutines.launch


/**
 * @author yashkasera
 * Created 22/04/22 at 7:42 PM
 */

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val context by lazy {
        application.applicationContext
    }
    val isNetworkAvailable = ObservableBoolean(true)
    val isLoading = ObservableBoolean(false)
    val adapter = MessageAdapter()
    var messages: List<MessageModel> = emptyList()
        set(value) {
            field = value
            adapter.submitList(value)
        }
    private val retrofitService by lazy {
        RetrofitService.getInstance()
    }
    private val mutableList = mutableListOf<MessageModel>()

    fun sendMessage(message: String) {
        addToList(MessageModel(message, null, System.currentTimeMillis()))
        isLoading.set(true)
        viewModelScope.launch {
            try {
                val res = retrofitService.getKeywords(msg = message)
                isLoading.set(false)
                if (res.isSuccessful) {
                    res.body()?.let {
                        addToList(
                            MessageModel(
                                it.message,
                                "Alexa",
                                System.currentTimeMillis()
                            )
                        )
                    } ?: addToList(
                        MessageModel(
                            "Sorry, I didn't get that",
                            "Alexa",
                            System.currentTimeMillis()
                        )
                    )
                } else {
                    addToList(
                        MessageModel(
                            "Oops, Please try again!",
                            "Alexa",
                            System.currentTimeMillis()
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                addToList(
                    MessageModel(
                        "Sorry, I didn't get that",
                        "Alexa",
                        System.currentTimeMillis()
                    )
                )
                Toast.makeText(context, "An error occurred!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addToList(message: MessageModel) {
        mutableList.add(message)
        messages = mutableList
    }
}