package com.example.internproject.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.internproject.R
import com.example.internproject.databinding.FragmentLoginBinding
import com.example.internproject.presentation.MainActivity
import com.example.internproject.presentation.dialog.LoadingDialog
import com.example.internproject.presentation.ui_state.ResultUiState
import com.example.internproject.presentation.viewmodels.UserViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val userViewModel by activityViewModels<UserViewModel>()
    private lateinit var loginJob: Job

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            loginBtn.setOnClickListener {
                onLogin(
                    idEt.text.toString(),
                    pwdEt.text.toString()
                )
            }
            signUpBtn.setOnClickListener {
                findNavController().navigate(R.id.to_signUpFragment)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        loginJob = lifecycleScope.launch {
            val loadingDialog = LoadingDialog(requireContext())
            userViewModel.resultUiState.collectLatest { state ->
                when(state) {
                    is ResultUiState.Success -> {
                        findNavController().navigate(R.id.to_mainFragment)
                    } is ResultUiState.Failure -> {
                        Toast.makeText(requireContext(), "로그인을 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }  else -> {}
                }

                if(state is ResultUiState.Loading) {
                    loadingDialog.show()
                } else loadingDialog.dismiss()
            }
        }
    }



    private fun onLogin(id: String, pwd: String) {
        userViewModel.tryLogin(id, pwd)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}