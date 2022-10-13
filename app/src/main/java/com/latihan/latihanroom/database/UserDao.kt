package com.latihan.latihanroom.database

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUsers() : List<User>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUser(id: Int) : User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)
}