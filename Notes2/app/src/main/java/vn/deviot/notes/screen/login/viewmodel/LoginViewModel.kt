package vn.deviot.notes.screen.login.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import vn.deviot.notes.data.common.ResponseData
import vn.deviot.notes.data.repo.Repository_Impl
import vn.deviot.notes.screen.login.model.LoginRp
import vn.deviot.notes.screen.login.model.UserCredential
import vn.deviot.notes.utils.EMPTY
import vn.deviot.notes.utils.JSON
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repositoryImpl: Repository_Impl
) : ViewModel() {
    private val TAG = LoginViewModel::class.java.simpleName

    val username: MutableState<String> = mutableStateOf(EMPTY)
    val password: MutableState<String> = mutableStateOf(EMPTY)
    val token: MutableState<String> = mutableStateOf(EMPTY)

    private val dataLogin: MutableState<ResponseData<LoginRp>?> = mutableStateOf(null)

    fun login() {
        viewModelScope.launch {
            val userCredential = UserCredential(username.value, password.value)
            val body: RequestBody = RequestBody.create(JSON, Gson().toJson(userCredential))
            dataLogin.value = try {
                repositoryImpl.logIn(body)
            } catch (e: Exception) {
                Log.e(TAG, "login: ${e.message}")
                null
            } finally {
                token.value = dataLogin.value?.data?.token ?: EMPTY
                Log.e(TAG, "login: ${token.value}")
            }
        }
    }
}