package com.example.mykotlinproject.blog.DB

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mykotlinproject.blog.data.Post
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates.push
import org.bson.Document
import org.bson.conversions.Bson
import java.util.concurrent.Callable
import java.util.concurrent.Executors

const val CONNECTION_STRING_URI_PLACEHOLDER:String = "mongodb+srv://itaymerel1212:ijwU1LoHXvFSMidD@cluster0.lqxvoov.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"


class MongoDBClient(uri: String =CONNECTION_STRING_URI_PLACEHOLDER, dbName: String = "sportBlog"): ViewModel()  {

    //private var list1 = mutableListOf<String>()
    private var Response: String? = null
    // MutableLiveData for your list
    private val _yourList = MutableLiveData<List<Post>>()
    // Exposed as LiveData
    val yourList: LiveData<List<Post>>
        get() = _yourList

    private val mongoClient = MongoClients.create(uri)
    private val database: MongoDatabase = mongoClient.getDatabase(dbName)

    //helper
    val post = Post("2014-03-23", "11:45", "After Workout", "This is the content of Example of question.", listOf("nice question", "really need to think on it"))
    var posts : List<Post>  = emptyList()



    //helper function for get database
    fun getCollection(collectionName: String = "subjects"): MongoCollection<Document> {
        //println(database.getCollection(collectionName))
        return database.getCollection(collectionName)
    }


    //Insert Main Post
    fun insertPost(post:Post = this.post){
        val uri = CONNECTION_STRING_URI_PLACEHOLDER // Replace with your MongoDB URI
        val dbName = "sportBlog" // Replace with your database name
        val collectionName = "subjects" // Replace with your collection name

        try {
            val mongoDBClient = MongoDBClient(uri, dbName)
            val collection = mongoDBClient.getCollection(collectionName)
            //
            //check if title is unique
            //
            // Example: Insert a document
            val document = Document("title", this.post.title)
                .append("time", this.post.time)
                .append("date", this.post.date)
                .append("content", this.post.content)
                .append("coments", this.post.comments)
            collection.insertOne(document)

            // Example: Find a document
            val foundDocument = collection.find(Document("title", this.post.title)).first()
            if (foundDocument != null) {
                println(foundDocument.toJson())
            }
            mongoDBClient.close()
        }catch (e:Exception){
            println("$e")
        }
    }


    fun deletePost(post:Post = this.post){
        val uri = CONNECTION_STRING_URI_PLACEHOLDER // Replace with your MongoDB URI
        val dbName = "sportBlog" // Replace with your database name
        val collectionName = "subjects" // Replace with your collection name

        try {
            val mongoDBClient = MongoDBClient(uri, dbName)
            val collection = mongoDBClient.getCollection(collectionName)
            // Example: Find a document
            val foundDocument = collection.find(Document("title", this.post.title)).first()
            if (foundDocument != null) {
                collection.deleteOne(foundDocument)
            }
            println(foundDocument?.toJson())

            mongoDBClient.close()
        }catch (e:Exception){
            println("$e")
        }
    }


    fun addComment(postTitle: String, comment: String) {
        val uri = CONNECTION_STRING_URI_PLACEHOLDER // Replace with your MongoDB URI
        val dbName = "sportBlog" // Replace with your database name
        val collectionName = "subjects" // Replace with your collection name

        val mongoDBClient = MongoDBClient(uri, dbName)
        val collection = getCollection("subjects")

        // Find the post by title
        val filter: Bson = eq("title", postTitle)

        // Add the comment to the comments array
        val update: Bson = push("coments", comment)
        collection.updateOne(filter, update)

        val updatedDocument = collection.find(filter).first()
        println(updatedDocument?.toJson())
        mongoDBClient.close()
    }

    fun printDB(): MutableList<Post> {
        val uri = CONNECTION_STRING_URI_PLACEHOLDER // Replace with your MongoDB URI
        val dbName = "sportBlog" // Replace with your database name
        val collectionName = "subjects" // Replace with your collection name

        val mongoDBClient = MongoDBClient(uri, dbName)
        val collection = getCollection(collectionName)

        val alldata = collection.find()
        val dataList = mutableListOf<Post>()

        for (doc in alldata) {
            println(doc.toJson())
            val blog = doc.toJson()
            val postBlog:Post = transferData(blog)
            dataList.add(postBlog)
        }
        mongoClient.close()
        this._yourList.postValue(dataList)
        return dataList
    }




//    fun clickedOnBlogSub(Blog: String) {
//        val uri = CONNECTION_STRING_URI_PLACEHOLDER // Replace with your MongoDB URI
//        val dbName = "sportBlog" // Replace with your database name
//        val collectionName = "subjects" // Replace with your collection name
//
//        val mongoDBClient = MongoDBClient(uri, dbName)
//        val collection = getCollection(collectionName)
//
//        // Find the post by title
//        val filter: Bson = eq("title", Blog)
//        val updatedDocument = collection.find(filter).first()
//        println(updatedDocument?.toJson())
//        mongoDBClient.close()
//        if (updatedDocument != null) {
//            transferData(updatedDocument.toJson())
//        }
//    }
    private fun transferData(data: String): Post {
        val gson = Gson()
        val listType = object : TypeToken<Post>() {}.type
        return gson.fromJson(data, listType)
    }

    fun execute(){
        val callable = Callable {
            val l = printDB()
            println(l)
            return@Callable l
        }

        val executorService = Executors.newSingleThreadExecutor()
        val future = executorService.submit(callable)
        try {
            val result = future.get()
            // Blocks until the task is completed and returns the result
            println("Callable result: $result")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            executorService.shutdown()
        }
    }

    fun close() {
        mongoClient.close()
    }

}

//fun main(){
//    val f = MongoDBClient()
//    //f.insertPost()
//    //f.deletePost()
//    //f.addComment("exercises After Workout","hello")
//    f.printDB()
////    f.execute()
//}
