package com.cns.wekezamoney.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cns.wekezamoney.dao.UserDao
import com.cns.wekezamoney.model.Notification
import com.cns.wekezamoney.model.User

@Database(entities = [User::class, Notification::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var instance: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, UserDatabase::class.java, "wekeza_db")
                .fallbackToDestructiveMigration()
                .build()
    }
}
