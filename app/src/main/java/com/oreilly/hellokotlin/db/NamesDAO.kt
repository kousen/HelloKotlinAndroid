package com.oreilly.hellokotlin.db

import android.content.Context
import org.jetbrains.anko.db.*

class NamesDAO(private val ctx: Context,
              private val helper: DatabaseHelper = DatabaseHelper.getInstance(ctx)) {

    fun getAllNames(): List<String> = helper.use {
        select("Names", "name").exec {
            parseList(StringParser)
        }
    }

    fun insertName(name: String): Long = helper.use {
        insert("Names", "name" to name)
    }


    fun deleteName(id: Long) = helper.use {
        delete("Names", "_id = {id}", "id" to id)
    }


    fun deleteAllNames() = helper.use {
        delete("Names")
    }

    fun exists(name: String): Boolean = helper.use {
        val s = select("Names", "name")
                .whereSimple("name = ?", name)
                .exec {
                    parseOpt(StringParser)
                }
        !s.isNullOrBlank()
    }
}
