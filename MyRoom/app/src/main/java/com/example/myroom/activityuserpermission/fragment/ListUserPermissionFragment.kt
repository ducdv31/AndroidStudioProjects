package com.example.myroom.activityuserpermission.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.R
import com.example.myroom.activitymain.MyApplication
import com.example.myroom.activityuserpermission.model.UserPermission
import com.example.myroom.activityuserpermission.ActivityUserPermission
import com.example.myroom.activityuserpermission.adapter.RCVUserPermissionAdapter
import com.example.myroom.activityuserpermission.model.UserPermissionImg
import com.example.myroom.components.dialog.PermissionSelectDialog

class ListUserPermissionFragment : Fragment() {

    private val TAG_USER_PERMISSION_DATA_DIALOG = "send data from adapter to dialog"

    lateinit var rcvUserPermissionAdapter: RCVUserPermissionAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var permissionSelectDialog: PermissionSelectDialog
    lateinit var activityUserPermission: ActivityUserPermission

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_list_user_permission, container, false)
        recyclerView = fragView.findViewById(R.id.rcv_list_user_permission)
        activityUserPermission = ActivityUserPermission()
        permissionSelectDialog =
            PermissionSelectDialog(object : PermissionSelectDialog.IClickUserShowPermission {
                override fun onClickUserPermission(value: Int) {
                    val bundle: Bundle? = permissionSelectDialog.arguments
                    bundle?.let {
                        /*
                        level > or null -> can't set permission
                        level < -> can set permission
                         */
                        val userPermission: UserPermissionImg =
                            bundle.get(TAG_USER_PERMISSION_DATA_DIALOG) as UserPermissionImg
                        /* check user */
                        MyApplication.comparePermission(value, userPermission)
                    }
                }
            })

        rcvUserPermissionAdapter = RCVUserPermissionAdapter(
            requireActivity(),
            object : RCVUserPermissionAdapter.IClickUserPermission {
                override fun onClickUserPermission(userPermissionImg: UserPermissionImg) {
                    val bundle = Bundle()
                    bundle.putSerializable(TAG_USER_PERMISSION_DATA_DIALOG, userPermissionImg)
                    permissionSelectDialog.arguments = bundle
                    permissionSelectDialog.show(
                        requireActivity().supportFragmentManager,
                        "Select permission"
                    )
                }

                override fun onClickEditPermissionUser(userPermissionImg: UserPermissionImg) {
                    val bundle = Bundle()
                    bundle.putSerializable(TAG_USER_PERMISSION_DATA_DIALOG, userPermissionImg)
                    permissionSelectDialog.arguments = bundle
                    permissionSelectDialog.show(
                        requireActivity().supportFragmentManager,
                        "Select permission"
                    )
                }

            })

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = rcvUserPermissionAdapter

        activityUserPermission.getUserPermissionData(rcvUserPermissionAdapter)

        return fragView
    }
}