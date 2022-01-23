package vn.dv.myhome.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import vn.dv.myhome.utils.Constants
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

@ActivityScoped
class DataStoreManager @Inject constructor(
    @ActivityContext private val context: Context
) {

    private val HOST_KEY = stringPreferencesKey("HOST_KEY")
    private val PORT_KEY = intPreferencesKey("PORT_KEY")
    private val TOPIC_PUB_KEY = stringPreferencesKey("TOPIC_PUB_KEY")
    private val MESSAGE_PUB_KEY = stringPreferencesKey("MESSAGE_PUB_KEY")
    private val TOPIC_SUB_KEY = stringPreferencesKey("TOPIC_SUB_KEY")

    val hostDataFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[HOST_KEY] ?: Constants.EMPTY
    }

    suspend fun setHostData(host: String) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[HOST_KEY] = host
        }
    }

    val portDataFlow: Flow<Int> = context.dataStore.data.map { value: Preferences ->
        value[PORT_KEY] ?: Constants.PORT_DEFAULT
    }

    suspend fun setPortData(port: Int) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[PORT_KEY] = port
        }
    }

    val topicPubFlow: Flow<String> = context.dataStore.data.map { value ->
        value[TOPIC_PUB_KEY] ?: Constants.EMPTY
    }

    suspend fun setTopicPubData(topic: String) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[TOPIC_PUB_KEY] = topic
        }
    }

    val messagePubFlow: Flow<String> = context.dataStore.data.map { value ->
        value[MESSAGE_PUB_KEY] ?: Constants.EMPTY
    }

    suspend fun setMessagePubData(message: String) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[MESSAGE_PUB_KEY] = message
        }
    }

    val topicSubFlow: Flow<String> = context.dataStore.data.map { value ->
        value[TOPIC_SUB_KEY] ?: Constants.EMPTY
    }

    suspend fun setTopicSubData(topic: String) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[TOPIC_SUB_KEY] = topic
        }
    }

}