package com.example.appsqli.data.remote

import com.example.appsqli.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("users")
    suspend fun getUsers(@Query("page") id: Int?, @Query("per_page") per_page: Int?): Response<UserResponse>
//   @GET(value = "api/users")
//   fun getUser(@Query("page") id:Int ): Observable<Users>

}