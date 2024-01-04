package com.arpackit.arfacator.ui.viewmodel

import androidx.lifecycle.ViewModel

import com.arpackit.arfacator.data.Account


class FormViewModel : ViewModel() {
    
    fun find(id: Int): Account = accountList.get(id)
   
    fun add(account: Account) = accountList.add(account)
    
}