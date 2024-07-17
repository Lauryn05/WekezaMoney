package com.cns.wekezamoney.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cns.wekezamoney.model.Budget

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(budget: Budget)

    @Update
    suspend fun update(budget: Budget)

    @Delete
    suspend fun delete(budget: Budget)

    @Query("SELECT * FROM budgets ORDER BY id ASC")
    fun getAllBudgets(): LiveData<List<Budget>>
}
