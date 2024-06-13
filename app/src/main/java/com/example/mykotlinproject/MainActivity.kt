package com.example.mykotlinproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                //Mypage(modifier = Modifier.fillMaxSize())
                val pageType = "Home" // This can be dynamic
                val page = PageFactory.createPage(pageType)
                page.Content(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun Mypage(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Greeting("Android")
    }
}
interface Page {
    @Composable
    fun Content(modifier: Modifier)
}
class HomePage : Page {
    @Preview(showBackground = true)
    @Composable
    override fun Content(modifier: Modifier) {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            Text(text = "Home Page", modifier = Modifier.padding(16.dp))
        }
    }
}

class ProfilePage : Page {
    @Preview(showBackground = true)
    @Composable
    override fun Content(modifier: Modifier) {
        BasicsCodelabTheme {
            Surface(
                modifier = modifier,
                color = MaterialTheme.colorScheme.background
            ) {
                Text(text = "Profile Page", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

object PageFactory {
    fun createPage(pageType: String): Page {
        return when (pageType) {
            "Home" -> HomePage()
            "Profile" -> ProfilePage()
            else -> throw IllegalArgumentException("Unknown page type")
        }
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
        Mypage()
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
