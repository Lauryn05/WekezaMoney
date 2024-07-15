package com.cns.wekezamoney.database

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var instance: UserDatabase? = null

    fun getInstance(context: Context): UserDatabase {
        if (instance == null) {
            synchronized(UserDatabase::class) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "wekeza_db"
                ).build()
            }
        }
        return instance!!
    }
}
