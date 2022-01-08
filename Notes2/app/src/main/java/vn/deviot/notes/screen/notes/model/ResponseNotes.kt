package vn.deviot.notes.screen.notes.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseNotes(
    @SerializedName("notes")
    @Expose
    var notes: List<NoteRp>? = null
)
