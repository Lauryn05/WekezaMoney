package com.cns.wekezamoney.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cns.wekezamoney.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    suspend fun checkUser(username: String, password: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long
}
