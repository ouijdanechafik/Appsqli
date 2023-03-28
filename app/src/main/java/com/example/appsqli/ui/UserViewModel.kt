package com.example.appsqli.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appsqli.data.model.User
import com.example.appsqli.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(){

    private val _user = MutableLiveData<User>()
    var user: MutableLiveData<User> = _user

    fun getUserById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                //_user.value = userRepository.getUserList(id=id)
            } catch (e: java.lang.Exception) {
                Log.e("UserViewModel", e.message.toString())
            }
        }
    }


    fun getUser(id:Int?) = userRepository.getUserList(id=id)

    fun getAllUser() = userRepository.getUsers()

    suspend fun addUser(user: User) = userRepository.addUser(user)

    suspend fun addAllUser(user: MutableList<User>) = userRepository.addAllUser(user)

    suspend fun updateUser(user: User) = userRepository.updateUser(user)

    suspend fun deleteUser(user: User) = userRepository.deleteUser(user)

    suspend fun deleteAllUser() = userRepository.deleteAllUser()



}