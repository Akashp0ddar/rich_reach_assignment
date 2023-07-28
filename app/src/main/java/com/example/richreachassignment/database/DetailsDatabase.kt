package com.example.richreachassignment.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.richreachassignment.models.Details

class DetailsDatabase(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "details.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "details"

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                    "employeeName TEXT," +
                    "employeeNumber INTEGER," +
                    "employeeTitle TEXT," +
                    "employeeDepartment TEXT," +
                    "timeSpentAsManager TEXT," +
                    "isActive INTEGER" +
                    ");"
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun insertDetails(detailsList: List<Details>) {
        val db = writableDatabase
        db.beginTransaction()

        try {
            // Loop through the list and insert each Details object into the table
            for (details in detailsList) {
                val values = ContentValues().apply {
                    put("employeeName", details.employeeName)
                    put("employeeNumber", details.employeeNumber)
                    put("employeeTitle", details.employeeTitle)
                    put("employeeDepartment", details.employeeDepartment)
                    put("timeSpentAsManager", details.timeSpentAsManager)
                    put("isActive", if (details.isActive == true) 1 else 0)
                }
                db.insert(TABLE_NAME, null, values)
            }

            db.setTransactionSuccessful()
        } catch (e: Exception) {
            // Handle any exceptions that might occur during insertion
            e.printStackTrace()
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    fun getAllDetails(): ArrayList<Details> {
        val detailsList = ArrayList<Details>()
        val db = readableDatabase

        val query = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor? = db.rawQuery(query, null)

        cursor?.use {
            // Cache column indices to avoid getColumnIndex calls inside the loop
            val nameIndex = it.getColumnIndex("employeeName")
            val numberIndex = it.getColumnIndex("employeeNumber")
            val titleIndex = it.getColumnIndex("employeeTitle")
            val departmentIndex = it.getColumnIndex("employeeDepartment")
            val timeAsManagerIndex = it.getColumnIndex("timeSpentAsManager")
            val isActiveIndex = it.getColumnIndex("isActive")

            while (it.moveToNext()) {
                val employeeName = it.getString(nameIndex)
                val employeeNumber = it.getInt(numberIndex)
                val employeeTitle = it.getString(titleIndex)
                val employeeDepartment = it.getString(departmentIndex)
                val timeSpentAsManager = it.getString(timeAsManagerIndex)
                val isActive = it.getInt(isActiveIndex) == 1

                val details = Details(
                    employeeName,
                    employeeNumber,
                    employeeTitle,
                    employeeDepartment,
                    timeSpentAsManager,
                    isActive
                )
                detailsList.add(details)
            }
        }

        cursor?.close()
        db.close()
        return detailsList
    }

    fun getListOfDetailsWithCustomQuery(query: String): ArrayList<Details>? {
        val detailsList = ArrayList<Details>()
        val db = readableDatabase

        try {
            val cursor: Cursor? = db.rawQuery(query, null)

            cursor?.use {
                // Cache column indices to avoid getColumnIndex calls inside the loop
                val nameIndex = it.getColumnIndex("employeeName")
                val numberIndex = it.getColumnIndex("employeeNumber")
                val titleIndex = it.getColumnIndex("employeeTitle")
                val departmentIndex = it.getColumnIndex("employeeDepartment")
                val timeAsManagerIndex = it.getColumnIndex("timeSpentAsManager")
                val isActiveIndex = it.getColumnIndex("isActive")

                while (it.moveToNext()) {
                    val employeeName = it.getString(nameIndex)
                    val employeeNumber = it.getInt(numberIndex)
                    val employeeTitle = it.getString(titleIndex)
                    val employeeDepartment = it.getString(departmentIndex)
                    val timeSpentAsManager = it.getString(timeAsManagerIndex)
                    val isActive = it.getInt(isActiveIndex) == 1

                    val details = Details(
                        employeeName,
                        employeeNumber,
                        employeeTitle,
                        employeeDepartment,
                        timeSpentAsManager,
                        isActive
                    )
                    detailsList.add(details)
                }
            }

            cursor?.close()
            db.close()
        } catch (e: Exception) {
            // Print "Query is not valid" if there's an exception (e.g., invalid SQL syntax)
            e.printStackTrace()
            return null
        }

        return detailsList
    }

}