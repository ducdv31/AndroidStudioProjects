package vn.deviot.notes.screen.notes.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NoteRp(
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("note")
    @Expose
    var note: String? = null,

    @SerializedName("date")
    @Expose
    var date: Double? = null
)
