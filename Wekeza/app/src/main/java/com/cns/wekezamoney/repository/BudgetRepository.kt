package com.cns.wekezamoney.repository

import com.cns.wekezamoney.dao.BudgetDao
import com.cns.wekezamoney.model.Budget

class BudgetRepository(private val budgetDao: BudgetDao) {

    val allBudgets = budgetDao.getAllBudgets()

    suspend fun insert(budget: Budget) {
        budgetDao.insert(budget)
    }

    suspend fun update(budget: Budget) {
        budgetDao.update(budget)
    }

    suspend fun delete(budget: Budget) {
        budgetDao.delete(budget)
    }
}
