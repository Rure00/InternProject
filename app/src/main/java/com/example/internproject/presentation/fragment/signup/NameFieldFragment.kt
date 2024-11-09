package com.example.internproject.presentation.fragment.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import com.example.internproject.R
import com.example.internproject.databinding.FragmentNameFieldBinding
import com.example.internproject.presentation.MainActivity
import com.example.internproject.presentation.fragment.SignUpField
import com.example.internproject.presentation.utils.ValidateSignUp


class NameFieldFragment : Fragment() {
    private var _binding: FragmentNameFieldBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNameFieldBinding.inflate(inflater)
        return binding.root
    }

    private lateinit var nameValidateResult: ValidateSignUp.ValidateResult

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val validate = ValidateSignUp()

        with(binding) {
            nameEt.doAfterTextChanged {
                val text = it.toString()
                nameValidateResult = validate.doValidate(SignUpField.NAME, text)

                if(nameValidateResult != ValidateSignUp.ValidateResult.APPROVED) {
                    wrongInputGuideTv.text = nameValidateResult.msg
                    changeNextButtonState(false)
                } else {
                    changeNextButtonState(true)
                }
            }

            nextBtn.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .add(R.id.sign_up_fcv, IdFieldFragment())
                    .commit()

            }
        }

    }

    private fun changeNextButtonState(isOn: Boolean) = with(binding.nextBtn) {
        isClickable = isOn
        if(isOn) {
            background = ResourcesCompat.getDrawable(resources, R.color.toss_blue, null)
            setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
        } else {
            background = ResourcesCompat.getDrawable(resources, R.color.toss_blue_inactive, null)
            setTextColor(ResourcesCompat.getColor(resources, R.color.text_inactive, null))
        }
    }
    private fun onBack() {
        parentFragmentManager.beginTransaction()
            .remove(this@NameFieldFragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}