package com.example.myhome.ui.view.activity.typeUser

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.BaseActivity
import com.example.myhome.R
import com.example.myhome.data.model.manageUserModel.UserProfileHasIDModel
import com.example.myhome.ui.adapter.managerUserAdapter.ManageUserAdapter
import com.example.myhome.ui.view.dialog.DialogSelectTypeUser
import com.example.myhome.utils.Constants
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.*

class ManageUserActivity : BaseActivity() {

    private val TAG = ManageUserActivity::class.java.simpleName
    private lateinit var manageUserAdapter: ManageUserAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var numberUser: TextView
    private lateinit var dialogSelectTypeUser: DialogSelectTypeUser
    private lateinit var lnManageUser: LinearLayout
    private lateinit var callback: ListenerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_user)

        setTitleActionBar(getString(R.string.manage_user))
        isShowBackActionBar(true)
        isShowUserImg(false)

        lnManageUser = findViewById(R.id.ln_manage_user)
        numberUser = findViewById(R.id.number_user)
        recyclerView = findViewById(R.id.rv_manage_user)

        manageUserAdapter = ManageUserAdapter(this) {
            if (typeUserViewModel.getTypeUser().value == 0) {
                if (!dialogSelectTypeUser.isAdded) {
                    val bundle = Bundle()
                    bundle.putSerializable(Constants.BUNDLE_MANAGE_USER, it)
                    dialogSelectTypeUser.arguments = bundle
                    dialogSelectTypeUser.show(
                        supportFragmentManager,
                        getString(R.string.title_set_type_user)
                    )
                }
            } else {
                Snackbar.make(
                    lnManageUser,
                    "${Constants.CANT_SET_TYPE_USER} - cid: ${typeUserViewModel.getTypeUser().value}",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        dialogSelectTypeUser = DialogSelectTypeUser {
            val bundle = dialogSelectTypeUser.arguments
            val userProfileHasIDModelClicked: UserProfileHasIDModel? =
                bundle?.get(Constants.BUNDLE_MANAGE_USER) as UserProfileHasIDModel?
            userProfileHasIDModelClicked?.let { userClicked ->
                val currentUserType: Int = typeUserViewModel.getTypeUser().value ?: 99
                if (currentUserType < userClicked.getPermissionEx()
                    && currentUserType <= 1
                ) {
                    setUserPermission(userClicked.id!!, it)
                }

            }
        }
        val llmn = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = llmn
        recyclerView.adapter = manageUserAdapter

        getListUser()
    }

    private fun getListUser() {
        callback = FirebaseFirestore.getInstance().collection(Constants.PERMISSION)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    value?.let {
                        if (it.documents.isNotEmpty()) {
                            val listUser: MutableList<UserProfileHasIDModel> = mutableListOf()
                            for (i in it.documents.iterator()) {
                                val userProfileHasIDModel =
                                    i.toObject(UserProfileHasIDModel::class.java)
                                userProfileHasIDModel?.let { it1 -> listUser.add(it1) }
                            }
                            numberUser.text = listUser.size.toString()
                            manageUserAdapter.setData(listUser)
                        }
                    }
                }

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        callback.remove()
    }
}