package com.example.internproject.presentation.recyclerview

import android.view.View

data class SignUpPage(
    val guide: String,
    val content: String,
    val onContentClick: ((View) -> Unit)? = null
)