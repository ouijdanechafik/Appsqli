package com.example.appsqli.repository

import androidx.lifecycle.liveData
import com.example.appsqli.data.base.Resource
import com.example.appsqli.data.local.UserDao
import com.example.appsqli.data.model.User
import com.example.appsqli.data.model.UserResponse
import com.example.appsqli.data.remote.UserApi

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
 class UserRepository @Inject constructor(
    private val userApi: UserApi,
    private val userDao: UserDao
) {

    fun getUserList(id:Int?) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val response = userApi.getUsers(id =id, per_page=10)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            }
        } catch (exception: Exception) {
            Resource.failed<UserResponse>(exception.message.toString())
        }
    }

    fun getUsers() = userDao.getUsers()

    suspend fun addUser(user: User) = userDao.insert(user)
    suspend fun addAllUser(user: MutableList<User>) = userDao.insertAll(user)
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
    suspend fun deleteAllUser() = userDao.deleteAllUser()

    companion object {
        fun getPagedList(limit: Int, offset: Int) {

        }
    }


}