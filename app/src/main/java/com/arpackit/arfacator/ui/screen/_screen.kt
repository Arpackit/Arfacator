package com.arpackit.arfacator.ui.screen


sealed class Screen(val route: String) {
    
    object Main: Screen("main_screen")
    
    object Form: Screen("form_screen") {
        val ID_KEY = "id"
        
        fun fullRoute() = "$route?edit={$ID_KEY}"
        
        fun editRouteOfId(id: Int) = "$route?edit=$id"
    }
    
    object Preferences: Screen("preferences_screen")
    
    object About: Screen("about_screen")
    
}