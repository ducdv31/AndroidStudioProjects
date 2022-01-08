package vn.deviot.notes.screen.login.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRp(
    @SerializedName("token")
    @Expose
    var token: String? = null
)
