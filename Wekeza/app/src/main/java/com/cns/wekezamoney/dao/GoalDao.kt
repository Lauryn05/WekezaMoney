package com.cns.wekezamoney.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cns.wekezamoney.model.Goal

@Dao
interface GoalDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(goal: Goal)

    @Update
    suspend fun update(goal: Goal)

    @Delete
    suspend fun delete(goal: Goal)

    @Query("SELECT * FROM goal_table ORDER BY id ASC")
    fun getAllGoals(): LiveData<List<Goal>>
}
