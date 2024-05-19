package com.example.jaloliddinhaykaligavertualsayohat.fragment


import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codebyashish.googledirectionapi.AbstractRouting
import com.codebyashish.googledirectionapi.ErrorHandling
import com.codebyashish.googledirectionapi.RouteDrawing
import com.codebyashish.googledirectionapi.RouteInfoModel
import com.codebyashish.googledirectionapi.RouteListener
import com.example.jaloliddinhaykaligavertualsayohat.R
import com.example.jaloliddinhaykaligavertualsayohat.databinding.FragmentMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.RoundCap
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import kotlin.collections.ArrayList


class MapFragment : Fragment(), OnMapReadyCallback, RouteListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    private var mMap: GoogleMap? = null
    private var locationPermissionGranted = false
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient
    private lateinit var binding: FragmentMapBinding
    private var lastKnownLocation: Location? = null
    private val TAG = "Map fragment"
    private var userLocation: LatLng? = null
    private var endLocation: LatLng? = null
    private var polylines: ArrayList<Polyline>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getPermissionListener()
        binding = FragmentMapBinding.inflate(layoutInflater)
        getDeviceLocation()
        val mapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        Places.initialize(requireContext(), getString(R.string.api_key))
        placesClient = Places.createClient(requireContext())
        locationPermissionLouncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        endLocation = LatLng(41.55131769384426, 60.63141196886147)
        binding.go.setOnClickListener {
            val markerOptions = MarkerOptions().position(endLocation!!).title("Jaloliddin Manguberdi haykali")
            mMap!!.addMarker(markerOptions)
            mMap?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        endLocation!!.latitude,
                        endLocation!!.longitude
                    ), 17F
                )
            )
            getRoute(userLocation, endLocation)
        }
        return binding.root
    }

    private val locationPermissionLouncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                locationPermissionGranted = true
                updateLocationUI()
                getDeviceLocation()
            } else {
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setCancelable(false)
                dialog.setTitle("Joylashuni aniqlash")
                dialog.setMessage("Bu ilovada joylashuvingiz funksiyasii qo'shilgan iltimos joylashuvingizni yoqing.")
                dialog.setPositiveButton("Ruxsat berish") { p0, p1 ->
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        1
                    )
                }
                dialog.setNegativeButton("Ruxsat bermaslik") { p0, p1 ->
                    p0?.dismiss()
                    locationPermissionGranted = false
                }
                dialog.show()
            }
        }

    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            userLocation = (LatLng(
                                lastKnownLocation!!.latitude,
                                lastKnownLocation!!.longitude
                            ))
                            mMap?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), 17F
                                )
                            )
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        mMap?.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(LatLng(41.569928, 60.630431), 17F)
                        )
                        mMap?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun updateLocationUI() {
        if (mMap == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                mMap?.isMyLocationEnabled = true
                mMap?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                mMap?.isMyLocationEnabled = false
                mMap?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                locationPermissionLouncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun getPermissionListener() {
        val lm = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gpsEnabled = false
        var networkEnabled = false
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
        }

        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
        }

        if (!gpsEnabled) {
            AlertDialog.Builder(context)
                .setCancelable(false)
                .setMessage("GPS o'chiq iltimos uni yoqing")
                .setPositiveButton("Sozlamalar") { dialog, which ->
                    requireContext().startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                .setNegativeButton("Orqaga") { dialog, which ->
                    findNavController().navigateUp()
                }
                .show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        mMap!!.uiSettings.isZoomControlsEnabled = true
    }

    private fun getRoute(start: LatLng?, end: LatLng?) {
        if (start == null || end == null) {
            Toast.makeText(requireContext(),"Start yoki end location birortasi null", Toast.LENGTH_SHORT).show()
        } else {
            val routeDrawing = RouteDrawing.Builder()
                .context(requireContext())
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this).alternativeRoutes(true)
                .waypoints(start, end)
                .build()
            routeDrawing.execute()
        }
    }

    override fun onRouteFailure(e: ErrorHandling?) {
        Log.w("Xatoliq ", "onRoutingFailure: " + e)
    }

    override fun onRouteStart() {
        Toast.makeText(requireContext(), "Muvafaqqiyatli ", Toast.LENGTH_SHORT).show()
    }

    override fun onRouteSuccess(list: ArrayList<RouteInfoModel>, indexing: Int) {
        if (polylines != null) {
            polylines!!.clear()
        } else {
            val polylineOptions = PolylineOptions()
            polylines = ArrayList()
            for (i in 0 until list.size) {
                if (i == indexing) {
                    polylineOptions.color(Color.BLUE)
                    polylineOptions.width(12f)
                    polylineOptions.addAll(list[indexing].points)
                    polylineOptions.startCap(RoundCap())
                    polylineOptions.endCap(RoundCap())
                    val polyline = mMap!!.addPolyline(polylineOptions)
                    polylines!!.add(polyline)
                }
            }
        }
    }

    override fun onRouteCancelled() {
        Toast.makeText(requireContext(), "Xatolik", Toast.LENGTH_SHORT).show()
    }
}