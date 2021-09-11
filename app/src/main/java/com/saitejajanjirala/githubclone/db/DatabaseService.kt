package com.saitejajanjirala.githubclone.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.saitejajanjirala.githubclone.models.Item

@Database(
    entities = [Item::class],
    exportSchema = true,
    version = 1
)
@TypeConverters(MyTypeConverter::class)
abstract class DatabaseService :RoomDatabase(){

    public abstract fun getItemsDao():ItemsDao
}

