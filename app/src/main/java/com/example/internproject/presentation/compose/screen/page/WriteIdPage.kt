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
import androidx.compose.material3.TextField
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
import com.example.internproject.presentation.compose.component.KoreanTextField
import com.example.internproject.presentation.ui_state.ResultUiState
import com.example.internproject.presentation.utils.SignUpField
import com.example.internproject.presentation.utils.ValidateSignUp
import com.example.internproject.presentation.viewmodels.SignUpViewModel
import com.example.internproject.ui.theme.Black
import com.example.internproject.ui.theme.TossBlue
import com.example.internproject.ui.theme.Typography
import com.example.internproject.ui.theme.White

@Composable
fun WriteIdPage(
    value: String,
    onBackButton: () -> Unit,
    onNext: (String) -> Unit,
    signUpViewModel: SignUpViewModel = hiltViewModel<SignUpViewModel>()
) {

    val idState = remember { mutableStateOf(value) }
    val activateNextButton = remember { mutableStateOf(false) }

    val warningMsg = remember { mutableStateOf("") }
    val checkDuplicated = signUpViewModel.duplicatingUiState.collectAsState()
    when(checkDuplicated.value) {
        is ResultUiState.Success -> {
            onNext(idState.value)
        }
        is ResultUiState.Failure -> {
            activateNextButton.value = false
        }
        else -> {

        }
    }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    fun validateId() {
        val validate = ValidateSignUp()
        val result = validate.doValidate(SignUpField.ID, idState.value)

        if(result == ValidateSignUp.ValidateResult.APPROVED) {
            activateNextButton.value = true
            warningMsg.value = ""
        } else {
            activateNextButton.value = false
            warningMsg.value = result.msg
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
                text = "ID를 입력해주세요.",
                style = Typography.titleLarge,
                color = Black
            )

            Spacer(modifier = Modifier.height(40.dp).fillMaxWidth())

            BasicTextField(
                value = idState.value,
                onValueChange = {
                    idState.value = it
                    validateId()
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp).focusRequester(focusRequester),
                textStyle = Typography.bodyMedium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.padding(top = 3.dp).fillMaxWidth().height(1.dp).background(LightGray))

            Spacer(modifier = Modifier.height(5.dp))

            if(warningMsg.value.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.warning_icon),
                        contentDescription = null
                    )
                    Text(
                        text = warningMsg.value,
                        style = Typography.labelSmall,
                        color = Red
                    )
                }
            }
        }



        Box(
            modifier = Modifier.height(55.dp).fillMaxWidth().background(
                if(activateNextButton.value) TossBlue
                else LightGray
            ).clickable {
                if(!activateNextButton.value) return@clickable
                signUpViewModel.checkIdDuplicated(idState.value)
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