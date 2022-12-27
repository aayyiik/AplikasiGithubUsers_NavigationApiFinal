package com.submission.picodiploma.aplikasigithubusers_navigationapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.submission.picodiploma.aplikasigithubusers_navigationapi.data.model.ItemsItem
import com.submission.picodiploma.aplikasigithubusers_navigationapi.data.remote.ApiConfig
import com.submission.picodiploma.aplikasigithubusers_navigationapi.room.Database
import com.submission.picodiploma.aplikasigithubusers_navigationapi.utils.MyResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(private val db: Database) : ViewModel(){

    val resultDetailUser = MutableLiveData<MyResult>()
    val resultFollowersUser = MutableLiveData<MyResult>()
    val resultFollowingUser = MutableLiveData<MyResult>()
    val resultSuccessFavUser = MutableLiveData<Boolean>()
    val resultDeleteFavUser = MutableLiveData<Boolean>()

    fun detailUser(username: String){
        viewModelScope.launch{
            flow{
                val response = ApiConfig.githubService.getDetail(username)
                emit(response)
            }.onStart {
                resultDetailUser.value = MyResult.Loading(true)
            }.onCompletion {
                resultDetailUser.value = MyResult.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultDetailUser.value = MyResult.Error(it)
            }.collect{
                resultDetailUser.value = MyResult.Success(it)
            }
        }
    }

    fun detailFollowers(username: String){
        viewModelScope.launch{
            flow{
                val response = ApiConfig.githubService.getFollowers(username)
                emit(response)
            }.onStart {
                resultFollowersUser.value = MyResult.Loading(true)
            }.onCompletion {
                resultFollowersUser.value = MyResult.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultFollowersUser.value = MyResult.Error(it)
            }.collect{
                resultFollowersUser.value = MyResult.Success(it)
            }
        }
    }

    fun detailFollowing(username: String){
        viewModelScope.launch{
            flow{
                val response = ApiConfig.githubService.getFollowing(username)
                emit(response)
            }.onStart {
                resultFollowingUser.value = MyResult.Loading(true)
            }.onCompletion {
                resultFollowingUser.value = MyResult.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultFollowingUser.value = MyResult.Error(it)
            }.collect{
                resultFollowingUser.value = MyResult.Success(it)
            }
        }
    }

    private var isFavorite = false

    fun clickFavorite(itemsItem: ItemsItem?){
        viewModelScope.launch {
            itemsItem?.let {
                if(isFavorite){
                    db.favDao.delete(itemsItem)
                    resultDeleteFavUser.value = true
                }else{
                    db.favDao.insert(itemsItem)
                    resultSuccessFavUser.value = true
                }
            }
            isFavorite = !isFavorite
        }
    }

    fun catchFavorite(id: Int, listenFavorite: () -> Unit){
        viewModelScope.launch {
            val user = db.favDao.findById(id)
            if (user != null){
                listenFavorite()
                isFavorite = true
            }
        }
    }


    class Factory(private val db: Database): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailViewModel(db) as T
    }

}