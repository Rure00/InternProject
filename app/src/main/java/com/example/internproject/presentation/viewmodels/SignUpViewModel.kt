package com.example.internproject.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internproject.data.dto.SignUpDto
import com.example.internproject.domain.results.DuplicatedResult
import com.example.internproject.domain.results.SignUpResult
import com.example.internproject.domain.usecase.CheckIdDuplicateUseCase
import com.example.internproject.domain.usecase.CheckNameDuplicateUseCase
import com.example.internproject.domain.usecase.SignUpUseCase
import com.example.internproject.presentation.ui_state.ResultUiState
import com.example.internproject.presentation.ui_state.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val checkIdUseCase: CheckIdDuplicateUseCase,
    private val checkNameUseCase: CheckNameDuplicateUseCase,
    private val signUpUseCase: SignUpUseCase
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
        _signUpUiState.value = SignUpUiState.Init

        name = ""
        id = ""
        pwd = ""
        birth = ""
    }
    fun initCheckDuplicateState() {
        _duplicatingUiState.value = ResultUiState.Init
    }

    fun trySignUp() {
        if(name.isEmpty() || id.isEmpty() || pwd.isEmpty() || birth.isEmpty()) {
            _signUpUiState.value = SignUpUiState.Failure
            return
        }

        viewModelScope.launch {
            val signUpDto = createSignUpDto()

            delay(1000)

            when(signUpUseCase.invoke(signUpDto)) {
                is SignUpResult.Success -> {
                    _signUpUiState.value = SignUpUiState.Success
                }
                is SignUpResult.Failure -> {
                    _signUpUiState.value = SignUpUiState.Failure
                }
            }
        }
    }

    fun checkNameDuplicated(name: String) {
        viewModelScope.launch {
            _duplicatingUiState.value = ResultUiState.Loading

            delay(1000)

            when(val result = checkNameUseCase.invoke(name)) {
                is DuplicatedResult.Success -> {
                    _duplicatingUiState.value = ResultUiState.Success(result.isSuccess)
                }
                is DuplicatedResult.Failure -> {
                    _duplicatingUiState.value = ResultUiState.Failure
                }
            }
        }
    }
    fun checkIdDuplicated(id: String) {
        viewModelScope.launch {
            _duplicatingUiState.value = ResultUiState.Loading

            delay(1000)

            when(val result = checkIdUseCase.invoke(id)) {
                is DuplicatedResult.Success -> {
                    _duplicatingUiState.value = ResultUiState.Success(result.isSuccess)
                }
                is DuplicatedResult.Failure -> {
                    _duplicatingUiState.value = ResultUiState.Failure
                }
            }
        }
    }

    private fun createSignUpDto() = SignUpDto(
        loginId = id, pwd = pwd, name = name, birth = birth
    )
}