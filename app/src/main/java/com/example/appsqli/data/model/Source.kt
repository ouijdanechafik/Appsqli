package com.example.appsqli.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    val id: String,
    val first_name: String
):Parcelable