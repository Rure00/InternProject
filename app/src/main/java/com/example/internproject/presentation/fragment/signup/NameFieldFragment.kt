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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.internproject.R
import com.example.internproject.databinding.FragmentNameFieldBinding
import com.example.internproject.presentation.MainActivity
import com.example.internproject.presentation.fragment.SignUpField
import com.example.internproject.presentation.utils.ValidateSignUp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NameFieldFragment : Fragment() {
    private var _binding: FragmentNameFieldBinding? = null
    private val binding get() = _binding!!

    private lateinit var nameValidateResult: ValidateSignUp.ValidateResult
    private val validate = ValidateSignUp()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNameFieldBinding.inflate(inflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            nameEt.doAfterTextChanged {
                val text = it.toString()
                Log.d("NameFieldFragment", "text: $text")

                doCheckName(text)
            }

            lifecycleScope.launch {
                delay(700)
                nameEt.requestFocus()
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(nameEt, InputMethodManager.SHOW_IMPLICIT)
            }

            nextBtn.setOnClickListener {
                findNavController().navigate(R.id.to_idFragment)
            }
        }
    }

    private fun doCheckName(name: String) = with(binding) {
        nameValidateResult = validate.doValidate(SignUpField.NAME, name)

        if(nameValidateResult != ValidateSignUp.ValidateResult.APPROVED) {
            wrongInputGuideTv.text = nameValidateResult.msg
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