package com.oreilly.hellokotlin.db

import android.content.Context
import androidx.room.*

@Entity(tableName = "users")
data class User(
        @ColumnInfo(name = "name")
        val name: String,

        @PrimaryKey(autoGenerate = true)  // last, so can omit it when instantiating
        var _id: Long = 0L
)

@Dao
interface UserDAO {
    @Query("select * from users order by name asc")
    fun getAllUsers(): List<User>

    @Query("select * from users where _id = :key")
    fun get(key: Long): User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(vararg users: User)

    @Update
    fun update(user: User)

    @Query("delete from users where name = :name")
    fun delete(name: String)

    @Query("delete from users")
    fun deleteAll()
}

class UserRepository(private val userDao: UserDAO) {
    val allUsers = userDao.getAllUsers()

    fun insertUser(name: String) =
            userDao.insertUsers(User(name))
}

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDAO

    companion object {
        @Volatile
        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                        .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database")
                        .fallbackToDestructiveMigration()
                        .build()
    }

}