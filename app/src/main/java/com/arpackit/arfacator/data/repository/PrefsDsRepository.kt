package com.arpackit.arfacator.data.repository

import android.content.Context

import com.arpackit.arfacator.data.prefsds.PrefsDataStoreHelper
import com.arpackit.arfacator.data.prefsds.PrefsDataStoreKeys

 
class PrefsDataStoreRepository(context: Context) {
    
    private val prefsHelper = PrefsDataStoreHelper(context)
    
    
    suspend fun getViewAsGrid(defaultValue: Boolean) = 
        prefsHelper.getFirstPref(
            PrefsDataStoreKeys.VIEW_AS_GRID_KEY,
            defaultValue)
    
    suspend fun putViewAsGrid(value: Boolean) = 
        prefsHelper.putPref(
            PrefsDataStoreKeys.VIEW_AS_GRID_KEY,
            value)
    
    
    suspend fun getIsDarkTheme(defaultValue: Boolean) = 
        prefsHelper.getFirstPref(
            PrefsDataStoreKeys.IS_DARK_THEME_KEY,
            defaultValue)
    
    suspend fun putIsDarkTheme(value: Boolean) = 
        prefsHelper.putPref(
            PrefsDataStoreKeys.IS_DARK_THEME_KEY,
            value)
    
    
    suspend fun getFontId(defaultValue: Int) = 
        prefsHelper.getFirstPref(
            PrefsDataStoreKeys.FONT_ID_KEY,
            defaultValue)
    
    suspend fun putFontId(value: Int) = 
        prefsHelper.putPref(
            PrefsDataStoreKeys.FONT_ID_KEY,
            value)
    
}