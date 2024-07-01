package com.example.mykotlinproject.sportEquipment.presentation

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mykotlinproject.R
import com.example.mykotlinproject.sportEquipment.data.EquipmentItem


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false)
@Composable
fun EquipmentListPage() {
    val equipmentList = remember {
        mutableListOf(
            EquipmentItem(R.drawable.dumbells, "Treadmill", "A device for walking or running while staying in one place."),
            EquipmentItem(R.drawable.treadmill, "Dumbbells", "A short bar with a weight at each end, used typically in pairs for exercise."),
            EquipmentItem(R.drawable.yoga_mat, "Yoga Mat", "A soft mat used during yoga to provide cushioning and support."),
            EquipmentItem(R.drawable.bycycle, "Bicycle", "A vehicle composed of two wheels held in a frame one behind the other."),
            EquipmentItem(R.drawable.resistancebands, "Resistance Bands", "Elastic bands used for strength training and physical therapy."),
            EquipmentItem(R.drawable.kettlebells, "Kettlebells", "A cast-iron or cast steel weight used to perform ballistic exercises."),
            EquipmentItem(R.drawable.jumprope, "Jump Rope", "A rope used in exercise or sport for jumping over as it swings under the feet."),
            EquipmentItem(R.drawable.foamroller, "Foam Roller", "A cylindrical tool used for self-myofascial release and massage."),
            EquipmentItem(R.drawable.medicineball, "Medicine Ball", "A weighted ball used for strength training and rehabilitation."),
            EquipmentItem(R.drawable.pullupbar, "Pull-up Bar", "A horizontal bar used for pull-up exercises to strengthen the upper body."),
            EquipmentItem(R.drawable.exerciseball, "Exercise Ball", "A large ball used for various forms of physical exercise and therapy."),
            EquipmentItem(R.drawable.rowingmachine, "Rowing Machine", "A machine used to simulate the action of watercraft rowing for exercise."),
            EquipmentItem(R.drawable.ellipticaltrainer, "Elliptical Trainer", "A stationary exercise machine used to simulate walking or running."),
            EquipmentItem(R.drawable.battleropes, "Battle Ropes", "Heavy ropes used for strength training through wave-like movements."),
            EquipmentItem(R.drawable.abroller, "Ab Roller", "A small wheel with handles used to exercise the abdominal muscles."),
            EquipmentItem(R.drawable.punchingbag, "Punching Bag", "A sturdy bag designed to be repeatedly punched for exercise or training."),
            EquipmentItem(R.drawable.benchpress, "Bench Press", "A weight training exercise that focuses on the pectoral muscles."),
            EquipmentItem(R.drawable.speedladder, "Speed Ladder", "A piece of equipment used for agility training."),
            EquipmentItem(R.drawable.stationarybike, "Stationary Bike", "A device with pedals, a saddle, and handlebars, used for indoor cycling.")
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
            title = { Text(text = "Equipment List") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(color = 0xFF7E2222))
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(equipmentList) { equipment ->
                EquipmentCard(equipment)
            }
        }
    }
}

@Composable
fun EquipmentCard(equipment: EquipmentItem) {
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


            //the icon need to be Image
            Image(
                painter = painterResource(id = equipment.image),
                contentDescription = equipment.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = equipment.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = equipment.description, fontSize = 16.sp, color = Color.Gray)
            }
        }
    }
}