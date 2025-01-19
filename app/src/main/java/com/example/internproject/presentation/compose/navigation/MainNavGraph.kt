package com.example.internproject.presentation.compose.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.internproject.presentation.compose.screen.HomeScreen
import com.example.internproject.presentation.compose.screen.LoginScreen
import com.example.internproject.presentation.compose.screen.SignUpScreen

const val MainNavRoute = "main"
fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    navigation(
        route = MainNavRoute,
        startDestination = Destination.Login.route
    ) {
        composable(route = Destination.Home.route) {
            HomeScreen(

            )
        }

        composable(route = Destination.Login.route) {
            LoginScreen(
                toHomeScreen = { navController.navigate(Destination.Home.route) },
                toSignUpScreen = { navController.navigate(Destination.SignUp.route) }
            )
        }

        composable(route = Destination.SignUp.route) {
            SignUpScreen(
                popBackStack = { navController.popBackStack() }
            )
        }
    }
}