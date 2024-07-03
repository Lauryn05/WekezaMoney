import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "wekeza_db"
        const val DATABASE_VERSION = 1

        // Define your table and columns
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_USERS_TABLE = ("CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_USERNAME TEXT," +
                "$COLUMN_PASSWORD TEXT)")
        db.execSQL(CREATE_USERS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.run {
            execSQL(/* sql = */ "DROP TABLE IF EXISTS $TABLE_USERS")
            onCreate(this)
        }
    }

    fun addUser(username: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USERNAME, username)
        values.put(COLUMN_PASSWORD, password)

        val id = db.insert(TABLE_USERS, null, values)
        db.close()
        return id
    }

    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USERNAME=? AND $COLUMN_PASSWORD=?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        val count = cursor.count
        cursor.close()
        return count > 0
    }
}
