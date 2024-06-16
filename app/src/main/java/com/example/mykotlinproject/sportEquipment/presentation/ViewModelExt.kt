package com.example.mykotlinproject.sportEquipment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykotlinproject.util.EventBus
import kotlinx.coroutines.launch

fun ViewModel.sendEvent(event: Any){
    viewModelScope.launch {
        EventBus.sendEvent(event)
    }
}