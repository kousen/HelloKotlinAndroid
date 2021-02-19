package com.oreilly.hellokotlin.db

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserDAOTest {
    companion object {
        const val TAG = "UserDAOTest"
    }

    private lateinit var userDao: UserDAO
    private lateinit var db: UserDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        userDao = db.userDao
        // userDao.deleteAll()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndDeleteUser() {
        val picard = User("Picard")
        val namesBefore = userDao.getAllUsers()
        Log.i(TAG, "users before insert: $namesBefore")
        assertEquals(0, namesBefore.size)

        userDao.insertUsers(picard)
        val namesAfterInsert = userDao.getAllUsers()
        Log.i(TAG, "users after insert: $namesAfterInsert")
        assertTrue(namesAfterInsert.map { it.name }.contains("Picard"))

        userDao.delete(picard.name)
        val namesAfterDelete = userDao.getAllUsers()
        Log.i(TAG, "users after delete: $namesAfterDelete")
        assertFalse(namesAfterDelete.map { it.name }.contains("Picard"))
    }
}