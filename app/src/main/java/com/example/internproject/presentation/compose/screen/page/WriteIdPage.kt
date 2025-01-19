package com.example.internproject.presentation.compose.screen.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.internproject.presentation.compose.component.KoreanTextField
import com.example.internproject.ui.theme.Black
import com.example.internproject.ui.theme.Typography

@Composable
fun WriteIdPage(idState: String, onChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        Text(
            text = "ID를 입력해주세요.",
            style = Typography.titleSmall,
            color = Black
        )

        Spacer(modifier = Modifier.height(12.dp).fillMaxWidth())

        KoreanTextField(
            value = idState,
            onValueChange = { onChange(it) },
        )
    }
}