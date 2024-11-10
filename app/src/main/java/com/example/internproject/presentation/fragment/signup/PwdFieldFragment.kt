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
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.example.internproject.R
import com.example.internproject.databinding.FragmentPwdConfirmFieldBinding
import com.example.internproject.databinding.FragmentPwdFieldBinding
import com.example.internproject.presentation.fragment.SignUpField
import com.example.internproject.presentation.utils.ValidateSignUp

class PwdFieldFragment : Fragment() {
    private var _binding: FragmentPwdFieldBinding? = null
    private val binding get() = _binding!!

    private lateinit var pwdValidateResult: ValidateSignUp.ValidateResult
    private val validate = ValidateSignUp()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPwdFieldBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            pwdEt.doAfterTextChanged {
                val text = it.toString()
                Log.d("PwdFieldFragment", "text: $text")

                doCheckPwd(text)
            }

            pwdEt.requestFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(pwdEt, InputMethodManager.SHOW_IMPLICIT)

            nextBtn.setOnClickListener {
                findNavController().navigate(R.id.to_pwdConfirmFragment)
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

    private fun doCheckPwd(name: String) = with(binding) {
        pwdValidateResult = validate.doValidate(SignUpField.PWD, name)

        if(pwdValidateResult != ValidateSignUp.ValidateResult.APPROVED) {
            wrongInputGuideTv.text = pwdValidateResult.msg
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