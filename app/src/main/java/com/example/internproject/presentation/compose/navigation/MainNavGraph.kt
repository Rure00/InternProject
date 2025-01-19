package com.example.internproject.presentation.compose.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.internproject.presentation.compose.screen.HomeScreen
import com.example.internproject.presentation.compose.screen.LoginScreen
import com.example.internproject.presentation.compose.screen.SignUpScreen

fun NavGraphBuilder.mainNavGraph(navController: NavController, onScreenChanged: (Destination) -> Unit) {
    navigation(
        route = "main/",
        startDestination = Destination.Home.route
    ) {
        composable(route = Destination.Home.route) {
            HomeScreen(

            )
        }

        composable(route = Destination.Login.route) {
            LoginScreen(
                onLogin = { },
                onSignUp = { }
            )
        }

        composable(route = Destination.SignUp.route) {
            SignUpScreen(
                popBackStack = { navController.popBackStack() }
            )
        }
//
//        composable(route = Destination.Option.route) {
//            OptionScreen(
//                toAddMember = { navController.navigate(Destination.AddMember.route) },
//                toSaveAttendance = {  }
//            )
//            onScreenChanged(Destination.Option)
//        }
    }
}