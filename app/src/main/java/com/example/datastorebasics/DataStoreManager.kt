package com.example.datastorebasics

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreManager(context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "USER")
        val USER_NAME = stringPreferencesKey("USER_NAME")
        val USER_SURNAME = stringPreferencesKey("USER_SURNAME")
    }

    private val dataStore = context.dataStore

    // Example function to set user details
    suspend fun setValueDataStore(userDetails: UserDetails) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = userDetails.userName
            preferences[USER_SURNAME] = userDetails.userSurname
        }
    }

    // Example function to retrieve user  name
    fun getNameValueDataStore(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[USER_NAME] ?: ""
            }
    }

    // Example function to retrieve user  job
    fun getSurnameValueDataStore(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[USER_SURNAME] ?: ""
            }
    }

    // Example function to retrieve user details
    fun getValueDataStore(): Flow<UserDetails> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val userName = preferences[USER_NAME] ?: ""
                val userSurname = preferences[USER_SURNAME] ?: ""
                UserDetails(userName, userSurname)
            }
    }
}
