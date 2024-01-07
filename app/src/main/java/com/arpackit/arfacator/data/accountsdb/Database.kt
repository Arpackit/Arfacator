package com.arpackit.arfacator.data.accountsdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.arpackit.arfacator.data.model.Account


@Database(
    entities = [Account::class],
    version = 1, 
    exportSchema = false
)
abstract class AccountDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {
        @Volatile
        private var Instance: AccountDatabase? = null

        fun getDatabase(context: Context): AccountDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                        context, 
                        AccountDatabase::class.java, 
                        "arfacator_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}