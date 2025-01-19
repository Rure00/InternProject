package com.example.internproject.presentation.compose.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.internproject.R
import com.example.internproject.domain.results.DuplicatedResult
import com.example.internproject.domain.results.SignUpResult
import com.example.internproject.presentation.compose.component.KoreanTextField
import com.example.internproject.presentation.compose.screen.page.WriteIdPage
import com.example.internproject.presentation.compose.screen.page.WriteNamePage
import com.example.internproject.presentation.compose.screen.page.WritePwdCheckPage
import com.example.internproject.presentation.compose.screen.page.WritePwdPage
import com.example.internproject.presentation.ui_state.ResultUiState
import com.example.internproject.presentation.ui_state.SignUpUiState
import com.example.internproject.presentation.utils.SignUpField
import com.example.internproject.presentation.utils.ValidateSignUp
import com.example.internproject.presentation.viewmodels.SignUpViewModel
import com.example.internproject.ui.theme.Black
import com.example.internproject.ui.theme.TossBlue
import com.example.internproject.ui.theme.Typography
import com.example.internproject.ui.theme.White
import java.time.LocalDate

@Composable
fun SignUpScreen(
    popBackStack: () -> Unit,
    signUpViewModel: SignUpViewModel = hiltViewModel<SignUpViewModel>()
) {
    val nameState = remember { mutableStateOf("") }
    val idState = remember { mutableStateOf("") }
    val pwdState = remember { mutableStateOf("") }
    val pwdCheckState = remember { mutableStateOf("") }
    val birthState = remember { mutableStateOf(LocalDate.now()) }
    val activateNextButton = remember { mutableStateOf(false) }

    val validate = remember { ValidateSignUp() }

    val isValidated = remember { mutableStateOf(false) }

    val pageIndex = remember { mutableStateOf(0) }
    val pages = listOf<@Composable () -> Unit>(
        {
            WriteNamePage(
                onBackButton = {
                    pageIndex.value--
                },
                onNext = {
                    nameState.value = it
                    pageIndex.value++
                }
            )
        },
        { WriteIdPage(idState.value) {
            idState.value = it
            activateNextButton.value = (validate.doValidate(SignUpField.NAME, nameState.value) == ValidateSignUp.ValidateResult.APPROVED)
        } },
        { WritePwdPage(pwdState.value) {
            pwdState.value = it
            activateNextButton.value = (validate.doValidate(SignUpField.NAME, nameState.value) == ValidateSignUp.ValidateResult.APPROVED)
        } },
        { WritePwdCheckPage(pwdCheckState.value) {
            pwdCheckState.value = it
            activateNextButton.value = (validate.doValidate(SignUpField.NAME, nameState.value) == ValidateSignUp.ValidateResult.APPROVED)
        } }

    )

    fun toNextPage(context: Context) {
        if(pageIndex.value < pages.lastIndex) {
            pageIndex.value++
        } else {
            signUpViewModel.trySignUp()

            Toast.makeText(context, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            popBackStack()
        }

        isValidated.value = false
        activateNextButton.value = false
    }

    val signUpResultUiState = signUpViewModel.signUpUiState.collectAsState()
    when(signUpResultUiState.value) {
        is SignUpUiState.Success -> {
            popBackStack()
        }
        is SignUpUiState.Failure -> {
            Toast.makeText(LocalContext.current, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show()
        }
        is SignUpUiState.Loading -> {
            //TODO show Loading bar
        }
        else -> {

        }
    }

    val fieldValidateUiState = signUpViewModel.duplicatingUiState.collectAsState()
    when(fieldValidateUiState.value) {
        is ResultUiState.Success -> {
            toNextPage(LocalContext.current)
        }
        else -> {
            //TODO: 에러 가이드 띄우기
        }
    }

    BackHandler {
        if(pageIndex.value > 0) pageIndex.value--
        else popBackStack()
    }









    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        val context = LocalContext.current


        Spacer(modifier = Modifier.height(30.dp))

        Box(modifier = Modifier.weight(1f).fillMaxSize()) {
            pages[pageIndex.value]()
        }
    }

}

