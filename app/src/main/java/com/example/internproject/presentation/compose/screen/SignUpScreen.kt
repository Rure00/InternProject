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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
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


    val pageIndex = remember { mutableStateOf(0) }
    val pages = listOf<@Composable () -> Unit>(
        {
            WriteNamePage(
                value = nameState.value,
                onBackButton = {
                    popBackStack()
                },
                onNext = {
                    nameState.value = it
                    pageIndex.value += 1

                    signUpViewModel.initCheckDuplicateState()
                }
            )
        },
        {
            WriteIdPage(
                value = idState.value,
                onBackButton = {
                    pageIndex.value--
                },
                onNext = {
                    idState.value = it
                    pageIndex.value += 1

                    signUpViewModel.initCheckDuplicateState()
                }
            )
        },
        {
            WritePwdPage(
                value = pwdState.value,
                onBackButton = {
                    pageIndex.value--
                },
                onNext = {
                    pwdState.value = it
                    pageIndex.value += 1

                    signUpViewModel.initCheckDuplicateState()
                }
            )
        },
        {
            WritePwdCheckPage(
                value = pwdCheckState.value,
                pwd = pwdState.value,
                onBackButton = {
                    pageIndex.value--
                },
                onNext = {
                    pwdCheckState.value = it
                    pageIndex.value += 1

                    signUpViewModel.initCheckDuplicateState()
                }
            )
        }

    )

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

    val checkDuplicatedUiState = signUpViewModel.duplicatingUiState.collectAsState()

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

    if(checkDuplicatedUiState.value == ResultUiState.Loading || signUpResultUiState.value == SignUpUiState.Loading) {
        Box(modifier = Modifier.fillMaxSize().background(color = LightGray.copy(alpha = 0.5f))) {
            LoadingDialog()
        }
    }
}

@Composable
private fun LoadingDialog() {
    Dialog(
        onDismissRequest = {  },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    ) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0f)
        CircularProgressIndicator(
        )
    }
}

