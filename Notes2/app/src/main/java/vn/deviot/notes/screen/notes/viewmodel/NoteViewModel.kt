package vn.deviot.notes.screen.notes.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import vn.deviot.notes.data.datastore.DataStoreManager
import vn.deviot.notes.data.repo.Repository_Impl
import vn.deviot.notes.screen.notes.model.NoteRp
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repositoryImpl: Repository_Impl
) : ViewModel() {

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    private val TAG = NoteViewModel::class.java.simpleName

    val listNote: SnapshotStateList<NoteRp?> = mutableStateListOf()

    fun getNote() {
        viewModelScope.launch {
            val auth = dataStoreManager.tokenFlow.first()
            val job = CoroutineScope(Dispatchers.Default).async {
                repositoryImpl.getAllNote(auth = auth)
            }
            try {
                val response = job.await()
                if (response?.data?.notes != null) {
                    listNote.clear()
                    listNote.addAll(response.data?.notes!!)
                }
            } catch (e: Exception) {
                Log.e(TAG, "getNote: ${e.message}")
            }
        }
    }

    suspend fun addNote(note: String) {
        viewModelScope.launch {
            val auth = dataStoreManager.tokenFlow.first()
            try {
                repositoryImpl.addNote(auth = auth, note = note)
            } catch (e: Exception) {
                Log.e(TAG, "addNote: ${e.message}")
            }
        }
    }
}