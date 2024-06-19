package com.example.mykotlinproject.sportTracer.presentaion

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var tracking by remember { mutableStateOf(false) }
    var totalDistance by remember { mutableStateOf(0f) }
    var lastLocation by remember { mutableStateOf<Location?>(null) }
    val path = remember { mutableStateListOf<LatLng>() }

    val mapView = rememberMapViewWithLifecycle()
    val fusedLocationProviderClient = remember {
        com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(context)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Start tracking
        }
    }

    fun startTracking() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            tracking = true
            fusedLocationProviderClient.requestLocationUpdates(
                com.google.android.gms.location.LocationRequest.create().apply {
                    interval = 1000
                    fastestInterval = 500
                    priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
                },
                object : com.google.android.gms.location.LocationCallback() {
                    override fun onLocationResult(result: com.google.android.gms.location.LocationResult) {
                        val location = result.lastLocation ?: return
                        if (lastLocation != null) {
                            val distance = lastLocation!!.distanceTo(location)
                            totalDistance += distance / 1000 // Convert to kilometers
                            path.add(LatLng(location.latitude, location.longitude))
                        }
                        lastLocation = location
                    }
                },
                null
            )
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    fun stopTracking() {
        tracking = false
        fusedLocationProviderClient.removeLocationUpdates(object : com.google.android.gms.location.LocationCallback() {})
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Distance Tracker") })
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MapViewContainer(mapView = mapView, path = path)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Distance: ${"%.2f".format(totalDistance)} km")
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (tracking) {
                            stopTracking()
                        } else {
                            startTracking()
                        }
                    }
                ) {
                    Text(if (tracking) "Stop Tracking" else "Start Tracking")
                }
            }
        }
    )
}

@Composable
fun MapViewContainer(mapView: MapView, path: List<LatLng>) {
    val context = LocalContext.current
    val mapState = rememberMapState()

    LaunchedEffect(mapView) {
        mapView.getMapAsync { googleMap ->
            googleMap.uiSettings.isZoomControlsEnabled = true
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 15f))
        }
    }

    AndroidView(
        factory = { mapView },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) { mapView ->
        mapView.getMapAsync { googleMap ->
            val polylineOptions = PolylineOptions().addAll(path).color(Color.RED).width(5f)
            googleMap.clear()
            googleMap.addPolyline(polylineOptions)
            if (path.isNotEmpty()) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(path.last()))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            // Initialize the MapView
            onCreate(null)
            onResume()
        }
    }

    DisposableEffect(key1 = mapView) {
        onDispose {
            mapView.onPause()
            mapView.onDestroy()
        }
    }

    return mapView
}



class MapState(
    var center: LatLng = LatLng(0.0, 0.0),
    var zoom: Float = 15f
)

@Composable
fun rememberMapState(
    center: LatLng = LatLng(0.0, 0.0),
    zoom: Float = 15f
): MapState {
    return remember { MapState(center, zoom) }
}