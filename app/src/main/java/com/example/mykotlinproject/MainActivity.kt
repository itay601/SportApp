package com.example.mykotlinproject

import SportTracerPage
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.SportsMartialArts
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mykotlinproject.blog.presentaion.BlogPage
import com.example.mykotlinproject.home.presentaion.HomePage
import com.example.mykotlinproject.kalories.presantion.FoodListPage
import com.example.mykotlinproject.sportEquipment.presentation.EquipmentListPage


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
            "BlogScreen" -> BlogPage()
            "Food-kal" -> FoodListPage()
            "Sport-Equips" -> EquipmentListPage()
            "Tracer" -> SportTracerPage()
            else -> throw IllegalArgumentException("Unknown page type")
        }
    }
}



@Composable
fun BottomNavigationBar(currentPage: String, onPageSelected: (String) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            selected = currentPage == "home",
            onClick = { onPageSelected("home") },
            label = { Text("Home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
        )
        NavigationBarItem(
            selected = currentPage == "BlogScreen",
            onClick = { onPageSelected("BlogScreen") },
            label = { Text("Blog") },
            icon = { Icon(Icons.Default.SportsMartialArts, contentDescription = "Blog") }
        )
        NavigationBarItem(
            selected = currentPage == "Food-kal",
            onClick = { onPageSelected("Food-kal") },
            label = { Text("Food") },
            icon = { Icon(Icons.Default.Fastfood, contentDescription = "Food Calories") }
        )
        NavigationBarItem(
            selected = currentPage == "Sport-Equips",
            onClick = { onPageSelected("Sport-Equips") },
            label = { Text("Equipment") },
            icon = { Icon(Icons.Default.Sports, contentDescription = "Sport Equipment") }
        )
        NavigationBarItem(
            selected = currentPage == "Tracer",
            onClick = { onPageSelected("Tracer") },
            label = { Text("Tracer") },
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "Tracer") }
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
