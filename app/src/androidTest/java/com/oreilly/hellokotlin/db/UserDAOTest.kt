package com.oreilly.hellokotlin.db

import android.content.Context
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
    private lateinit var userDao: UserDAO
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        userDao = db.userDao()

        userDao.deleteAll()
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
        assertEquals(0, namesBefore.size)

        userDao.insertUsers(picard)
        val namesAfterInsert = userDao.getAllUsers()
        assertTrue(namesAfterInsert.contains(picard))

        userDao.delete(picard)
        val namesAfterDelete = userDao.getAllUsers()
        assertFalse(namesBefore.contains(picard))
    }
}