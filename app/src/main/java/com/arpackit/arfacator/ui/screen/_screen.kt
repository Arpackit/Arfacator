package com.arpackit.arfacator.ui.screen


sealed class Screen(val route: String) {
    
    object Main: Screen("main_screen")
    
    object Creator: Screen("creator_screen") {
        fun routeWithId(id: Int) = "$route?id=$id"
    }
    
    object Preferences: Screen("preferences_screen")
    
    object About: Screen("about_screen")
    
}