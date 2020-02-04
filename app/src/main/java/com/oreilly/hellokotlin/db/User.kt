package com.oreilly.hellokotlin.db

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "users")
data class User(
        @NonNull @ColumnInfo(name = "name")
        @PrimaryKey val name: String
)

@Dao
interface UserDAO {
    @Query("select * from users order by name asc")
    fun getAllUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("delete from users")
    fun deleteAll()
}

class UserRepository(private val userDao: UserDAO) {
    val allUsers = userDao.getAllUsers()

    fun insertUser(name: String) =
            userDao.insertUsers(User(name))
}

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                        .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context,
                    AppDatabase::class.java,
                    "users.db")
                    .build()
        }
    }

}