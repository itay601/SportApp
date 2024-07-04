package com.example.mykotlinproject.blog.DB


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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


const val CONNECTION_STRING_URI_PLACEHOLDER:String = "mongodb+srv://itaymerel1212:ijwU1LoHXvFSMidD@cluster0.lqxvoov.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"




class MongoDBClient(uri: String =CONNECTION_STRING_URI_PLACEHOLDER, dbName: String = "sportBlog") {

    private var list1 = mutableListOf<String>()

    private var Response: String? = null
    // MutableLiveData for your list
    private val _yourList = MutableLiveData<List<Post>>()
    // Exposed as LiveData
    val yourList: LiveData<List<Post>>
        get() = _yourList



    private val mongoClient = MongoClients.create(uri)
    private val database: MongoDatabase = mongoClient.getDatabase(dbName)

    val post = Post("2014-03-23", "11:45", "After Workout", "This is the content of Example of question.", listOf("nice question", "really need to think on it"))
    var posts : List<Post>  = emptyList()

    //it all the database i need to find one specific
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
            val collection = this.getCollection(collectionName)
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

    fun printDB() {
        val uri = CONNECTION_STRING_URI_PLACEHOLDER // Replace with your MongoDB URI
        val dbName = "sportBlog" // Replace with your database name
        val collectionName = "subjects" // Replace with your collection name

        val mongoDBClient = MongoDBClient(uri, dbName)
        val collection = getCollection("subjects")

        val alldata = collection.find()
        for (doc in alldata) {
            println(doc.toJson())
        }
        mongoDBClient.close()
    }


    fun clickedOnBlogSub(Blog: String) {
        val uri = CONNECTION_STRING_URI_PLACEHOLDER // Replace with your MongoDB URI
        val dbName = "sportBlog" // Replace with your database name
        val collectionName = "subjects" // Replace with your collection name

        val mongoDBClient = MongoDBClient(uri, dbName)
        val collection = getCollection("subjects")

        // Find the post by title
        val filter: Bson = eq("title", post)
        val updatedDocument = collection.find(filter).first()
        println(updatedDocument?.toJson())
        mongoDBClient.close()
        if (updatedDocument != null) {
            transferData(updatedDocument.toJson())
        }
    }
    private fun transferData(data: String){
        val gson = Gson()
        val listType = object : TypeToken<List<Post>>() {}.type
        this._yourList.postValue(gson.fromJson(this.Response, listType))

        return gson.fromJson(data, listType)
    }


    fun close() {
        mongoClient.close()
    }

}

fun main(){
    val f = MongoDBClient()
    //f.insertPost()
    //f.deletePost()
    //f.addComment("exercises After Workout","hello")
    f.printDB()
}