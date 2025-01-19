package com.example.internproject.presentation.compose.navigation

sealed class Destination(
    val route: String
) {
    data object Login: Destination("login")

    data object SignUp: Destination( "sign_up")

    data object Home: Destination("home")
}