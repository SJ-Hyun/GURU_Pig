package com.android.guru_pig

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.fragment.app.FragmentActivity

class DBManger(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE plus (year INTEGER, month INTEGER, day INTEGER, class text, money INTEGER, content text);")
        db!!.execSQL("CREATE TABLE minus (year INTEGER, month INTEGER, day INTEGER, class text, money INTEGER, content text);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}