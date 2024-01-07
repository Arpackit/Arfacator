package com.arpackit.arfacator.data.repository

import android.content.Context

import kotlinx.coroutines.flow.Flow

import com.arpackit.arfacator.data.accountsdb.*
import com.arpackit.arfacator.data.model.Account


class AccountsDatabaseRepository(private val accountDao: AccountDao) {
    
    suspend fun insertOrUpdateAccount(account: Account) = accountDao.insertOrUpdate(account)
    
    suspend fun deleteAccount(account: Account) = accountDao.delete(account)

    fun getAccountById(id: Int): Flow<Account> = accountDao.getById(id)
    
    fun getAllAccounts(): Flow<List<Account>> = accountDao.getAll()
    
    
    companion object {
        private var repo: AccountsDatabaseRepository? = null

        fun build(context: Context): AccountsDatabaseRepository {
            return repo ?: synchronized(this) {
                AccountsDatabaseRepository(
                    AccountDatabase
                        .getDatabase(context)
                        .accountDao()
                )
                .also { repo = it }
            }
        }
    }
    
}