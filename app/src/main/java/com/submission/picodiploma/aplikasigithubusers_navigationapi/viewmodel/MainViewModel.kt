package com.submission.picodiploma.aplikasigithubusers_navigationapi.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.submission.picodiploma.aplikasigithubusers_navigationapi.data.remote.ApiConfig
import com.submission.picodiploma.aplikasigithubusers_navigationapi.preferences.SettingPreferences
import com.submission.picodiploma.aplikasigithubusers_navigationapi.utils.MyResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val preferences: SettingPreferences) : ViewModel() {

    val myResultUser = MutableLiveData<MyResult>()
    fun getTheme() = preferences.getThemeSetting().asLiveData()

    fun procesUser(){
        viewModelScope.launch{
                flow{
                    val response = ApiConfig.githubService.getUser()
                    emit(response)
                }.onStart {
                    myResultUser.value = MyResult.Loading(true)
                }.onCompletion {
                    myResultUser.value = MyResult.Loading(false)
                }.catch {
                    Log.e("Error", it.message.toString())
                    it.printStackTrace()
                    myResultUser.value = MyResult.Error(it)
                }.collect{
                    myResultUser.value = MyResult.Success(it)
                }
        }
    }

    fun procesUser(username: String){
        viewModelScope.launch{
            flow{
                val response = ApiConfig.githubService.searchUser(
                    mapOf(
                        "q" to username
                    )
                )
                emit(response)
            }.onStart {
                myResultUser.value = MyResult.Loading(true)
            }.onCompletion {
                myResultUser.value = MyResult.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                myResultUser.value = MyResult.Error(it)
            }.collect{
                myResultUser.value = MyResult.Success(it.items)
            }
        }
    }

    //constructor dari di viewmodel
    class Factory(private val preferences: SettingPreferences): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(preferences) as T
    }
}