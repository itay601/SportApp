package com.example.mykotlinproject

import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.mykotlinproject.blog.presentaion.BlogScreen
import com.example.mykotlinproject.sportEquipment.presentation.ProductScreen
import com.example.mykotlinproject.util.Event
import com.example.mykotlinproject.util.EventBus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var page = mutableStateOf("home") // Changed to mutableStateOf for state management
        setContent {
            BasicsCodelabTheme {
                Scaffold(
                    bottomBar = { BottomNavigationBar(page.value) { page.value = it } }
                ) {
                    Mypage(
                        page.value, modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    )
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
            "home" -> HomePage()
            "BlogScreen" -> BlogScreen()
            "profile" -> Profile()
            "GreetingPreview" -> GreetingPreview()
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
            selected = currentPage == "BlogScreen",
            onClick = { onPageSelected("BlogScreen") },
            label = { Text("BlogScreen") },
            icon = { Icon(Icons.Default.Face, contentDescription = null) }
        )
        NavigationBarItem(
            selected = currentPage == "profile",
            onClick = { onPageSelected("profile") },
            label = { Text("Profile") },
            icon = { Icon(Icons.Default.Person, contentDescription = null) }
        )
        NavigationBarItem(
            selected = currentPage == "GreetingPreview",
            onClick = { onPageSelected("GreetingPreview") },
            label = { Text("GreetingPreview") },
            icon = { Icon(Icons.Default.Person, contentDescription = null) }
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



@Composable
fun BasicsCodelabTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
   )
}
