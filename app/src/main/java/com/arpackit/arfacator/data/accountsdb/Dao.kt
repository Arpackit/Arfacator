package com.arpackit.arfacator.data.accountsdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import kotlinx.coroutines.flow.Flow

import com.arpackit.arfacator.data.model.Account


@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(account: Account)

    @Delete
    suspend fun delete(account: Account)

    @Query("SELECT * FROM accounts WHERE id = :id")
    fun getById(id: Int): Flow<Account>

    @Query("SELECT * FROM accounts")
    fun getAll(): Flow<List<Account>>
    
}