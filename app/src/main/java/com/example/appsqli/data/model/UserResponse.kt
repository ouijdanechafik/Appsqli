package com.example.appsqli.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val users: MutableList<User>
)
