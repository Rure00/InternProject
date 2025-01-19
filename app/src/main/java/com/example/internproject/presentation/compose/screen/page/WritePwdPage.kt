package com.example.internproject.presentation.compose.screen.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.internproject.R
import com.example.internproject.ui.theme.Black
import com.example.internproject.ui.theme.Typography

@Composable
fun WritePwdPage(pwdState: String, onChange: (String) -> Unit) {
    val isShow = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        Text(
            text = "비밀번호를 입력해주세요.",
            style = Typography.titleSmall,
            color = Black
        )

        Spacer(modifier = Modifier.height(12.dp).fillMaxWidth())

        Box {
            TextField(
                value = pwdState,
                onValueChange = { onChange(it) },
                keyboardOptions = KeyboardOptions(keyboardType = if(isShow.value) KeyboardType.Text else KeyboardType.Password)
            )

            val icon = if(isShow.value) R.drawable.show
            else R.drawable.hide
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.clickable {
                    isShow.value = !isShow.value
                }
            )
        }
    }
}