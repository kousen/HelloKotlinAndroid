package com.oreilly.hellokotlin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DatabaseHelper private constructor(ctx: Context) :
        ManagedSQLiteOpenHelper(ctx, "names.db", null, 1) {

    init {
        instance = this
    }

    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context) =
                instance ?: DatabaseHelper(ctx.applicationContext)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("Names", true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "name" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("Names", true)
        onCreate(db)
    }
}

val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(this)