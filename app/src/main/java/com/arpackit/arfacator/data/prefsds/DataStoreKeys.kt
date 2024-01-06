package com.arpackit.arfacator.data.prefsds

import androidx.datastore.preferences.core.booleanPreferencesKey


object PrefsDataStoreKeys {

    val VIEW_AS_GRID_KEY = 
        booleanPreferencesKey("VIEW_AS_GRID")
        
    val IS_DARK_THEME_KEY = 
        booleanPreferencesKey("IS_DARK_THEME")
        
    val IS_POPPINS_FONT_KEY = 
        booleanPreferencesKey("IS_POPPINS_FONT")
        
}