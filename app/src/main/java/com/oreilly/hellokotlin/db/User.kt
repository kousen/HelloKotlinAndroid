package com.oreilly.hellokotlin.db

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.room.*

@Entity(tableName = "users")
data class User(
        @ColumnInfo(name = "name")
        val name: String,

        @PrimaryKey(autoGenerate = true)  // last, so can omit it when instantiating
        var _id: Long = 0L
)