package com.arpackit.arfacator.ui.viewmodel

import androidx.lifecycle.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import com.arpackit.arfacator.data.Account


lateinit var accountList: MutableList<Account> //by remember { mutableStateOf(listOf<Account>()) }
    
class MainViewModel : ViewModel() {
    
    var accounts: MutableList<Account>
    
    init {
        accounts = mutableListOf(
            Account(1, "Github", "GJXQBQUHFFINHIVP", "Lorem ipsum dollor sumit"),
            Account(2, "Test Arfacator result", "ar2fammaryasser"),
            Account(3, "Reddit", "nvmlxld", "Hello world to my app"),
            Account(4, "X", "hdisixdhsks"),
            Account(5, "LinkedIn", "G2jDqTzKVBFaH33cPbVw"),
            Account(6, "Telegram", "p3oBPTayj92hs7DjG"),
        )
        accountList = accounts
    }
    
}