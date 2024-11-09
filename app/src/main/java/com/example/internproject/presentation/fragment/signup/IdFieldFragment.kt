package com.example.internproject.presentation.fragment.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.internproject.R
import com.example.internproject.databinding.FragmentBirthFieldBinding
import com.example.internproject.databinding.FragmentIdFieldBinding

class IdFieldFragment : Fragment() {
    private var _binding: FragmentIdFieldBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentIdFieldBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}