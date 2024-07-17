package com.cns.wekezamoney.repository

import androidx.lifecycle.LiveData
import com.cns.wekezamoney.dao.ExpenseDao
import com.cns.wekezamoney.model.Expense

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    val allExpenses: LiveData<List<Expense>> = expenseDao.getAllExpenses()

    suspend fun insert(expense: Expense) {
        expenseDao.insert(expense)
    }

    suspend fun update(expense: Expense) {
        expenseDao.update(expense)
    }

    suspend fun delete(expense: Expense) {
        expenseDao.delete(expense)
    }
}
