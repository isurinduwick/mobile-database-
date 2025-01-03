import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
   
    companion object {
        const val DATABASE_NAME = "AppDatabase.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_AGE = "age"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_AGE INTEGER
            )
        """
        db?.execSQL(createTableQuery)
    }
}
override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    onCreate(db)
}

fun insertUser(name: String, age: Int): Long {
    val db = writableDatabase
    val values = ContentValues().apply {
        put(COLUMN_NAME, name)
        put(COLUMN_AGE, age)
    }
    return db.insert(TABLE_NAME, null, values)
}

fun getAllUsers(): List<String> {
    val db = readableDatabase
    val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
    val users = mutableListOf<String>()
    while (cursor.moveToNext()) {
        val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE))
        users.add("$name ($age)")
    }
    cursor.close()
    return users
}
