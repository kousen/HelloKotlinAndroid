package com.oreilly.hellokotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDAO

    companion object {
        @Volatile
        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "user_database")
                .fallbackToDestructiveMigration()
                .build()
    }
}