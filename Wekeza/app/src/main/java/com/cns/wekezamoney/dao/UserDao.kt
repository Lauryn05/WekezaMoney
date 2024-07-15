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

    @Query(value = "SELECT * FROM user WHERE username = :username AND password = :password")
    suspend fun checkUser(username: String, password: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: Notification): Long

    @Update
    suspend fun updateNotification(notification: Notification): Int

    @Query(value = "SELECT enabled FROM notifications WHERE userId = :userId")
    suspend fun areNotificationsEnabled(userId: Int): Boolean
}
