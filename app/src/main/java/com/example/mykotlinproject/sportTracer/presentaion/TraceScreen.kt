
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

// Assuming a ViewModel to manage location tracking, distance, and calories
class SportsTrackerViewModel : ViewModel() {
    var isTracking = false
    var distance = 0.0 // in km
    var calories = 0 // in kcal
    // ... other properties and tracking logic
}

@Preview(showBackground = true)
@Composable
fun SportsTrackerScreen(viewModel: SportsTrackerViewModel = viewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Google Map composable here
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Distance: ${viewModel.distance} km", color = Color.Black)
            Spacer(modifier = Modifier.width(24.dp))
            Text(text = "Calories: ${viewModel.calories} kcal", color = Color.Black)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            if (viewModel.isTracking) {
                Button(
                    onClick = { viewModel.isTracking = false },
                    //colors = Back
                ) {
                    Text(text = "Stop Tracking")
                }
            } else {
                Button(onClick = { viewModel.isTracking = true }) {
                    Text(text = "Start Tracking")
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { /* Reset logic */ }) {
                Text(text = "Reset")
            }
        }
        // Optional Settings button (not shown here)
    }
}