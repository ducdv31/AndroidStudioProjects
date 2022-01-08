package vn.deviot.notes.screen.notes.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.deviot.notes.data.repo.Repository_Impl
import vn.deviot.notes.screen.notes.model.NoteRp
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repositoryImpl: Repository_Impl
) : ViewModel() {

    private val TAG = NoteViewModel::class.java.simpleName

    val listNote: SnapshotStateList<NoteRp?> = mutableStateListOf()

    fun getNote(auth: String) {
        Log.e(TAG, "getNote: $auth")
        viewModelScope.launch {
            val response = try {
                repositoryImpl.getAllNote(auth = auth)
            } catch (e: Exception) {
                Log.e(TAG, "getNote: ${e.message}")
                null
            }
            if (response?.data?.notes != null) {
                listNote.clear()
                listNote.addAll(response.data?.notes!!)
            }
        }
    }
}