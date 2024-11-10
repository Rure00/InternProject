package com.example.internproject.presentation.fragment.signup

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.internproject.R
import com.example.internproject.databinding.FragmentIdFieldBinding
import com.example.internproject.presentation.ui_state.ResultUiState
import com.example.internproject.presentation.utils.SignUpField
import com.example.internproject.presentation.utils.ValidateSignUp
import com.example.internproject.presentation.viewmodels.SignUpViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class IdFieldFragment : Fragment() {
    private var _binding: FragmentIdFieldBinding? = null
    private val binding get() = _binding!!

    private lateinit var idValidateResult: ValidateSignUp.ValidateResult
    private val validate = ValidateSignUp()

    private val signUpViewModel by activityViewModels<SignUpViewModel>()
    private lateinit var checkDuplicateJob: Job

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentIdFieldBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            idEt.doAfterTextChanged {
                val text = it.toString()
                Log.d("IdFieldFragment", "text: $text")

                doCheckId(text)
            }

            idEt.requestFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(idEt, InputMethodManager.SHOW_IMPLICIT)

            nextBtn.setOnClickListener {
                signUpViewModel.checkIdDuplicated(idEt.text.toString())
            }
        }

        signUpViewModel.initCheckDuplicateState()
    }

    override fun onStart() {
        super.onStart()

        checkDuplicateJob = lifecycleScope.launch {
            val onFail =  { msg: String ->
                binding.wrongInputGuideTv.text = msg
                binding.wrongInputGuideTv.visibility = View.VISIBLE
                changeNextButtonState(false)
            }

            signUpViewModel.duplicatingUiState.collectLatest { state ->
                when(state) {
                    is ResultUiState.Success -> {
                        if(state.isSuccess) {
                            signUpViewModel.id = binding.idEt.text.toString()
                            findNavController().navigate(R.id.to_idFragment)
                        } else {
                            onFail("중복된 ID입니다.")
                        }
                    }
                    is ResultUiState.Failure -> {
                        onFail("알 수 없는 이유로 실패하였습니다.")
                    }
                    else -> { }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        checkDuplicateJob.cancel()
    }

    private fun doCheckId(name: String) = with(binding) {
        idValidateResult = validate.doValidate(SignUpField.ID, name)

        if(idValidateResult != ValidateSignUp.ValidateResult.APPROVED) {
            wrongInputGuideTv.text = idValidateResult.msg
            wrongInputGuideTv.visibility = View.VISIBLE
            changeNextButtonState(false)
        } else {
            wrongInputGuideTv.visibility = View.INVISIBLE
            changeNextButtonState(true)
        }
    }

    private fun changeNextButtonState(isOn: Boolean) = with(binding.nextBtn) {
        isEnabled = isOn
        if(isOn) {
            setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
        } else {
            setTextColor(ResourcesCompat.getColor(resources, R.color.text_inactive, null))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}