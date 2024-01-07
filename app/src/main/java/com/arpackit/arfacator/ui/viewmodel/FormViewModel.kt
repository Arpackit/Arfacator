package com.arpackit.arfacator.ui.viewmodel

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

import kotlinx.coroutines.launch

import com.arpackit.arfacator.data.model.Account
import com.arpackit.arfacator.data.repository.AccountsDatabaseRepository


class FormScreenViewModel(private val app: Application) : AndroidViewModel(app) {
    
    private val repo = AccountsDatabaseRepository
        .build(app.applicationContext)
    
    
    fun saveAccount(account: Account) {
        viewModelScope.launch {
            repo.insertOrUpdateAccount(account)
        }
    }

    fun getAccountById(id: Int) = repo.getAccountById(id)
    
    
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as Application)
                
                FormScreenViewModel(app)
            }
        }
    }
}