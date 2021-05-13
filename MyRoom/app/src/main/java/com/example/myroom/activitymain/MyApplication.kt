package com.example.myroom.activitymain

import android.app.Application
import android.content.Context
import com.example.myroom.activityuserpermission.model.UserPermissionImg
import com.example.myroom.components.`interface`.IPermissionRequest
import com.example.myroom.components.sharedpreference.DataLocalManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
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

    companion object {

        fun setDataAfterLogIn(context: Context, user: FirebaseUser) {
            FirebaseDatabase.getInstance().reference
                .child(MainActivity.PARENT_PERMISSION_CHILD)
                .child(user.uid).child(MainActivity.USERNAME_CHILD)
                .setValue(user.displayName)
            FirebaseDatabase.getInstance().reference
                .child(MainActivity.PARENT_PERMISSION_CHILD)
                .child(user.uid).child(MainActivity.EMAIL_CHILD)
                .setValue(user.email)
            FirebaseDatabase.getInstance().reference
                .child(MainActivity.PARENT_PERMISSION_CHILD)
                .child(user.uid).child(MainActivity.URI_PHOTO_CHILD)
                .setValue(GoogleSignIn.getLastSignedInAccount(context)?.photoUrl.toString())
        }

        fun getUIDPermission(iPermissionRequest: IPermissionRequest) {
            val mAuth = FirebaseAuth.getInstance()
            val user: FirebaseUser? = mAuth.currentUser
            iPermissionRequest.hasUnKnowUser(true)
            iPermissionRequest.hasRootUser(false)
            iPermissionRequest.hasSupperRoot(false)
            iPermissionRequest.hasUserInRoom(false, "")
            if (user?.uid != null) {
                FirebaseDatabase.getInstance().reference
                    .child(MainActivity.PARENT_PERMISSION_CHILD)
                    .child(user.uid).child(MainActivity.PERM_CHILD)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val codePerm: String = snapshot.value.toString()
                            when (codePerm) {
                                "1" -> {
                                    iPermissionRequest.hasRootUser(true)
                                }
                                "0" -> {
                                    iPermissionRequest.hasSupperRoot(true)
                                }
                                "2" -> {
                                    iPermissionRequest.hasUserInRoom(true, user.displayName)
                                }
                                "3" -> {
                                    iPermissionRequest.hasUnKnowUser(true)
                                }
                                else -> {
                                    iPermissionRequest.hasUnKnowUser(true)
                                }
                            }

                        }

                        override fun onCancelled(error: DatabaseError) {
                            iPermissionRequest.hasUnKnowUser(true)
                        }

                    })
            } else {
                /* delete permission */
                /* save to shared preference */
                iPermissionRequest.hasUnKnowUser(true)
            }
        }

        fun comparePermission(newValue: Int, userPermission: UserPermissionImg) {
            val mAuth = FirebaseAuth.getInstance()
            val user: FirebaseUser? = mAuth.currentUser
            if (user?.uid != null) {
                FirebaseDatabase.getInstance().reference
                    .child(MainActivity.PARENT_PERMISSION_CHILD)
                    .child(user.uid).child(MainActivity.PERM_CHILD)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val codePerm: String = snapshot.value.toString()
                            if (codePerm.toInt() < userPermission.perm) {
                                FirebaseDatabase.getInstance().reference
                                    .child(MainActivity.PARENT_PERMISSION_CHILD)
                                    .child(userPermission.uid)
                                    .child(MainActivity.PERM_CHILD)
                                    .setValue(newValue)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                        }

                    })
            } else {

            }
        }
    }
}