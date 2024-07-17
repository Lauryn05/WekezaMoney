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

    suspend fun enableNotifications(userId: Long): Long {
        val notification = Notification(userId, true)
        return userDao.insertNotification(notification)
    }

    suspend fun disableNotifications(userId: Long): Int {
        return userDao.updateNotification(userId, false)
    }

    suspend fun areNotificationsEnabled(userId: Long): Boolean {
        return userDao.areNotificationsEnabled(userId)
    }

    suspend fun updateUser(user: User): Int {
        return userDao.updateUser(user)
    }

    suspend fun getUserById(userId: Long): User? {
        return userDao.getUserById(userId)
    }

}
