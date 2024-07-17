package com.cns.wekezamoney.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.cns.wekezamoney.database.UserDatabase
import com.cns.wekezamoney.model.Goal
import com.cns.wekezamoney.repository.GoalRepository
import kotlinx.coroutines.launch

class GoalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GoalRepository
    val allGoals: LiveData<List<Goal>>
    val totalGoals: MediatorLiveData<Double> = MediatorLiveData()

    init {
        val goalDao = UserDatabase.getDatabase(application).goalDao()
        repository = GoalRepository(goalDao)
        allGoals = repository.allGoals

        totalGoals.addSource(allGoals) { goals ->
            totalGoals.value = goals.sumOf { it.targetAmount ?: 0.0 }
        }
    }

    fun insertGoal(goal: Goal) {
        viewModelScope.launch {
            repository.insert(goal)
        }
    }

    fun updateGoal(goal: Goal) {
        viewModelScope.launch {
            repository.update(goal)
        }
    }

    fun deleteGoal(goal: Any) {
        viewModelScope.launch {
            repository.delete(goal as Goal)
        }
    }
}
