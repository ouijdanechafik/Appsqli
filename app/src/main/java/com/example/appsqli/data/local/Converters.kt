package com.example.appsqli.data.local

import androidx.room.TypeConverter
import com.example.appsqli.data.model.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String{
        return source.first_name
    }

    @TypeConverter
    fun toSource(name: String): Source{
        return Source(name, name)
    }
}