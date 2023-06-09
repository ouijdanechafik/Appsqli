package com.example.appsqli.data.base

data class Resource<out T>(
    val status: Status,
    val data: T?=null,
    val message: String?=null
){
    companion object{
        fun <T> success(data:T): Resource<T> {
            return Resource(status = Status.SUCCESS,data,null)
        }
        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING,null,null)
        }
        fun <T> failed(message: String): Resource<T> {
            return Resource(Status.FAIL,null,message)
        }

    }
}