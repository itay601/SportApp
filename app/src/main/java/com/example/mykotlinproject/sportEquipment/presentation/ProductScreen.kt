package com.example.mykotlinproject.sportEquipment.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
internal fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel()
){
    val state  by viewModel.state.collectAsStateWithLifecycle()
    ProductContent(state = state)
}

@Composable
fun ProductContent(
    state:ProductViewState
){
    LoadingDialog(isLoading = state.isLaoding)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarProduct(title = "Products")
        }
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.padding(top = it.calculateTopPadding())){
            items(state.prodacts){  product ->

            }

        }
    }

}