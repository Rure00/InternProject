package com.example.internproject.presentation.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.internproject.R
import com.example.internproject.databinding.FragmentMainBinding
import com.example.internproject.databinding.FragmentSelectBinding
import com.example.internproject.presentation.compose.ComposeActivity


class SelectFragment : Fragment() {

    private var _binding: FragmentSelectBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSelectBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            xmlBtn.setOnClickListener {
                findNavController().navigate(R.id.to_loginFragment)
            }
            composeBtn.setOnClickListener {
                val intent = Intent(requireActivity(), ComposeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}