package com.example.internproject.presentation.ui_state

sealed class ResultUiState {
    data object Init: ResultUiState()
    data object Loading: ResultUiState()
    data class Success(val isSuccess: Boolean): ResultUiState()
    data object Failure: ResultUiState()
}