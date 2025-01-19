package com.example.internproject.presentation.compose.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.internproject.R
import com.example.internproject.presentation.ui_state.ResultUiState
import com.example.internproject.presentation.viewmodels.UserViewModel
import com.example.internproject.ui.theme.ButtonGray
import com.example.internproject.ui.theme.TossBlue
import com.example.internproject.ui.theme.TossGray
import com.example.internproject.ui.theme.Typography
import com.example.internproject.ui.theme.White

@Composable
fun LoginScreen(
    toHomeScreen: () -> Unit,
    toSignUpScreen: () -> Unit,
    userViewModel: UserViewModel = hiltViewModel<UserViewModel>()
) {
    val loginResultUiState = userViewModel.resultUiState.collectAsState()

    val idValue = remember {
        mutableStateOf("")
    }
    val pwdValue = remember {
        mutableStateOf("")
    }

    val onLoginButton = {
        userViewModel.tryLogin(
            idValue.value, pwdValue.value
        )
    }
    val onSignUpButton = {
        toSignUpScreen()
    }

    when(loginResultUiState.value) {
        is ResultUiState.Success -> {
            toHomeScreen()
        }
        is ResultUiState.Loading -> {
            //TODO: 로딩바 보여주기

        }
        is ResultUiState.Failure -> {
            Toast.makeText(LocalContext.current, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
        }
        else -> {

        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(White).padding(15.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.toss_logo),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "로그인",
            style = Typography.bodyLarge,
        )

        Spacer(modifier = Modifier.height(15.dp))

        BasicTextField(
            value = idValue.value,
            onValueChange = {
                idValue.value = it
            },
            modifier = Modifier.fillMaxWidth().wrapContentHeight().border(border = BorderStroke(1.dp, TossGray),
                shape = RoundedCornerShape(10.dp)
            ).padding(10.dp)
        ) {
            if(idValue.value.isEmpty()) {
                Text(
                    text = "id를 입력해주세요.",
                    style = Typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        BasicTextField(
            value = pwdValue.value,
            onValueChange = {
                pwdValue.value = it
            },
            modifier = Modifier.fillMaxWidth().wrapContentHeight().border(border = BorderStroke(1.dp, TossGray),
                shape = RoundedCornerShape(10.dp)
            ).padding(10.dp)
        ) {
            if(pwdValue.value.isEmpty()) {
                Text(
                    text = "비밀번호를 입력해주세요.",
                    style = Typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = TossBlue, shape = RoundedCornerShape(10.dp))
                .clickable {
                    onLoginButton()
                }
            ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "로그인",
                color = White,
                style = Typography.labelLarge,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "회원가입",
                color = ButtonGray,
                style = Typography.labelLarge,
                modifier = Modifier.padding(vertical = 10.dp).clickable {
                    onSignUpButton()
                }
            )
        }



    }
}