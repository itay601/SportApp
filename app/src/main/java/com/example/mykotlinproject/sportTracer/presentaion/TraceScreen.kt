package com.example.mykotlinproject.sportTracer.presentaion

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mykotlinproject.sportTracer.functionality.calculateDistance


//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = false)
//@Composable
//fun SportTracerPage() {
//    val distanceCovered = remember { mutableDoubleStateOf(0.0) } // Track distance in km
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        TopAppBar(
//            title = { Text(text = "Sport Tracer") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color(color = 0xFF7E2222))
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "Track Your Activity",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp)
//                .background(Color.White),
//           // elevation = 4.dp
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    text = "Distance Covered",
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Gray
//                )
//                Text(
//                    text = "${distanceCovered.doubleValue} km",
//                    fontSize = 36.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(vertical = 16.dp)
//                )
//                Button(onClick = { /* Start Tracking */ }) {
//                    Text(text = "Start Tracking")
//                }
//                Spacer(modifier = Modifier.height(8.dp))
//                Button(onClick = { /* Stop Tracking */ }) {
//                    Text(text = "Stop Tracking")
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            StatusCard(icon = Icons.Filled.LocationOn, label = "Current Location", value = "N/A")
//            StatusCard(icon = Icons.Filled.LocationOn, label = "Speed", value = "N/A")
//        }
//    }
//}

@Composable
fun StatusCard(icon: ImageVector, label: String, value: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(150.dp)
            .background(Color.White)
        //elevation = 4.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.Blue,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold,color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}













@Preview(showBackground = false)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportTracerPage2() {
    val distanceCovered = remember { mutableStateOf(0.0) }
    var lastLocation by remember { mutableStateOf<Location?>(null) }
    var isTracking by remember { mutableStateOf(false) }

    RequestLocationPermissions {
        if (isTracking) {
            StartLocationUpdates(isTracking) { location ->
                lastLocation?.let { previousLocation ->
                    val distance = calculateDistance(
                        previousLocation.latitude, previousLocation.longitude,
                        location.latitude, location.longitude
                    )
                    distanceCovered.value += distance
                }
                lastLocation = location
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(text = "Sport Tracer") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(color = 0xFF7E2222))
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Track Your Activity",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .background(Color.White),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Distance Covered",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Text(
                    text = "${distanceCovered.value} km",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Button(onClick = {
                    isTracking = true
                }) {
                    Text(text = "Start Tracking")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    isTracking = false
                }) {
                    Text(text = "Stop Tracking")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            StatusCard(icon = Icons.Filled.LocationOn, label = "Current Location", value = "N/A")
            StatusCard(icon = Icons.Filled.LocationOn, label = "Speed", value = "N/A")
        }
    }
}
