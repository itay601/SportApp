package com.example.mykotlinproject.home.presentaion

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mykotlinproject.home.data.Exercise
import com.example.mykotlinproject.home.domain.FetchApi2
import kotlinx.coroutines.launch


@Composable
fun ActionButtonChallenge(navController: NavHostController, icon: ImageVector, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable { navController.navigate("exercise") }
    ) {
        Icon(
            icon,
            contentDescription = text,
            modifier = Modifier
                .size(48.dp)
                .background(color = Color.Magenta, shape = CircleShape)
                .padding(12.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text, fontSize = 14.sp, color = Color.Gray)
    }
}



@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ExerciseListScreen(navController: NavHostController, foodViewModel: FetchApi2 = viewModel()) {
    val exercises by foodViewModel.exerciseList.observeAsState(emptyList())
    var selectedMuscle by remember { mutableStateOf("biceps") }

    LaunchedEffect(Unit) {
        foodViewModel.execute(selectedMuscle)
    }

    Column {
        Button(onClick = { navController.navigate("home_page") }) {
            Text(text = "Return To Home Screen")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = selectedMuscle,
            onValueChange = { selectedMuscle = it },
            label = { Text("biceps") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                foodViewModel.viewModelScope.launch {
                    foodViewModel.execute(selectedMuscle)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Fetch Exercises")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(exercises) { exercise ->
                ExerciseItem(exercise)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ExerciseListScreen2(navController: NavHostController, foodViewModel: FetchApi2 = viewModel()) {
    val exercises by foodViewModel.exerciseList.observeAsState(emptyList())
    var selectedMuscle by remember { mutableStateOf("biceps") }

    var expanded by remember { mutableStateOf(false) }
    val muscles  = listOf("biceps", "triceps", "shoulders", "chest", "quadriceps","hamstrings",
        "glutes","adductors",)

    LaunchedEffect(Unit) {
        foodViewModel.execute(selectedMuscle)
    }

    SpaceHight()

    Column {
        Button(onClick = { navController.navigate("home_page") }) {
            Text(text = "Return To Home Screen")
        }

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedMuscle,
                onValueChange = { selectedMuscle = it },
                label = { Text("Select Muscle") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                muscles.forEach { muscle ->
                    DropdownMenuItem(
                        text = { Text(muscle) },
                        onClick = {
                            selectedMuscle = muscle
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                foodViewModel.viewModelScope.launch {
                    foodViewModel.execute(selectedMuscle)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Fetch Exercises")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(exercises) { exercise ->
                ExerciseItem(exercise)
            }
        }
    }
}


@Composable
fun ExerciseItem(exercise: Exercise) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Name: ${exercise.name}", fontWeight = FontWeight.Bold)
        Text(text = "Type: ${exercise.type}")
        Text(text = "Muscle: ${exercise.muscle}")
        Text(text = "Equipment: ${exercise.equipment}")
        Text(text = "Difficulty: ${exercise.difficulty}")
        Text(text = "Instructions: ${exercise.instructions}")
        Spacer(modifier = Modifier.height(4.dp))
        Divider()
    }
}



