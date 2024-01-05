package com.arpackit.arfacator

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
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
                    navController.navigate(Screen.Form.route)
                },
                onEditAccount = { id -> 
                    navController.navigate(Screen.Form.editRouteOfId(id))
                },
                onNavToPreferencesScreen = {
                    //navController.navigate(Screen.Preferences.route)
                },
                onNavToAboutScreen = {
                    navController.navigate(Screen.About.route)
                },
            )
        }
        
        composable(
            route = Screen.Form.fullRoute(),
            arguments = listOf(
                navArgument(Screen.Form.ID_KEY) {
                    type = NavType.IntType
                    defaultValue = -1
                },
            )
        ) {
            FormScreen(
                curAccId = it.arguments?.getInt(Screen.Form.ID_KEY),
                onNavBack = {
                    navController.popBackStack()
                },
            )
        }
        
        composable(route = Screen.About.route) {
            AboutScreen {
                navController.popBackStack()
            }
        }
    }
}