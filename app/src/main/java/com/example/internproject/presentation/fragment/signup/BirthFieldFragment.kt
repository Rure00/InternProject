package com.example.internproject.presentation.fragment.signup

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.NumberPicker
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.internproject.R
import com.example.internproject.databinding.BirthBottomSheetBinding
import com.example.internproject.databinding.FragmentBirthFieldBinding
import com.example.internproject.presentation.viewmodels.SignUpViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BirthFieldFragment : Fragment() {
    private var _binding: FragmentBirthFieldBinding? = null
    private val binding get() = _binding!!

    private lateinit var dataPickBottomSheet: BirthBottomSheetBinding
    private lateinit var dataPickBottomSheetDialog: BottomSheetDialog

    private val signUpViewModel by activityViewModels<SignUpViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBirthFieldBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCalendarBottomSheet()

        lifecycleScope.launch {
            delay(700)
            dataPickBottomSheetDialog.show()
        }


        with(binding) {
            binding.birthEt.setOnClickListener {
                dataPickBottomSheetDialog.show()
            }
            nextBtn.setOnClickListener {
                signUpViewModel.birth = birthEt.text.toString()
                findNavController().navigate(R.id.to_successFragment)
            }
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initCalendarBottomSheet() {
        dataPickBottomSheet = BirthBottomSheetBinding.inflate(layoutInflater)
        dataPickBottomSheetDialog = BottomSheetDialog(requireContext())
        dataPickBottomSheetDialog.setContentView(dataPickBottomSheet.root)

        with(dataPickBottomSheet) {
            dataPickBottomSheet.datePk.init(2000, 0, 1) { _, year, month, day ->

            }

            dataPickBottomSheet.confirmBtn.setOnClickListener {
                with(datePk) {
                    val birthText = "${year}년 ${month+1}월 ${dayOfMonth}일"
                    binding.birthEt.setText(birthText)
                    dataPickBottomSheetDialog.dismiss()

                    binding.nextBtn.isEnabled = true
                }
            }

//            val textColor = ContextCompat.getColor(requireContext(), R.color.black) // 원하는 색상
//            val datePk = dataPickBottomSheet.datePk
//            for (i in 0 until datePk.childCount) {
//                val child = datePk.getChildAt(i)
//                if (child is ViewGroup) {
//                    for (j in 0 until child.childCount) {
//                        val innerChild = child.getChildAt(j)
//                        if (innerChild is NumberPicker) {
//                            innerChild.setTextColor(textColor) // 텍스트 색상 설정
//                        }
//                    }
//                }
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}