package com.example.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(private val context: Context) {
    companion object {
        const val NAME_DATA_STORE = "My Data Store"
        val USER_NAME = stringPreferencesKey("USER_NAME")
        val BIRTHDAY = intPreferencesKey("BIRTHDAY")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = NAME_DATA_STORE)

    val userNameFlow: Flow<String> = context.dataStore.data.map {
        it[USER_NAME] ?: ""
    }

    val birthdayFlow: Flow<Int> = context.dataStore.data.map {
        it[BIRTHDAY] ?: 0
    }

    suspend fun saveDataStore(username: String, birthday: Int) {
        context.dataStore.edit {
            it[USER_NAME] = username
            it[BIRTHDAY] = birthday
        }
    }
}