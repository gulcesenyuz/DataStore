package com.example.datastorebasics

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class PreferenceDataStore(context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "USER")
    private val dataStore = context.dataStore

    companion object {
        val USER_NAME = stringPreferencesKey("USER_NAME")
        val USER_JOB = stringPreferencesKey("USER_JOB")
    }

    // Example function to set user details
    suspend fun setValueDataStore(userDetails: UserDetails) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = userDetails.userName
            preferences[USER_JOB] = userDetails.userJob
        }
    }

    // Example function to retrieve user details
    val getValueDataStore = dataStore.data
        .map { preferences ->
            val userName = preferences[USER_NAME] ?: ""
            val userJob = preferences[USER_JOB] ?: ""
            UserDetails(userName, userJob)
        }
    // Example function to retrieve user  name
    val getNameValueDataStore = dataStore.data
        .map { preferences ->
            val userName = preferences[USER_NAME] ?: ""
        }
    // Example function to retrieve user  job
    val getJobValueDataStore = dataStore.data
        .map { preferences ->
            val userJob = preferences[USER_JOB] ?: ""
        }

}
