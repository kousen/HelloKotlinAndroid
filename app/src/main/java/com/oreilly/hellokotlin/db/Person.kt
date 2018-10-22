package com.oreilly.hellokotlin.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "people")
data class Person(@PrimaryKey
                  val id: String = UUID.randomUUID().toString(),
                  val name: String)
