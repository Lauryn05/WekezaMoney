package com.cns.wekezamoney.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cns.wekezamoney.database.UserDatabase
import com.cns.wekezamoney.model.Budget
import com.cns.wekezamoney.repository.BudgetRepository
import kotlinx.coroutines.launch

class BudgetViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BudgetRepository
    val allBudgets: LiveData<List<Budget>>

    init {
        val budgetDao = UserDatabase.getDatabase(application).budgetDao()
        repository = BudgetRepository(budgetDao)
        allBudgets = repository.allBudgets
    }

    fun insertBudget(budget: Budget) {
        viewModelScope.launch {
            repository.insert(budget)
        }
    }

//    fun updateBudget(budget: Budget) {
//        viewModelScope.launch {
//            repository.update(budget)
//        }
//    }
//
//    fun deleteBudget(budget: Budget) {
//        viewModelScope.launch {
//            repository.delete(budget)
//        }
//    }
}
