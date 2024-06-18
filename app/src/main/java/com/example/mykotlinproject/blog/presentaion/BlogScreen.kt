package com.example.mykotlinproject.blog.presentaion

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mykotlinproject.blog.data.Post
import com.example.mykotlinproject.blog.functionality.fetchPostsFromDatabase
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun BlogScreen() {
        BlogScreen2(fetchPosts = { fetchPostsFromDatabase() })
    }


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BlogScreen2(fetchPosts: suspend () -> List<Post>) {
    val scope = rememberCoroutineScope()
    var posts by remember { mutableStateOf(listOf<Post>()) }

    LaunchedEffect(Unit) {
        scope.launch {
            posts = fetchPosts()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Beautiful Blog", color = Color.White) },
                Modifier.background(Color.Green)
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F2F2))
                .padding(16.dp)
        ) {
            if (posts.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF6200EE)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(posts) { post ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical =  8.dp),
                           // elevation = Eleva,
                        shape = RoundedCornerShape(8.dp)
                        ) {
                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = post.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color(0xFF333333),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = post.content,
                                fontSize = 16.sp,
                                color = Color(0xFF666666),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            }
                        }
                    }
                }
            }
        }
    }
}