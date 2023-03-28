package com.example.appsqli.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var first_name: String? = null,
    var email: String? = null,
    var mobile: String? = null,
    // @SerializedName("lastName")
    val last_name:String?= null,
    val ville: String?= null,
    val pays: String?= null,
    val fonction: String?= null,
    val description: String?= null,
    val phone: String?= null,
    val portable: String?= null,
    val avatar: String?= null,
): Parcelable
@Parcelize
data class Users (
    val page: Int,
    @SerializedName("per_page") val perPage:Int,
    @SerializedName("per_page") val totalPage:Int,
    val data : List<User>
):Parcelable

/*
@Parcelize
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")

    val id: Int?,
    val email:String,
    @SerializedName("first_name")
    val firstName:String,
    @SerializedName("last_name")
    val lastName:String,

    ):Parcelable
@Parcelize
data class Users (
    val page: Int,
    @SerializedName("per_page") val perPage:Int,
    @SerializedName("per_page") val totalPage:Int,
    val data : List<User>
):Parcelable*/


/*data class User (
    @PrimaryKey(autoGenerate = true)

    val id: Int?,
    val email:String?,
   // @SerializedName("firstName")
    val name:String?=null,
   // @SerializedName("lastName")
    val prenom:String?= null,
    val ville: String?= null,
    val pays: String?= null,
    val fonction: String?= null,
    val description: String?= null,
    val phone: String?= null,
    val portable: String?= null,


    ):Parcelable*/

/*@Parcelize
data class Users (
    val page: Int,
    @SerializedName("per_page") val perPage:Int,
    @SerializedName("per_page") val totalPage:Int,
    val data : List<User>
):Parcelable*/

