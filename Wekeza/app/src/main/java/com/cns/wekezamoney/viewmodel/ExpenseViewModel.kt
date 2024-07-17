package com.cns.wekezamoney.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.cns.wekezamoney.database.UserDatabase
import com.cns.wekezamoney.model.Expense
import com.cns.wekezamoney.repository.ExpenseRepository
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExpenseRepository
    val allExpenses: LiveData<List<Expense>>
    val totalExpenses: MediatorLiveData<Double> = MediatorLiveData()

    init {
        val expenseDao = UserDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(expenseDao)
        allExpenses = repository.allExpenses

        totalExpenses.addSource(allExpenses) { expenses ->
            totalExpenses.value = expenses.sumOf { it.amount }
        }
    }

    fun insert(expense: Expense) = viewModelScope.launch {
        repository.insert(expense)
    }

    fun update(expense: Expense) = viewModelScope.launch {
        repository.update(expense)
    }

    fun delete(expense: Expense) = viewModelScope.launch {
        repository.delete(expense)
    }

}
