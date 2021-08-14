package com.example.myhome.ui.viewmodel.dht

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myhome.R
import com.example.myhome.data.model.dht.ThValue
import com.example.myhome.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DhtViewmodel(activity: Activity) : ViewModel() {

    init {
        getCurrentData()
    }

    val thVal: MutableLiveData<String> = MutableLiveData(activity.getString(R.string.temp_humi))

    private fun getCurrentData() {
        FirebaseDatabase.getInstance().reference.child(Constants.DHT11_CHILD)
            .child(Constants.CURRENT_CHILD)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val thValue: ThValue? = snapshot.getValue(ThValue::class.java)
                    thValue?.let {
                        thVal.value = thValue.t?.let { it1 ->
                            thValue.h?.let { it2 ->
                                getTHFormatValue(
                                    it1,
                                    it2
                                )
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    fun getTHFormatValue(t: Int, h: Int): String {
        return "$t ºC | $h %"
    }

}