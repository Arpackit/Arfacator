package com.arpackit.arfacator

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
//import androidx.navigation.NavType
//import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.arpackit.arfacator.ui.screen.*


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController, 
        startDestination = Screen.Main.route
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(
                onAddAccount = {
                    //navController.navigate(Screen.Creator.route)
                },
                onEditAccount = { id -> 
                    //navController.navigate(Screen.Creator.routeWithId(id))
                },
                onNavToPreferencesScreen = {
                    //navController.navigate(Screen.Preferences.route)
                },
                onNavToAboutScreen = {
                    //navController.navigate(Screen.About.route)
                },
            )
        }
    }
}