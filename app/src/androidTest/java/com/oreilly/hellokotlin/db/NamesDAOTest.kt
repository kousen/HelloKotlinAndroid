package com.oreilly.hellokotlin.db

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NamesDbTest {
    private val dao = NamesDAO(InstrumentationRegistry.getTargetContext())

    @Before
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
        dao.insertName("Fred")
        assertTrue(dao.exists("Fred"))
    }

    @Test
    fun nameDoesNotExist() {
        assertFalse(dao.exists("Wilma"))
    }
}