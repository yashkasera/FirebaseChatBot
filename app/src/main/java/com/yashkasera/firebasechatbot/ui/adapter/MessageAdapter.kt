package com.yashkasera.firebasechatbot.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yashkasera.firebasechatbot.databinding.ItemTextMessageBinding
import com.yashkasera.firebasechatbot.repository.model.MessageModel

/**
 * @author yashkasera
 * Created 22/04/22 at 10:54 PM
 */
class MessageAdapter :
    ListAdapter<MessageModel, MessageAdapter.MessageViewHolder>(MessageCallback) {
    object MessageCallback : DiffUtil.ItemCallback<MessageModel>() {
        override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean =
            oldItem.message == newItem.message


        override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean =
            oldItem == newItem
    }

    class MessageViewHolder(private val binding: ItemTextMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(messageModel: MessageModel) {
            (binding.card.layoutParams as ConstraintLayout.LayoutParams).horizontalBias =
                if (messageModel.sender == null) 1f else 0f
            binding.messageItem = messageModel
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): MessageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTextMessageBinding.inflate(layoutInflater, parent, false)
                return MessageViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder =
        MessageViewHolder.create(parent)


    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

