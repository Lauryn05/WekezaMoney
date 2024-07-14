package com.cns.wekezamoney.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cns.wekezamoney.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun checkUser(username: String, password: String): User?
}
