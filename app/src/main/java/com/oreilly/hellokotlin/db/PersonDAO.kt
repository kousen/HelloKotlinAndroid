package com.oreilly.hellokotlin.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PersonDAO {

    @Query("select * from people")
    fun getAllPeople(): List<Person>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPerson(person: Person)

    @Delete
    fun deletePerson(person: Person)

    @Query("delete from people")
    fun deleteAllPeople()

    @Query("SELECT EXISTS(SELECT 1 FROM people where name=:name)")
    fun exists(name: String): Boolean
}
