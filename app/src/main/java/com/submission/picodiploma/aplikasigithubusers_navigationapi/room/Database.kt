package com.submission.picodiploma.aplikasigithubusers_navigationapi.room

import android.content.Context
import androidx.room.Room

class Database (private val context: Context) {
    private val db = Room.databaseBuilder(context, FavRoomDatabase::class.java, "usergithub.db" )
        .allowMainThreadQueries()
        .build()

    val favDao = db.FavDao()
}