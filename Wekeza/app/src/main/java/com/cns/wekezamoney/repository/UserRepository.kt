package com.cns.wekezamoney.repository

import com.cns.wekezamoney.dao.UserDao
import com.cns.wekezamoney.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun checkUser(username: String, password: String): User? {
        return userDao.checkUser(username, password)
    }
}
