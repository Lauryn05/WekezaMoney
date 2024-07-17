package com.cns.wekezamoney.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cns.wekezamoney.dao.BudgetDao
import com.cns.wekezamoney.dao.ExpenseDao
import com.cns.wekezamoney.dao.GoalDao
import com.cns.wekezamoney.dao.UserDao
import com.cns.wekezamoney.model.Budget
import com.cns.wekezamoney.model.Expense
import com.cns.wekezamoney.model.Goal
import com.cns.wekezamoney.model.User
import com.cns.wekezamoney.model.Notification

@Database(entities = [User::class, Notification::class, Budget::class, Goal::class, Expense::class], version = 3, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun budgetDao(): BudgetDao
    abstract fun goalDao(): GoalDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "wekeza_db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
