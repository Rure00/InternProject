package com.example.internproject.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlin.properties.Delegates

class NavigationHelper(
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) {
    fun replaceFragment(fragment: Fragment) {
        fragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }
    fun addFragment(fragment: Fragment) {
        fragmentManager.beginTransaction()
            .add(containerId, fragment)
            .commit()
    }
    fun removeFragment(fragment: Fragment) {
        fragmentManager.beginTransaction()
            .remove(fragment)
            .commit()
    }
}