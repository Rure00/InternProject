package com.example.internproject.presentation.compose.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.internproject.R
import com.example.internproject.ui.theme.Black
import com.example.internproject.ui.theme.Typography
import com.example.internproject.ui.theme.White

@Composable
fun HomeScreen(

) {
//stringResource(R.string.success_login_str)
    Log.d("HomeScreen", "Composition")
    Box(
        modifier = Modifier.fillMaxSize().background(color = White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "로그인 성공!",
            style = Typography.bodyLarge,
            color = Black
        )
    }

}