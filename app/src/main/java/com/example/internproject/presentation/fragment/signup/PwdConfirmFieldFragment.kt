package com.example.internproject.presentation.fragment.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.internproject.R
import com.example.internproject.databinding.FragmentIdFieldBinding
import com.example.internproject.databinding.FragmentPwdConfirmFieldBinding

class PwdConfirmFieldFragment : Fragment() {
    private var _binding: FragmentPwdConfirmFieldBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPwdConfirmFieldBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}