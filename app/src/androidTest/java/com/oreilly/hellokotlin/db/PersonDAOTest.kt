package com.oreilly.hellokotlin.db

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PersonDAOTest {
    private val dao = PeopleDatabase.getInstance(
            InstrumentationRegistry.getInstrumentation().targetContext).personDao()

    @Before
    fun init() {
        dao.deleteAllPeople()
    }

    @Test
    fun getInsertAndGetAllNames() {
        dao.insertPerson(Person(name = "Homer"))
        dao.insertPerson(Person(name = "Marge"))
        dao.insertPerson(Person(name = "Bart"))
        dao.insertPerson(Person(name = "Lisa"))
        dao.insertPerson(Person(name = "Maggie"))

        val people = dao.getAllPeople()
        Log.i("TestRunner", people.toString())
        assertEquals(5, people.size)
        assertThat(people.map { it.name }, containsInAnyOrder("Bart", "Lisa",
                "Maggie", "Homer", "Marge"))
    }

    @Test
    fun insertAndDeleteName() {
        // DB is empty
        assertEquals(0, dao.getAllPeople().size)

        // Insert a name
        val barney = Person(name = "Barney")
        dao.insertPerson(barney)
        assertEquals(1, dao.getAllPeople().size)
        assertNotNull(barney.id)

        // Delete the name
        dao.deletePerson(barney)
        assertEquals(0, dao.getAllPeople().size)
    }

    @Test
    fun nameExists() {
        dao.insertPerson(Person(name = "Fred"))
        assertTrue(dao.exists("Fred"))
    }

    @Test
    fun nameDoesNotExist() {
        assertFalse(dao.exists("Wilma"))
    }
}