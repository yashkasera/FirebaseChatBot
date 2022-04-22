package com.yashkasera.firebasechatbot.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.yashkasera.firebasechatbot.MainActivity
import com.yashkasera.firebasechatbot.databinding.FragmentSignInBinding
import java.util.concurrent.TimeUnit

/**
 * @author yashkasera
 * Created 22/04/22 at 7:18 PM
 */
class SignInFragment : Fragment() {
    private lateinit var storedVerificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private val binding: FragmentSignInBinding by lazy {
        FragmentSignInBinding.inflate(layoutInflater)
    }
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }
    private val viewModel by lazy {
        ViewModelProvider(this).get(SignInViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.sendOtp.setOnClickListener {
            if (binding.phoneNumber.text.toString().isNotEmpty()) {
                binding.phoneNumber1.error = null
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(binding.phoneNumber.text.toString())
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(requireActivity())
                    .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                            signInWithPhoneAuthCredential(p0)
                        }

                        override fun onVerificationFailed(p0: FirebaseException) {
                            Snackbar.make(
                                binding.root,
                                "An error occurred - ${p0.message}",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            p0.printStackTrace()
                        }

                        override fun onCodeSent(
                            verificationId: String,
                            token: PhoneAuthProvider.ForceResendingToken
                        ) {
                            viewModel.isLoading.set(false)
                            viewModel.isOtpSent.set(true)
                            storedVerificationId = verificationId
                            resendToken = token
                        }

                    })
                    .build()
                viewModel.isLoading.set(true)
                PhoneAuthProvider.verifyPhoneNumber(options)
            } else {
                binding.phoneNumber1.error = "Phone number cannot be empty"
            }
        }
        binding.start.setOnClickListener {
            if (binding.otp.text.toString().isNotEmpty()) {
                binding.otp1.error = null
                val credential = PhoneAuthProvider.getCredential(
                    storedVerificationId,
                    binding.otp.text.toString()
                )
                viewModel.isLoading.set(true)
                signInWithPhoneAuthCredential(credential)
            } else {
                binding.otp1.error = "OTP cannot be empty"
            }
        }
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
            if (it.isSuccessful) {
                viewModel.isLoading.set(false)
                 MainActivity.start(requireActivity()).also {
                     requireActivity().finish()
                 }
            } else {
                Log.w("SignInFragment", "signInWithCredential:failure", it.exception)
            }
        }
    }

}