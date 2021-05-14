package com.oreilly.hellokotlin.db

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDAO) {
    val allUsers: Flow<List<User>> = userDao.getAllUsers()

    @WorkerThread
    suspend fun insertUser(name: String) {
        if (userDao.get(name) == null) {
            userDao.insertUsers(User(name))
        } else {
            return
        }
    }

    suspend fun deleteUser(user: User) =
        userDao.delete(user.name)

    suspend fun deleteAllUsers() =
        userDao.deleteAll()
}