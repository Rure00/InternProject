package com.example.internproject.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import com.example.internproject.R
import com.example.internproject.databinding.FragmentSignUpBinding
import com.example.internproject.presentation.recyclerview.SignUpPage
import com.example.internproject.presentation.recyclerview.SignUpPageAdapter


class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val onBirthClick = { et: View ->

    }
    private val signUpFieldList by lazy {
        listOf(
            SignUpPage(getString(R.string.name_guide_str), getString(R.string.name_str)),
            SignUpPage(getString(R.string.enter_id_str), getString(R.string.id_str)),
            SignUpPage(getString(R.string.pwd_guide_str), getString(R.string.pwd_str)),
            SignUpPage(getString(R.string.enter_pwd_check_str), getString(R.string.pwd_str)),
            SignUpPage(getString(R.string.birth_guide_str), getString(R.string.birth_str), onBirthClick),
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signUpPageAdapter = SignUpPageAdapter()
        with(binding.signUpVp) {
            adapter = signUpPageAdapter
            signUpPageAdapter.submitList(signUpFieldList)
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
    private fun changeButtonText(isNext: Boolean) = with(binding.nextBtn) {
        text = if(isNext) resources.getString(R.string.next_str)
            else resources.getString(R.string.confirm_str)

    }
}