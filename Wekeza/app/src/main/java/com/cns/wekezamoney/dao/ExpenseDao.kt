package com.cns.wekezamoney.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cns.wekezamoney.model.Expense

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("SELECT * FROM expense_table ORDER BY id ASC")
    fun getAllExpenses(): LiveData<List<Expense>>
}
