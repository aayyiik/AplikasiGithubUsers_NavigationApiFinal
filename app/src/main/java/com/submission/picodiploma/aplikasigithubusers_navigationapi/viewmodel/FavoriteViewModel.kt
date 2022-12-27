package com.submission.picodiploma.aplikasigithubusers_navigationapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.submission.picodiploma.aplikasigithubusers_navigationapi.room.Database
import com.submission.picodiploma.aplikasigithubusers_navigationapi.utils.MyResult

class FavoriteViewModel (private val db: Database): ViewModel(){
    val myResultUser = MutableLiveData<MyResult>()

    fun getUserFavorite() = db.favDao.getAllUser()

    class Factory(private val db: Database): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(db) as T
    }
}