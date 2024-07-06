package com.example.mykotlinproject.blog.presentaion

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Topic(val title: String, val description: String, val comments: List<Comment>, var likes: Int, var liked: Boolean)
data class Comment(val username: String, val content: String)



@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogPage() {
//mongo: MongoDBClient = viewModel()
    //val yourList by mongo.yourList.observeAsState(initial = emptyList())

    val topics = remember {
        mutableStateListOf(
            Topic("Running Tips", "Discuss and share your running tips!", listOf(), 0, false),
            Topic("Healthy Eating", "Share your favorite healthy recipes.", listOf(), 0, false),
            Topic("Workout Routines", "Talk about different workout routines.", listOf(), 0, false)
        )
    }

    //LaunchedEffect (Unit){
      //  mongo.printDB()
    //}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(text = "Blog Topics") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(color = 0xFF7E2222))
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Navigate to Create Topic Page */ }) {
            Text(text = "Create New Topic")
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(topics) { topic ->
                TopicCard(topic = topic) {   }
            }
        }
    }
}

@Composable
fun TopicCard(topic: Topic, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
            .background(Color.White)
        //elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = topic.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = topic.description, fontSize = 16.sp, color = Color.Gray)
            }
        }
    }
}
//
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicPage(topic: Topic) {
    var newComment by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(text = topic.title) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(color = 0xFF7E2222))
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = topic.description, fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Handle like/unlike */ }) {
                Icon(
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = if (topic.liked) "Unlike" else "Like",
                    tint = if (topic.liked) Color.Blue else Color.Gray
                )
            }
            Text(text = "${topic.likes} likes")
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(topic.comments) { comment ->
                CommentCard(comment)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newComment,
            onValueChange = { newComment = it },
            label = { Text("Add a comment") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Handle add comment */ }) {
            Text(text = "Post Comment")
        }
    }
}

@Composable
fun CommentCard(comment: Comment) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White)
        //elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = comment.username, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = comment.content, fontSize = 16.sp, color = Color.Gray)
            }
        }
    }
}