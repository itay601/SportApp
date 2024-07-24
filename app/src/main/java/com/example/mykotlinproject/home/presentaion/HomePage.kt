package com.example.mykotlinproject.home.presentaion

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false)
@Composable
fun HomePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(text = "Sport App") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(color = 0xFF7E2222)),
            actions = {
                IconButton(onClick = { /* Handle search */ }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
                IconButton(onClick = { /* Handle notifications */ }) {
                    Icon(Icons.Filled.Notifications, contentDescription = "Notifications")
                }
            }
        )
        SpaceHight()
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage()
            SpaceWidth()
            Column {
                Text(text = "Hello, John Doe", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "Level 5 Athlete", fontSize = 16.sp, color = Color.Gray)
            }
        }
        SpaceHight()
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            ActionButton(icon = Icons.Filled.FitnessCenter, text = "Workouts")
            ActionButton(icon = Icons.AutoMirrored.Filled.DirectionsRun, text = "Running")
            ActionButtonChallenge(icon = Icons.Filled.Star, text = "Challenges")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Popular Activities", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        ActivityList()
    }
}

@Composable
fun ProfileImage() {
    // Placeholder drawable if you don't have an image resource.
    // Use a vector drawable here if needed.
    val profileDrawable = Icons.Default.Person
    Icon(
        imageVector = profileDrawable,
        contentDescription = "Profile Picture",
        tint = Color.Gray,
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
    )
}

@Composable
fun ActionButtonChallenge(icon: ImageVector, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable { /* Handle click */ }
    ) {
        Icon(
            icon,
            contentDescription = text,
            modifier = Modifier
                .size(48.dp)
                .background(color = Color.Magenta, shape = CircleShape)
                .padding(12.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text, fontSize = 14.sp, color = Color.Gray)
    }
}
@Composable
fun Challenges() {

}
@Composable
fun ActionButton(icon: ImageVector, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable { /* Handle click */ }
    ) {
        Icon(
            icon,
            contentDescription = text,
            modifier = Modifier
                .size(48.dp)
                .background(color = Color.Magenta, shape = CircleShape)
                .padding(12.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text, fontSize = 14.sp, color = Color.Gray)
    }
}

@Composable
fun ActivityList() {
    Column {
        for (i in 1..3) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                //elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.FitnessCenter,
                        contentDescription = "Activity",
                        modifier = Modifier.size(64.dp)
                    )
                    SpaceWidth()
                    Column {
                        Text(text = "Activity Name", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Description", fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}
@Composable
fun SpaceHight(){
    Spacer(modifier = Modifier.height(16.dp))
}
@Composable
fun SpaceWidth(){
    Spacer(modifier = Modifier.width(16.dp) )
}