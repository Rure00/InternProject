package com.example.internproject.presentation.fragment.signup

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.internproject.R
import com.example.internproject.databinding.FragmentIdFieldBinding
import com.example.internproject.databinding.FragmentPwdConfirmFieldBinding
import com.example.internproject.presentation.viewmodels.SignUpViewModel

class PwdConfirmFieldFragment : Fragment() {
    private var _binding: FragmentPwdConfirmFieldBinding? = null
    private val binding get() = _binding!!

    private val signUpViewModel by activityViewModels<SignUpViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPwdConfirmFieldBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            pwdEt.doAfterTextChanged {
                val text = it.toString()
                Log.d("PwdFieldFragment", "text: $text")

                doCheckPwdConfirm(text)
            }

            pwdEt.requestFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(pwdEt, InputMethodManager.SHOW_IMPLICIT)

            nextBtn.setOnClickListener {
                findNavController().navigate(R.id.to_birthFragment)
            }
            showPwdBtn.setOnClickListener {
                it.isSelected = !it.isSelected
                pwdEt.inputType =  if(it.isSelected) {
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                } else {
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }

                pwdEt.setSelection(pwdEt.text.length)
            }
        }
    }

    private fun doCheckPwdConfirm(pwd: String) = with(binding) {
        nextBtn.isEnabled = (signUpViewModel.pwd == pwd)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}