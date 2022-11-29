package com.oreilly.hellokotlin.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Query("select * from users order by name asc")
    fun getAllUsers(): Flow<List<User>>

    @Query("select * from users where _id = :key")
    fun get(key: Long): User?

    @Query("select count(*) from users where name = :name")
    suspend fun count(name: String): Int

    @Query("select * from users where name = :name")
    fun findByName(name: String): List<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(vararg users: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Delete
    suspend fun deleteAll(vararg users: User)
}