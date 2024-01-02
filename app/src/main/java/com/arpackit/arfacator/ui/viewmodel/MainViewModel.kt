package com.arpackit.arfacator.ui.viewmodel

import androidx.lifecycle.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import com.arpackit.arfacator.data.Account


class MainViewModel : ViewModel() {
    
    lateinit var accounts: MutableList<Account> //by remember { mutableStateOf(listOf<Account>()) }
    
    init {
        accounts = mutableListOf(
            Account(1, "Github", "bskkjddkke", "Lorem ipsum dollor sumit"),
            Account(2, "Telegram", "pwieidk"),
            Account(3, "Reddit", "nvmlxld", "Hello world to my app"),
            Account(4, "X", "hdisixdhsks"),
            Account(5, "Github", "xafwfsfsg"),
        )
    }
    
    
}