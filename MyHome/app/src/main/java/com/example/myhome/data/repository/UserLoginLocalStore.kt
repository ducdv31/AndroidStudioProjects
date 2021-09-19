package com.example.myhome.data.repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.myhome.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserLoginLocalStore private constructor(private val context: Context) {

    companion object {
        private val ID_USER_LOGIN = stringPreferencesKey("ID_USER_LOGIN")
        @SuppressLint("StaticFieldLeak")
        private var userLoginLocalStore: UserLoginLocalStore? = null

        fun getInstant(context: Context): UserLoginLocalStore {
            if (userLoginLocalStore == null){
                userLoginLocalStore = UserLoginLocalStore(context)
            }
            return userLoginLocalStore ?: UserLoginLocalStore(context)
        }
    }

    private val NAME_USER_LOGIN_DATA_STORE = "NAME_USER_LOGIN_DATA_STORE"

    private val Context.dataStore: DataStore<Preferences>
            by preferencesDataStore(name = NAME_USER_LOGIN_DATA_STORE)

    val getIDUserLogin: Flow<String> = context.dataStore.data.map {
        it[ID_USER_LOGIN] ?: Constants.EMPTY
    }

    suspend fun setIdUserLogin(id: String) {
        context.dataStore.edit {
            it[ID_USER_LOGIN] = id
        }
    }
}