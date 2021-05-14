package com.oreilly.hellokotlin.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserRepositoryTest {

    private lateinit var userDao: UserDAO
    private lateinit var db: UserDatabase
    private lateinit var repository: UserRepository

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        userDao = db.userDao
        repository = UserRepository(userDao)
        // userDao.deleteAll()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndDeleteUser() = runBlocking {
        repository.deleteAllUsers()
        assertEquals(0, repository.allUsers.first().size)

        repository.insertUser("Picard")
        assertEquals(1, repository.allUsers.first().size)

        repository.insertUser("Picard")
        assertEquals(1, repository.allUsers.first().size)

        repository.deleteUsersByName("Picard")
        assertEquals(0, repository.allUsers.first().size)
    }
}