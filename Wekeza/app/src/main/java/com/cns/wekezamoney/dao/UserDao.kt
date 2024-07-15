package com.cns.wekezamoney.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cns.wekezamoney.model.User
import com.cns.wekezamoney.model.Notification

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    suspend fun checkUser(username: String, password: String): User?

    @Query("SELECT * FROM user WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: Long): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: Notification): Long

    @Update
    suspend fun updateNotification(notification: Notification): Int

    @Query("SELECT enabled FROM notifications WHERE userId = :userId LIMIT 1")
    suspend fun areNotificationsEnabled(userId: Long): Boolean

    @Update
    suspend fun updateUser(user: User): Int
}
