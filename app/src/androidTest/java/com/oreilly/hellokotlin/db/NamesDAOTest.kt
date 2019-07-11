package com.oreilly.hellokotlin.db

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NamesDAOTest {
    private val dao: NamesDAO
        get() = NamesDAO(ApplicationProvider.getApplicationContext<Context>())

    @After
    fun init() {
        dao.deleteAllNames()
    }

    @Test
    fun getInsertAndGetAllNames() {
        dao.insertName("Homer")
        dao.insertName("Marge")
        dao.insertName("Bart")
        dao.insertName("Lisa")
        dao.insertName("Maggie")

        val names = dao.getAllNames()
        Log.i("TestRunner", names.toString())
        assertEquals(5, names.size)
        assertThat(names, containsInAnyOrder("Bart", "Lisa",
                "Maggie", "Homer", "Marge"))
    }

    @Test
    fun insertAndDeleteName() {
        // DB is empty
        assertEquals(0, dao.getAllNames().size)

        // Insert a name
        val id = dao.insertName("Barney")
        assertEquals(1, dao.getAllNames().size)

        // Delete the name
        val rowsDeleted = dao.deleteName(id)
        assertEquals(0, dao.getAllNames().size)
        assertEquals(1, rowsDeleted)
    }

    @Test
    fun nameExists() {
        dao.insertName("Pebbles")
        assertTrue(dao.exists("Pebbles"))
    }

    @Test
    fun nameDoesNotExist() {
        assertFalse(dao.exists("Wilma"))
    }
}