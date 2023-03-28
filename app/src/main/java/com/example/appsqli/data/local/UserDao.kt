package com.example.appsqli.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.appsqli.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    fun getUsers() : LiveData<MutableList<User>>

    @Update
    suspend fun updateUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(user: MutableList<User>)

    @Delete
    suspend fun deleteUser(note: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUser()

}