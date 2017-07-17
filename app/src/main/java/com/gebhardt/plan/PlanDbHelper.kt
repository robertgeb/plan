package com.gebhardt.plan

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.gebhardt.plan.plan.PlanContent

/**
 * Created by robert on 7/17/17.
 */
class PlanDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PlanContract.PlanEntry.TABLE_NAME + " (" +
                    PlanContract.PlanEntry._ID + " INTEGER PRIMARY KEY," +
                    PlanContract.PlanEntry.COLUMN_NAME_CONTENT + " TEXT)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + PlanContract.PlanEntry.TABLE_NAME

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Plan.db"
    }

    fun findAll() : List<PlanContent.PlanItem>{
        val planList = ArrayList<PlanContent.PlanItem>()
        val projection = arrayOf<String>(PlanContract.PlanEntry._ID, PlanContract.PlanEntry.COLUMN_NAME_CONTENT)
        val result = this.readableDatabase.query(
                PlanContract.PlanEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null)
        while (result.moveToNext()){
            val content = result.getString(result.getColumnIndex(PlanContract.PlanEntry.COLUMN_NAME_CONTENT))
            val id = result.getInt(result.getColumnIndex(PlanContract.PlanEntry._ID))
            planList.add(PlanContent.PlanItem(id, content))
        }
        result.close()
        return planList
    }

    fun insert(content : String) : PlanContent.PlanItem{
        val values = ContentValues()
        values.put(PlanContract.PlanEntry.COLUMN_NAME_CONTENT, content)
        val id  = this.writableDatabase.insert(PlanContract.PlanEntry.TABLE_NAME, null, values)
        return PlanContent.PlanItem(id.toInt(), content)
    }

    fun delete(plan : PlanContent.PlanItem){
        val selection = PlanContract.PlanEntry._ID+ " = ?"
        val selectionArgs = arrayOf(plan.id.toString())
        this.writableDatabase.delete(PlanContract.PlanEntry.TABLE_NAME, selection, selectionArgs)
    }
}