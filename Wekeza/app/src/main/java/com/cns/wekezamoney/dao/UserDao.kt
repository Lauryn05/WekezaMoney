package com.cns.wekezamoney.dao

import androidx.room.*
import com.cns.wekezamoney.model.User
import com.cns.wekezamoney.model.Notification

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    suspend fun checkUser(username: String, password: String): User?

    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUserById(userId: Long): User?

    @Query("SELECT enabled FROM notifications WHERE userId = :userId")
    suspend fun areNotificationsEnabled(userId: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: Notification): Long

    @Query("UPDATE notifications SET enabled = :enabled WHERE userId = :userId")
    suspend fun updateNotification(userId: Long, enabled: Boolean): Int

    @Query("DELETE FROM notifications WHERE userId = :userId")
    suspend fun deleteNotification(userId: Long): Int
}
