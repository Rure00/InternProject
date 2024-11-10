package com.example.internproject.presentation.ui_state

sealed class SignUpUiState {
    data object Init: SignUpUiState()
    data object Loading: SignUpUiState()

    data object Success: SignUpUiState()
    data object Failure: SignUpUiState()
}