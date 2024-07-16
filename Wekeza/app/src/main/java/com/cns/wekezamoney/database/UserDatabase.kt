package com.cns.wekezamoney.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cns.wekezamoney.dao.UserDao
import com.cns.wekezamoney.model.User
import com.cns.wekezamoney.model.Notification

@Database(entities = [User::class, Notification::class], version = 2)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "wekeza_db"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
