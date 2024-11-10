package com.example.internproject.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internproject.data.dto.SignUpDto
import com.example.internproject.presentation.ui_state.ResultUiState
import com.example.internproject.presentation.ui_state.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(

): ViewModel() {
    var name: String = ""
    var id: String = ""
    var pwd: String = ""
    var birth: String = ""

    private val _signUpUiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Init)
    val signUpUiState = _signUpUiState.asStateFlow()

    private val _duplicatingUiState = MutableStateFlow<ResultUiState>(ResultUiState.Init)
    val duplicatingUiState = _duplicatingUiState.asStateFlow()

    val closeFragmentFlag = MutableLiveData<Boolean>()

    fun reset() {
        name = ""
        id = ""
        pwd = ""
        birth = ""
    }

    fun trySignUp() {
        if(name.isEmpty() || id.isEmpty() || pwd.isEmpty() || birth.isEmpty()) {
            _signUpUiState.value = SignUpUiState.Failure
            return
        }

        viewModelScope.launch {
            val signUpDto = createSignUpDto()
            //TODO: 유즈케이스
            val result = true
        }
    }

    fun checkPwdConfirm(pwdConfirm: String) = (pwdConfirm == pwd)

    fun checkNameDuplicated(name: String) {
        viewModelScope.launch {

        }
    }
    fun checkIdDuplicated(id: String) {
        viewModelScope.launch {

        }
    }

    private fun createSignUpDto() = SignUpDto(
        loginId = id, pwd = pwd, name = name, birth = birth
    )
}