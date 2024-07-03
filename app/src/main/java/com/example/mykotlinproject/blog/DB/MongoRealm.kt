package com.example.mykotlinproject.blog.DB


import com.example.mykotlinproject.blog.data.Post
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates.pull
import org.bson.Document
import org.bson.conversions.Bson


const val CONNECTION_STRING_URI_PLACEHOLDER:String = "mongodb+srv://itaymerel1212:ijwU1LoHXvFSMidD@cluster0.lqxvoov.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"




class MongoDBClient(uri: String =CONNECTION_STRING_URI_PLACEHOLDER, dbName: String = "sportBlog") {
    private val mongoClient = MongoClients.create(uri)
    private val database: MongoDatabase = mongoClient.getDatabase(dbName)

    val post = Post("2014-03-23", "11:45", "exercises After Workout", "This is the content of Example of question.", listOf("nice question", "really need to think on it"))
    var posts : List<Post>  = emptyList()

    //it all the database i need to find one specific
    fun getCollection(collectionName: String): MongoCollection<Document> {
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
            println(foundDocument?.toJson())

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
            //
            //check if title exist to delete
            //
            // Example: Insert a document
            val document = Document("title", this.post.title)
                .append("time", this.post.time)
                .append("date", this.post.date)
                .append("content", this.post.content)
                .append("coments", this.post.comments)
            collection.deleteOne(document)

            // Example: Find a document
            val foundDocument = collection.find(Document("title", this.post.title)).first()
            println(foundDocument?.toJson())

            mongoDBClient.close()
        }catch (e:Exception){
            println("$e")
        }
    }

    // Delete Comment from a Post
    fun deleteComment(postTitle: String, comment: String) {
        val collection = getCollection("subjects")

        // Find the post by title
        val filter: Bson = eq("title", postTitle)

        // Remove the comment from the comments array
        val update: Bson = pull("comments", comment)
        collection.updateOne(filter, update)

        val updatedDocument = collection.find(filter).first()
        println(updatedDocument?.toJson())
    }




    fun close() {
        mongoClient.close()
    }
}

fun main(){
    val f = MongoDBClient()
    f.insertPost()
    //f.getCollection("subjects")
}