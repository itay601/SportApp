package com.example.mykotlinproject.blog.functionality

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RunWith(MockitoJUnitRunner::class)
class DatabaseTest {

    @Mock
    private lateinit var mockFirestore: FirebaseFirestore

    @Mock
    private lateinit var mockCollectionReference: CollectionReference

    @Mock
    private lateinit var mockDocumentReference: DocumentReference

    @Mock
    private lateinit var mockTask: Task<QuerySnapshot>

    @Mock
    private lateinit var mockQuerySnapshot: QuerySnapshot

    @Captor
    private lateinit var documentCaptor: ArgumentCaptor<(DocumentReference) -> Unit>

    @Captor
    private lateinit var exceptionCaptor: ArgumentCaptor<(Exception) -> Unit>

    private lateinit var database: Database2

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(mockFirestore.collection("posts")).thenReturn(mockCollectionReference)
        database = Database2(mockFirestore)
    }

//    @Test
//    fun testAddPost() {
//        val post = Post("2023-07-21", "10:00", "Test Title", "Test Content", listOf())
//
//        database.finalAddPost(post)
//
//        verify(mockCollectionReference).add(eq(post))
//
//        // Simulate success
//        documentCaptor.value.invoke(mockDocumentReference)
//        // Simulate failure
//        exceptionCaptor.value.invoke(Exception("Test Exception"))
//    }

    @Test
    fun testGetCurrentDate() {
        val expectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val currentDate = database.getCurrentDate()
        assertEquals(expectedDate, currentDate)
    }

    @Test
    fun testGetCurrentTime() {
        val expectedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val currentTime = database.getCurrentTime()
        assertEquals(expectedTime, currentTime)
    }

    @Test
    fun testFetchPostsFromFirebase() = runBlocking {
//        `when`(mockCollectionReference.get()).thenReturn(mockTask)
//        `when`(mockTask.await()).thenReturn(mockQuerySnapshot)
//        `when`(mockQuerySnapshot.documents).thenReturn(listOf(/* mock documents */))
        val posts = database.fetchPostsFromFirebase()
        assertNotNull(posts)
    }
}