package com.yashkasera.firebasechatbot.ui.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author yashkasera
 * Created 22/04/22 at 10:59 PM
 */
@BindingAdapter("messageAdapter")
fun setMessageAdapter(recyclerView: RecyclerView, adapter: MessageAdapter) {
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.setHasFixedSize(false)
}