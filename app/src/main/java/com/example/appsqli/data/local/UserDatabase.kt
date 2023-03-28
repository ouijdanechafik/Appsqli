package com.example.appsqli.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.appsqli.data.model.User
import com.example.appsqli.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [User::class], version = 3)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    class Callback @Inject constructor(
        private val database: Provider<UserDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()

}