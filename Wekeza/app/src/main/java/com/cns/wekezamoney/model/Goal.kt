package com.cns.wekezamoney.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goal_table")
data class Goal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val targetAmount: Double?,
    val currentAmount: Double?
)
