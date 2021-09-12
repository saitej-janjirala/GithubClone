package com.saitejajanjirala.githubclone.db

import androidx.room.TypeConverter
import com.saitejajanjirala.githubclone.models.Owner

class MyTypeConverter {


    @TypeConverter
    fun ownerToStringConverter(owner: Owner):String{
        return owner.run {
            return@run "$avatarUrl,$login"
        }
    }

    @TypeConverter
    fun stringToOwnerConverter(string: String):Owner{
       val arr=string.split(",")
        return Owner(arr[0],arr[1])
    }


}