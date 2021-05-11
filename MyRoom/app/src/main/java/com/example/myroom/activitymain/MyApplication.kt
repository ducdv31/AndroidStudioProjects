package com.example.myroom.activitymain

import android.app.Application
import com.example.myroom.activitymain.`interface`.IPermissionRequest
import com.example.myroom.sharedpreference.DataLocalManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataLocalManager.init(applicationContext)
    }

    companion object{
        fun getUIDPermission(iPermissionRequest: IPermissionRequest) {
            val mAuth = FirebaseAuth.getInstance()
            val user: FirebaseUser? = mAuth.currentUser
            iPermissionRequest.hasUserUID(false, "")
            iPermissionRequest.hasRootUser(false)
            if (user?.uid != null) {
                iPermissionRequest.hasUserUID(true, user.displayName)
                FirebaseDatabase.getInstance().reference
                    .child(MainActivity.PARENT_PERMISSION_CHILD)
                    .child(user.uid).child(MainActivity.PERM_CHILD)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val codePerm: String = snapshot.value.toString()
                            if (codePerm == "1") {
                                iPermissionRequest.hasRootUser(true)
                            } else {
                                iPermissionRequest.hasRootUser(false)
                            }

                        }

                        override fun onCancelled(error: DatabaseError) {
                            iPermissionRequest.hasUserUID(false, "")
                            iPermissionRequest.hasRootUser(false)
                        }

                    })
            } else {
                /* delete permission */
                /* save to shared preference */
                iPermissionRequest.hasUserUID(false, "")
                iPermissionRequest.hasRootUser(false)
            }
        }
    }
}