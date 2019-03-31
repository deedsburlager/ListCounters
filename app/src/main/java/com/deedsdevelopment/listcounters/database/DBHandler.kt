package com.deedsdevelopment.listcounters.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.deedsdevelopment.listcounters.model.Counters
import android.util.Log
import java.util.*

class DBHandler(context: Context) : SQLiteOpenHelper(context,
    DB_NAME, null,
    DB_VERSION
)  {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                ID + " INTEGER PRIMARY KEY," +
                NAME + " TEXT," + TALLY + " TEXT," +
                COMPLETED + " TEXT);"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }
    //Create
    fun addCounter(counters: Counters): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, counters.name)
        values.put(TALLY, counters.tally)
        values.put(COMPLETED, counters.completed)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedId", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }
    //Read
    fun getCounter(_id: Int): Counters {
        val counters = Counters()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        counters.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        counters.name = cursor.getString(cursor.getColumnIndex(NAME))
        counters.tally = cursor.getString(cursor.getColumnIndex(TALLY))
        counters.completed = cursor.getString(cursor.getColumnIndex(COMPLETED))
        cursor.close()
        return counters
    }
    //List
    fun counter(): List<Counters> {
        val counterList = ArrayList<Counters>()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val counters = Counters()
                    counters.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    counters.name = cursor.getString(cursor.getColumnIndex(NAME))
                    counters.tally = cursor.getString(cursor.getColumnIndex(TALLY))
                    counters.completed = cursor.getString(cursor.getColumnIndex(COMPLETED))
                    counterList.add(counters)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return counterList
    }
    fun updateCounter(counters: Counters): Boolean{
        val db = this.writableDatabase
        val values  = ContentValues()
        values.put(NAME, counters.name)
        values.put(TALLY, counters.tally)
        values.put(COMPLETED, counters.completed)
        val _success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(counters.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteCounter(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteAllCounters(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, null, null).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

companion object {

    private val DB_VERSION = 1
    private val DB_NAME = "MyCounters"
    private val TABLE_NAME = "Counters"
    private val ID = "Id"
    private val NAME = "Name"
    private val TALLY = "Tally"
    private val COMPLETED = "Completed"
}
}