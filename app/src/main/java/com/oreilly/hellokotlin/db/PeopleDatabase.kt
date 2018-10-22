package com.oreilly.hellokotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Person::class], version = 1)
abstract class PeopleDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDAO

    companion object {
        @Volatile
        private var INSTANCE: PeopleDatabase? = null

        fun getInstance(context: Context): PeopleDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.inMemoryDatabaseBuilder(context.applicationContext,
                        PeopleDatabase::class.java)
                        .allowMainThreadQueries()
                        .build()
    }
}