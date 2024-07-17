package com.cns.wekezamoney.repository

import androidx.lifecycle.LiveData
import com.cns.wekezamoney.dao.GoalDao
import com.cns.wekezamoney.model.Goal

class GoalRepository(private val goalDao: GoalDao) {

    val allGoals: LiveData<List<Goal>> = goalDao.getAllGoals()

    suspend fun insert(goal: Goal) {
        goalDao.insert(goal)
    }

    suspend fun update(goal: Goal) {
        goalDao.update(goal)
    }

    suspend fun delete(goal: Goal) {
        goalDao.delete(goal)
    }
}