package com.example.internproject.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.internproject.R
import com.example.internproject.databinding.FragmentSignUpBinding
import com.example.internproject.presentation.MainActivity
import com.example.internproject.presentation.ui_state.ResultUiState
import com.example.internproject.presentation.utils.ValidateSignUp
import com.example.internproject.presentation.viewmodels.SignUpViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val signUpViewModel by activityViewModels<SignUpViewModel>()

    //TODO
    // viewModel에서 page observer 달아서 데이터 입력이 완료되면
    // Loading Dialog 부른 뒤 remove 하기

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpViewModel.reset()

        lifecycleScope.launch {
            signUpViewModel.duplicatingUiState.collectLatest { state ->
                when(state) {
                    is ResultUiState.Loading -> {
                        //TODO: 로딩화면 띄우기
                    }
                    else -> {
                        //TODO: 로딩화면 없애기
                    }
                }
            }
        }

        signUpViewModel.closeFragmentFlag.observe(viewLifecycleOwner) {
            if(it) {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}