package com.example.internproject.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.internproject.R
import com.example.internproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(this.layoutInflater)
    }
    private val navigationHelper by lazy {
        NavigationHelper(supportFragmentManager, R.id.fragment_cv)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun replaceFragment(fragment: Fragment) {
        navigationHelper.replaceFragment(fragment)
    }
    fun addFragment(fragment: Fragment) {
        navigationHelper.addFragment(fragment)
    }
    fun removeFragment(fragment: Fragment) {
        navigationHelper.removeFragment(fragment)
    }
}