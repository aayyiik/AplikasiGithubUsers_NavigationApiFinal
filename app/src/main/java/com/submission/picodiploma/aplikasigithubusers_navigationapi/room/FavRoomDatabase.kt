package com.submission.picodiploma.aplikasigithubusers_navigationapi.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.submission.picodiploma.aplikasigithubusers_navigationapi.data.model.ItemsItem

@Database(entities = [ItemsItem::class], version = 1, exportSchema = false)
abstract class FavRoomDatabase : RoomDatabase(){
    abstract fun FavDao(): FavDao
}