package com.example.datastorebasics

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(context: Context) : ViewModel() {

    var name: String by mutableStateOf("")
    var surname: String by mutableStateOf("")
    private val dataStore = DataStoreManager(context)

    val getName = dataStore.getNameValueDataStore()
    val getSurname = dataStore.getSurnameValueDataStore()
    fun setValueDataStore() {
        viewModelScope.launch(Dispatchers.IO) {
            val userDetails = UserDetails(userName = name, userSurname = surname)
            dataStore.setValueDataStore(userDetails)
        }
    }

    fun logUserValueDataStore() {
        val getUserLiveData = dataStore.getValueDataStore().asLiveData()
        getUserLiveData.observeForever { userDetails ->
            userDetails?.let {
                Log.d(
                    "DataStore val: ",
                    "user name: ${it.userName} , user job: ${it.userSurname}"
                )
            }
        }
    }
}