package com.example.mykotlinproject.sportEquipment.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykotlinproject.sportEquipment.domain.ProductsRepository
import com.example.mykotlinproject.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _state  = MutableStateFlow(ProductViewState())
    val state = _state.asStateFlow()

    init{
        getProducts()
    }


    fun getProducts(){
        viewModelScope.launch {
            _state.update {
                it.copy(isLaoding = true)
            }
            productsRepository.getProducts()
                .map { products ->
                    _state.update {
                        it.copy(prodacts = products)
                    }
                }.mapLeft{ error ->
                    _state.update {
                        it.copy(error = error.error.message)
                    }
                    sendEvent(Event.Toast(error.error.message))
                }
            _state.update {
                it.copy(isLaoding = false)
            }
        }
    }
}