package com.example.internproject.presentation.fragment.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.internproject.R
import com.example.internproject.databinding.FragmentSignUpBinding
import com.example.internproject.databinding.FragmentSignUpSuccessBinding
import com.example.internproject.presentation.ui_state.SignUpUiState
import com.example.internproject.presentation.viewmodels.SignUpViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignUpSuccessFragment : Fragment() {
    private var _binding: FragmentSignUpSuccessBinding? = null
    private val binding get() = _binding!!

    private val signUpViewModel by activityViewModels<SignUpViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpSuccessBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            Glide.with(requireContext())
                .load("https://cdn-icons-mp4.flaticon.com/512/6569/6569153.mp4")
                .into(successAni)

            backToLoginBtn.setOnClickListener {
                signUpViewModel.closeFragmentFlag.value = true
            }
            requireActivity().onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    signUpViewModel.closeFragmentFlag.value = true
                }
            })
        }

        lifecycleScope.launch {
            signUpViewModel.signUpUiState.collectLatest { state ->
                when(state) {
                    is SignUpUiState.Success -> {
                        Log.d("SignUpTry", "SignUpSuccess")
                        binding.backToLoginBtn.isEnabled = true
                    } SignUpUiState.Failure -> {
                    Log.d("SignUpTry", "Fail")
                        Toast.makeText(requireContext(), "회원가입에 실패하였습니다. 나중에 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                        signUpViewModel.closeFragmentFlag.value = true
                    } else -> {
                    Log.d("SignUpTry", "Else?")
                    }
                }
            }
        }

        signUpViewModel.trySignUp()
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}