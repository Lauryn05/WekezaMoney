package com.cns.wekezamoney.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cns.wekezamoney.dao.UserDao
import com.cns.wekezamoney.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
