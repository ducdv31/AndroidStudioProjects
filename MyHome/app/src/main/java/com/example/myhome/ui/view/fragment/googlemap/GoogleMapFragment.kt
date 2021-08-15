package com.example.myhome.ui.view.fragment.googlemap

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.myhome.R
import com.example.myhome.ui.view.activity.main.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission

class GoogleMapFragment : Fragment(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private val mainActivity: MainActivity by lazy {
        activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_google_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment: SupportMapFragment? = mainActivity.supportFragmentManager
            .findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        checkPermission()
    }

    override fun onResume() {
        super.onResume()
        getCurrentLocation()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getCurrentLocation()
    }

    private fun checkPermission() {
        TedPermission.with(requireContext())
            .setPermissionListener(tedPermissionListener)
            .setDeniedMessage(mainActivity.getString(R.string.deny_message))
            .setPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .check()
    }

    private val tedPermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            getCurrentLocation()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            mainActivity.showToast(mainActivity.getString(R.string.deny_permission))
        }

    }

    private fun getCurrentLocation() {
        val mFusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(mainActivity)
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mFusedLocationClient.lastLocation.addOnSuccessListener(object :
            OnSuccessListener<Location> {
            override fun onSuccess(location: Location?) {
                if (location == null) {
                    return
                }
                val currentLocation = LatLng(location.latitude, location.longitude)
                mMap?.addMarker(
                    MarkerOptions().position(currentLocation)
                        .title("current location")
                )
                mMap?.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
            }

        })
    }
}