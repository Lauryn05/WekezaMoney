package com.cns.wekezamoney.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "wekeza_db"
        const val DATABASE_VERSION = 1

        // Expenses table and columns
        const val TABLE_EXPENSES = "expenses"
        const val COLUMN_EXPENSE_ID = "id"
        const val COLUMN_EXPENSE_NAME = "name"
        const val COLUMN_EXPENSE_AMOUNT = "amount"

        // Budgets table and columns
        const val TABLE_BUDGETS = "budgets"
        const val COLUMN_BUDGET_ID = "id"
        const val COLUMN_BUDGET_NAME = "name"
        const val COLUMN_BUDGET_AMOUNT = "amount"

        // Goals table and columns
        const val TABLE_GOALS = "goals"
        const val COLUMN_GOAL_ID = "id"
        const val COLUMN_GOAL_NAME = "name"
        const val COLUMN_GOAL_AMOUNT = "amount"
        const val COLUMN_GOAL_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_EXPENSES_TABLE = ("CREATE TABLE $TABLE_EXPENSES (" +
                "$COLUMN_EXPENSE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_EXPENSE_NAME TEXT," +
                "$COLUMN_EXPENSE_AMOUNT REAL)")

        val CREATE_BUDGETS_TABLE = ("CREATE TABLE $TABLE_BUDGETS (" +
                "$COLUMN_BUDGET_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_BUDGET_NAME TEXT," +
                "$COLUMN_BUDGET_AMOUNT REAL)")

        val CREATE_GOALS_TABLE = ("CREATE TABLE $TABLE_GOALS (" +
                "$COLUMN_GOAL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_GOAL_NAME TEXT," +
                "$COLUMN_GOAL_AMOUNT REAL," +
                "$COLUMN_GOAL_DATE TEXT)")

        db.execSQL(CREATE_EXPENSES_TABLE)
        db.execSQL(CREATE_BUDGETS_TABLE)
        db.execSQL(CREATE_GOALS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.run {
            execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSES")
            execSQL("DROP TABLE IF EXISTS $TABLE_BUDGETS")
            execSQL("DROP TABLE IF EXISTS $TABLE_GOALS")
            onCreate(this)
        }
    }

    fun addExpense(name: String, amount: Double): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EXPENSE_NAME, name)
            put(COLUMN_EXPENSE_AMOUNT, amount)
        }

        val id = db.insert(TABLE_EXPENSES, null, values)
        db.close()
        return id
    }

    fun addBudget(name: String, amount: Double): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_BUDGET_NAME, name)
            put(COLUMN_BUDGET_AMOUNT, amount)
        }

        val id = db.insert(TABLE_BUDGETS, null, values)
        db.close()
        return id
    }

    fun addGoal(name: String, amount: Double, date: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_GOAL_NAME, name)
            put(COLUMN_GOAL_AMOUNT, amount)
            put(COLUMN_GOAL_DATE, date)
        }

        val id = db.insert(TABLE_GOALS, null, values)
        db.close()
        return id
    }

    fun getExpenses(): List<Map<String, Any>> {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_EXPENSES, null, null, null, null, null, null)
        val expenses = mutableListOf<Map<String, Any>>()

        if (cursor.moveToFirst()) {
            do {
                val expense = mapOf(
                    COLUMN_EXPENSE_ID to cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_ID)),
                    COLUMN_EXPENSE_NAME to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_NAME)),
                    COLUMN_EXPENSE_AMOUNT to cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_AMOUNT))
                )
                expenses.add(expense)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return expenses
    }

    fun getBudgets(): List<Map<String, Any>> {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_BUDGETS, null, null, null, null, null, null)
        val budgets = mutableListOf<Map<String, Any>>()

        if (cursor.moveToFirst()) {
            do {
                val budget = mapOf(
                    COLUMN_BUDGET_ID to cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_ID)),
                    COLUMN_BUDGET_NAME to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_NAME)),
                    COLUMN_BUDGET_AMOUNT to cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_AMOUNT))
                )
                budgets.add(budget)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return budgets
    }

    fun getGoals(): List<Map<String, Any>> {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_GOALS, null, null, null, null, null, null)
        val goals = mutableListOf<Map<String, Any>>()

        if (cursor.moveToFirst()) {
            do {
                val goal = mapOf(
                    COLUMN_GOAL_ID to cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GOAL_ID)),
                    COLUMN_GOAL_NAME to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GOAL_NAME)),
                    COLUMN_GOAL_AMOUNT to cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_GOAL_AMOUNT)),
                    COLUMN_GOAL_DATE to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GOAL_DATE))
                )
                goals.add(goal)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return goals
    }
}
