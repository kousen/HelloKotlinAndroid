package com.oreilly.hellokotlin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class NamesDAO(private val ctx: Context,
               private val db: SQLiteDatabase = ctx.database.readableDatabase) {

    fun getAllNames(): List<String> {
        return db.select("Names", "name").exec {
            parseList(StringParser)
        }
    }

    fun insertName(name: String): Long =
            db.insert("Names", "name" to name)


    fun deleteName(id: Long) =
            db.delete("Names", "_id = {id}", "id" to id)


    fun deleteAllNames() =
            db.delete("Names")

    fun exists(name: String): Boolean {
        val s = db.select("Names", "name")
                .whereSimple("name = ?", name)
                .exec {
                    parseOpt(StringParser)
                }
        return !s.isNullOrBlank()
    }
}
