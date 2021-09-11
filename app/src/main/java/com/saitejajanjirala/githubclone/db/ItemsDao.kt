package com.saitejajanjirala.githubclone.db

import androidx.room.*
import com.saitejajanjirala.githubclone.models.Item
import io.reactivex.rxjava3.core.Single

@Dao
interface ItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(items:List<Item>):Single<List<Long>>

    @Query("delete from items")
    fun clearAll():Single<Int>

    @Query("select count(*) from items")
    fun getCount():Single<Int>

    @Query("select * from items")
    fun getAllItems():Single<List<Item>>
}