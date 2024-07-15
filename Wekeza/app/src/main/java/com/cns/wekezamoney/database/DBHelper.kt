package com.cns.wekezamoney.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cns.wekezamoney.model.User

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "wekeza_db"
        const val DATABASE_VERSION = 1

        // Users table and columns
        const val TABLE_USERS = "users"
        const val COLUMN_USER_ID = "id"
        const val COLUMN_USER_USERNAME = "username"
        const val COLUMN_USER_PASSWORD = "password"

        // Notifications table and columns
        const val TABLE_NOTIFICATIONS = "notifications"
        const val COLUMN_NOTIFICATION_ID = "id"
        const val COLUMN_NOTIFICATION_USER_ID = "user_id"
        const val COLUMN_NOTIFICATION_ENABLED = "enabled"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_USERS_TABLE = ("CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_USER_USERNAME TEXT," +
                "$COLUMN_USER_PASSWORD TEXT)")

        val CREATE_NOTIFICATIONS_TABLE = ("CREATE TABLE $TABLE_NOTIFICATIONS (" +
                "$COLUMN_NOTIFICATION_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NOTIFICATION_USER_ID INTEGER," +
                "$COLUMN_NOTIFICATION_ENABLED INTEGER)")

        db.execSQL(CREATE_USERS_TABLE)
        db.execSQL(CREATE_NOTIFICATIONS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.run {
            execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
            execSQL("DROP TABLE IF EXISTS $TABLE_NOTIFICATIONS")
            onCreate(this)
        }
    }

    // User operations

    fun addUser(username: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_USERNAME, username)
            put(COLUMN_USER_PASSWORD, password)
        }

        val id = db.insert(TABLE_USERS, null, values)
        db.close()
        return id
    }

    fun updateUser(username: String, password: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_PASSWORD, password)
        }

        val rowsUpdated = db.update(TABLE_USERS, values, "$COLUMN_USER_USERNAME = ?", arrayOf(username))
        db.close()
        return rowsUpdated
    }

    @SuppressLint("Range")
    fun getUser(username: String): User? {
        val db = this.readableDatabase
        var user: User? = null
        val cursor = db.query(
            TABLE_USERS,
            null,
            "$COLUMN_USER_USERNAME = ?",
            arrayOf(username),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getLong(cursor.getColumnIndex(COLUMN_USER_ID)),
                username = cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME)),
                password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))
            )
        }
        cursor.close()
        db.close()
        return user
    }

    // Notification operations

    fun enableNotifications(userId: Long): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOTIFICATION_USER_ID, userId)
            put(COLUMN_NOTIFICATION_ENABLED, 1)
        }

        val id = db.insert(TABLE_NOTIFICATIONS, null, values)
        db.close()
        return id
    }

    fun disableNotifications(userId: Long): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOTIFICATION_ENABLED, 0)
        }

        val rowsUpdated = db.update(
            TABLE_NOTIFICATIONS,
            values,
            "$COLUMN_NOTIFICATION_USER_ID = ?",
            arrayOf(userId.toString())
        )
        db.close()
        return rowsUpdated
    }

    @SuppressLint("Range")
    fun areNotificationsEnabled(userId: String): Boolean {
        val db = this.readableDatabase
        var enabled = false
        val cursor = db.query(
            TABLE_NOTIFICATIONS,
            null,
            "$COLUMN_NOTIFICATION_USER_ID = ?",
            arrayOf(userId.toString()),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            enabled = cursor.getInt(cursor.getColumnIndex(COLUMN_NOTIFICATION_ENABLED)) == 1
        }
        cursor.close()
        db.close()
        return enabled
    }
}
