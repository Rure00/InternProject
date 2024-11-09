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
import androidx.recyclerview.widget.RecyclerView
import com.example.internproject.R
import com.example.internproject.databinding.FragmentSignUpBinding
import com.example.internproject.presentation.MainActivity
import com.example.internproject.presentation.recyclerview.SignUpPage
import com.example.internproject.presentation.recyclerview.SignUpPageAdapter


class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val signUpPageAdapter by lazy {
        SignUpPageAdapter()
    }

    private val onBackPressedCallback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            Log.d("SignUpFragment", "back clicked!")

            if(page == 0) {
                (requireActivity() as MainActivity).removeFragment(this@SignUpFragment)
            } else {
                if(fieldContentList.size == page+1) {
                    fieldContentList.removeLast()
                }
                binding.signUpVp.currentItem = --page
            }
        }
    }

    private val onBirthClick: (View) -> Unit = { et: View ->
        Log.d("SignUpFragment", "birth clicked! ${(et as EditText).hint.toString()}")
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
    private val fieldContentList = mutableListOf<String>()
    private var page = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding.signUpVp) {
            adapter = signUpPageAdapter
            signUpPageAdapter.submitList(signUpFieldList)
        }

        binding.nextBtn.setOnClickListener { onNext() }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    private fun onNext() {
        val viewHolder = (binding.signUpVp[0] as RecyclerView).findViewHolderForAdapterPosition(page)
                            as? SignUpPageAdapter.SignUpPageViewHolder
        val content = viewHolder?.binding!!.contentEt.text.toString()
        Log.d("SignUpFragment", "list size: ${signUpPageAdapter.currentList.size}")
        Log.d("SignUpFragment", "content: $content")
        fieldContentList.add(content)

//        val valdateResult =
//        if() {
//
//        }
        binding.signUpVp.currentItem = ++page
    }
    private fun onBack() {

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