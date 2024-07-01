package com.example.mykotlinproject.blog.DB


//import com.example.mykotlinproject.blog.data.Post
//import com.mongodb.client.model.Filters.eq
//import com.mongodb.kotlin.client.coroutine.MongoClient
//import java.util.concurrent.Callable
//
//
//val post = Post("2024-03-23", "12:45", "Exercises After Workout", "This is the content of Example of question.", listOf("nice question", "really need to think on it"))
//val posts : List<Post>  = emptyList()
//
//
//
//const val CONNECTION_STRING_URI_PLACEHOLDER:String = "mongodb+srv://itaymerel1212:ijwU1LoHXvFSMidD@cluster0.lqxvoov.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
//
//
//class fetchPosts {
//    fun execute() {
//        val callable = Callable {
//            println("enter")
//            val uri = CONNECTION_STRING_URI_PLACEHOLDER
//            val mongoClient = MongoClient.create(uri)
//            try {
//                val database = mongoClient.getDatabase("sportBlog")
//                // Get a collection of documents of type Posts
//                val collection = database.getCollection<Post>("subjects")
//
//
//                val doc = collection.find(eq("title", "Exercises After Workout"))
//                if (doc != null) {
//                    println(doc)
//                } else {
//                    println("No matching documents found.")
//                }
//            } catch (e: Exception) {
//                println("$e")
//            } finally {
//                mongoClient.close()
//            }
//        }
//    }
//}
//
//fun main(){
//   // val l = fetchPosts()
//   // l.execute()
//}