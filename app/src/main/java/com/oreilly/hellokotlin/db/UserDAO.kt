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
    fun count(name: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(vararg users: User)

    @Update
    suspend fun update(user: User)

    @Query("delete from users where name = :name")
    suspend fun delete(name: String)

    @Query("delete from users")
    suspend fun deleteAll()
}