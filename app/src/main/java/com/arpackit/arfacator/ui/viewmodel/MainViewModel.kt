package com.arpackit.arfacator.ui.viewmodel

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

import kotlinx.coroutines.launch

import com.arpackit.arfacator.data.model.Account
import com.arpackit.arfacator.data.repository.AccountsDatabaseRepository


class MainScreenViewModel(private val app: Application) : AndroidViewModel(app) {
    
    private val repo = AccountsDatabaseRepository
        .build(app.applicationContext)
    
    private val _accounts = MutableLiveData<List<Account>>()
    val accounts: LiveData<List<Account>> get() = _accounts
    
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading
    
    
    init { fetchAccounts() }
    
    
    fun fetchAccounts() {
        _loading.postValue(true)
        
        viewModelScope.launch {
            repo.getAllAccounts().collect {
                _accounts.postValue(it)
                _loading.postValue(false)
            }
        }
    }
    
    fun deleteAccount(account: Account) {
        viewModelScope.launch {
            repo.deleteAccount(account)
        }
    }
    
    
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as Application)
                
                MainScreenViewModel(app)
            }
        }
    }
}