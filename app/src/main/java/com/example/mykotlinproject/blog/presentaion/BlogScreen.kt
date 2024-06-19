package com.example.mykotlinproject.blog.presentaion

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
    BlogPage(fetchPosts = { fetchPostsFromDatabase() })
    }




@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BlogPage(fetchPosts: suspend () -> List<Post>) {
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
            title = { Text("Blog Subjects", color = Color.Black) },
            modifier = Modifier
                .background(color = Color.Blue )
                    .padding(2.dp).height(44.dp)
        )
    }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F2F2))
                .padding(16.dp)
        ) {
                LazyColumn(
                modifier = Modifier.padding(28.dp)
            ) {
                items(posts) { post ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical =  8.dp),
                        //elevation = 20.dp,
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
                                modifier = Modifier.padding(bottom = 12.dp) // More padding for title
                            )

                            // Add a divider line (optional)
                            HorizontalDivider(thickness = 1.dp, color = Color.Gray)

                            Text(
                                text = post.content,
                                fontSize = 16.sp,
                                color = Color(0xFF666666),
                                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp) // Adjust padding
                            )

                            // Consider adding a thumbnail image here
                            }
                        }
                    }
                }
            }
        }
    }