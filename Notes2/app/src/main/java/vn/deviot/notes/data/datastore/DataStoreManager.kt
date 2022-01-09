package vn.deviot.notes.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import vn.deviot.notes.utils.EMPTY
import javax.inject.Inject

// At the top level of your kotlin file:
val Context.userStore: DataStore<Preferences> by preferencesDataStore(name = "user_store")

/* key user */
val USER_DATA_KEY = stringPreferencesKey("user_name_key")
val PASS_DATA_KEY = stringPreferencesKey("pass_word_key")

class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    /* username */
    val usernameFlow: Flow<String> = context.userStore.data
        .map { preferences ->
            preferences[USER_DATA_KEY] ?: EMPTY
        }

    suspend fun setUserToStore(username: String) {
        context.userStore.edit { userStore ->
            userStore[USER_DATA_KEY] = username
        }
    }

    /* password */
    val passwordFlow: Flow<String> = context.userStore.data
        .map { preferences ->
            preferences[PASS_DATA_KEY] ?: EMPTY
        }

    suspend fun setPasswordToStore(password: String) {
        context.userStore.edit { userStore ->
            userStore[PASS_DATA_KEY] = password
        }
    }
}