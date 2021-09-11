package com.example.myhome.ui.viewmodel.typeuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.BaseActivity
import com.example.myhome.utils.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TypeUserViewModel : ViewModel() {

    private val TAG = TypeUserViewModel::class.java.simpleName
    private var typeUser: MutableLiveData<Int> = MutableLiveData(99)

    fun getTypeUser(): MutableLiveData<Int> {
        return typeUser
    }

    fun clearTypeUser() {
        typeUser.postValue(99)
    }

    fun getTypeUserFromFireStore(baseActivity: BaseActivity) {
        BaseActivity.idLd.observe(baseActivity, {
            if (it.isNotEmpty()) {
                Firebase.firestore.collection(Constants.PERMISSION).document(it)
                    .addSnapshotListener { value, error ->
                        val i = value?.get(Constants.PERMISSION).toString().toInt() ?: 99
                        typeUser.postValue(i)
                    }
            }
        })
    }

}