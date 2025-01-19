package com.example.internproject.presentation.compose.screen.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.internproject.R
import com.example.internproject.presentation.compose.component.BirthPicker
import com.example.internproject.presentation.ui_state.ResultUiState
import com.example.internproject.presentation.utils.SignUpField
import com.example.internproject.presentation.utils.ValidateSignUp
import com.example.internproject.presentation.viewmodels.SignUpViewModel
import com.example.internproject.ui.theme.Black
import com.example.internproject.ui.theme.TossBlue
import com.example.internproject.ui.theme.Typography
import com.example.internproject.ui.theme.White
import java.time.LocalDate

@Composable
fun WriteBirthPage(
    onBackButton: () -> Unit,
    onNext: (LocalDate) -> Unit,
    signUpViewModel: SignUpViewModel = hiltViewModel<SignUpViewModel>()
) {
    val birthState = remember { mutableStateOf(LocalDate.now()) }
    val activateNextButton = remember { mutableStateOf(true) }

    val warningMsg = remember { mutableStateOf("") }
    val checkDuplicated = signUpViewModel.duplicatingUiState.collectAsState()
    when(checkDuplicated.value) {
        is ResultUiState.Success -> {
            onNext(birthState.value)
        }
        is ResultUiState.Failure -> {
            activateNextButton.value = false
        }
        else -> {

        }
    }

    Column(modifier = Modifier) {
        Image(
            painter = painterResource(R.drawable.back_btn),
            contentDescription = null,
            modifier = Modifier.size(40.dp).padding(start = 10.dp).clickable {
                onBackButton()
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(modifier = Modifier.padding(horizontal = 10.dp).weight(1f)) {
            Text(
                text = "생년월일을 입력해주세요.",
                style = Typography.titleLarge,
                color = Black
            )

            Spacer(modifier = Modifier.height(40.dp).fillMaxWidth())

            BirthPicker(
                date = birthState.value,
                modifier = Modifier.weight(1f),
                itemModifier = Modifier,
                visibleItemNum = 5,
                dividerColor = LightGray
            ) {
                birthState.value = it
            }
        }

        Box(
            modifier = Modifier.height(55.dp).fillMaxWidth().background(
                if(activateNextButton.value) TossBlue
                else LightGray
            ).clickable {
                onNext(birthState.value)
            },
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = "확인",
                style = Typography.bodyMedium,
                color = White,
                textAlign = TextAlign.Center
            )
        }
    }
}