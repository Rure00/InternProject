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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.internproject.R
import com.example.internproject.databinding.FragmentBirthFieldBinding
import com.example.internproject.databinding.FragmentIdFieldBinding
import com.example.internproject.presentation.fragment.SignUpField
import com.example.internproject.presentation.utils.ValidateSignUp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IdFieldFragment : Fragment() {
    private var _binding: FragmentIdFieldBinding? = null
    private val binding get() = _binding!!

    private lateinit var idValidateResult: ValidateSignUp.ValidateResult
    private val validate = ValidateSignUp()

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
                findNavController().navigate(R.id.to_pwdFragment)
            }
        }
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