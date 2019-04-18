package com.oreilly.hellokotlin.db

import android.content.Context
import org.jetbrains.anko.db.*

class NamesDAO(private val ctx: Context) {

    fun getAllNames(): List<String> = ctx.database.use {
        select("Names", "name").exec {
            parseList(StringParser)
        }
    }

    fun insertName(name: String): Long = ctx.database.use {
        insert("Names", "name" to name)
    }

    fun deleteName(id: Long) = ctx.database.use {
        delete("Names", "_id = {id}", "id" to id)
    }

    fun deleteAllNames() = ctx.database.use {
        delete("Names")
    }

    fun exists(name: String): Boolean = ctx.database.use {
        val s = select("Names", "name")
                .whereSimple("name = ?", name)
                .exec {
                    parseOpt(StringParser)
                }
        !s.isNullOrBlank()
    }
}