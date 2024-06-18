package com.example.mykotlinproject.blog.presentaion

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mykotlinproject.blog.data.Post
import com.example.mykotlinproject.blog.functionality.fetchPostsFromDatabase
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun BlogScreen() {
        BlogScreen2(fetchPosts = { fetchPostsFromDatabase() })
    }


@Composable
fun BlogScreen2(fetchPosts: suspend () -> List<Post>) {
    val scope = rememberCoroutineScope()
    var posts by remember { mutableStateOf(listOf<Post>()) }

    LaunchedEffect(Unit) {
        scope.launch {
            posts = fetchPosts()
        }
    }

    LazyColumn {
        items(posts) { post ->
            Text(
                text = post.title,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
            Text(text = post.content, modifier = Modifier.padding(8.dp))
        }
    }
}