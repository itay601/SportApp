package com.example.mykotlinproject.kalories.presantion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class FoodItem(val name: String, val calories: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false)
@Composable
fun FoodListPage() {
    val foodList = remember {
        listOf(
            FoodItem("Apple", 52),
            FoodItem("Banana", 96),
            FoodItem("Chicken Breast", 165),
            FoodItem("Broccoli", 55)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(text = "Food List") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(color = 0xFF7E2222))
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(foodList) { food ->
                FoodCard(food)
            }
        }
    }
}

@Composable
fun FoodCard(food: FoodItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
        .background( Color.White)
        //elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Fastfood,
                contentDescription = "Food Icon",
                tint = Color.Green,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = food.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "${food.calories} kcal per 100g", fontSize = 16.sp, color = Color.Gray)
            }
        }
    }
}