package com.yashkasera.firebasechatbot.ui.chat_bot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yashkasera.firebasechatbot.AuthActivity
import com.yashkasera.firebasechatbot.MainActivity.Companion.auth
import com.yashkasera.firebasechatbot.R
import com.yashkasera.firebasechatbot.databinding.FragmentChatBinding

/**
 * @author yashkasera
 * Created 22/04/22 at 7:29 PM
 */
class ChatBotFragment : Fragment() {
    private val binding: FragmentChatBinding by lazy { FragmentChatBinding.inflate(layoutInflater) }

    private val viewModel: ChatViewModel by lazy { ViewModelProvider(this).get(ChatViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.message.doOnTextChanged { _, _, _, count ->
            binding.message1.isErrorEnabled = count != 0
        }
        binding.send.setOnClickListener {
            if (binding.message.text.toString().isNotEmpty()) {
                viewModel.sendMessage(binding.message.text.toString())
                binding.message.text = null
            } else {
                binding.message1.error = "Cannot be empty!"
            }
        }
        binding.options.setOnClickListener {
            PopupMenu(requireContext(), it).apply {
                inflate(R.menu.chat_bot_menu)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menu_logout -> auth.signOut().also {
                            AuthActivity.start(requireContext())
                            requireActivity().finish()
                        }
                    }
                    true
                }
                show()
            }
        }
    }
}