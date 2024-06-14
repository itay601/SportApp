package com.example.mykotlinproject

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var page = mutableStateOf("home") // Changed to mutableStateOf for state management
        setContent {
            BasicsCodelabTheme {
                Scaffold(
                    bottomBar = { BottomNavigationBar(page.value) { page.value = it } }
                ) {
                    Mypage(page.value, modifier = Modifier.fillMaxSize().padding(it))
                }
            }
        }
    }
}
//
@Composable
fun Mypage(pageName: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        when (pageName) {
            "greet" -> Greeting("Android")
            "home" -> HomePage()
            "profile" -> Profile()
            else -> throw IllegalArgumentException("Unknown page type")
        }
    }
}

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        Text(
            text = "Home Page",
            modifier = modifier.padding(24.dp)
        )
    }
}

@Composable
fun Profile(modifier: Modifier = Modifier) { // Fixed function name to start with an uppercase
    Surface(color = MaterialTheme.colorScheme.primary) {
        Text(
            text = "Profile",
            modifier = modifier.padding(24.dp)
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.primary) {
        Text(
            text = "Hello $name!",
            modifier = modifier.padding(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicsCodelabTheme {
        Column {
            Greeting("new member")
            Profile()
            HomePage()
            //BottomNavigationBar()
        }
    }
}
//@Preview(showBackground = true)
@Composable
fun BottomNavigationBar(currentPage: String, onPageSelected: (String) -> Unit) {
    NavigationBar{
        NavigationBarItem(
            selected = currentPage == "home",
            onClick = { onPageSelected("home") },
            label = { Text("Home") },
            icon = { Icon(Icons.Default.Home, contentDescription = null) }
        )
        NavigationBarItem(
            selected = currentPage == "greet",
            onClick = { onPageSelected("greet") },
            label = { Text("Greet") },
            icon = { Icon(Icons.Default.Face, contentDescription = null) }
        )
        NavigationBarItem(
            selected = currentPage == "profile",
            onClick = { onPageSelected("profile") },
            label = { Text("Profile") },
            icon = { Icon(Icons.Default.Person, contentDescription = null) }
        )
    }
}


@Composable
fun BasicsCodelabTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
   )
}
