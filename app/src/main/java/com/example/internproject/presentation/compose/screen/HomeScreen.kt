package com.example.internproject.presentation.compose.screen

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

@Composable
fun HomeScreen(

) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.success_login_str),
            style = Typography.bodyLarge,
            color = Black
        )
    }

}