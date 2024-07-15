package com.cns.wekezamoney.repository

import com.cns.wekezamoney.dao.UserDao
import com.cns.wekezamoney.model.User
import com.cns.wekezamoney.model.Notification

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun checkUser(username: String, password: String): User? {
        return userDao.checkUser(username, password)
    }

    suspend fun enableNotifications(userId: Int): Long {
        val notification = Notification(userId.toLong(), true)
        return userDao.insertNotification(notification)
    }

    suspend fun disableNotifications(userId: Int): Int {
        val notification = Notification(userId.toLong(), false)
        return userDao.updateNotification(notification)
    }

    suspend fun areNotificationsEnabled(userId: Int): Boolean {
        return userDao.areNotificationsEnabled(userId.toLong())
    }

    suspend fun updateUser(user: User): Int {
        return userDao.updateUser(user)
    }

    suspend fun getUserById(userId: Long): User? {
        return userDao.getUserById(userId)
    }
}
