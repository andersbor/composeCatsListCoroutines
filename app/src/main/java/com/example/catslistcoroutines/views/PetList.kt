package com.example.catslistcoroutines.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.catslistcoroutines.data.Cat
import com.example.catslistcoroutines.viewmodel.PetsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PetList(modifier: Modifier = Modifier) {
    val petsViewModel: PetsViewModel = koinViewModel()
    val petsUIState by petsViewModel.petsUIState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = petsUIState.isLoading) {
            CircularProgressIndicator()
        }

        AnimatedVisibility(visible = petsUIState.pets.isNotEmpty()) {
            LazyColumn {
                items(petsUIState.pets, key = { cat -> cat.id })
                { pet -> PetListItem(cat = pet) }
            }
        }
        AnimatedVisibility(visible = petsUIState.error != null) {
            Text(text = petsUIState.error ?: "")
        }
    }
}

@Composable
fun PetListItem(cat: Cat) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            //var statusMessage by remember { mutableStateOf("") }
            val context = LocalContext.current
            val model = remember(cat.id) {
                ImageRequest.Builder(context)
                    .data("https://cataas.com/cat/${cat.id}")
                    .crossfade(true)
                    /*.listener(
                    onStart = {},
                    onSuccess = { request, result -> statusMessage = "Loaded" },
                    onError = { request, result ->
                        statusMessage =
                            "error loading ${request.data}: ${result.throwable.message}"
                    }                )*/
                    .build()
            }
            AsyncImage(
                model = model,
                contentDescription = "Cat with id ${cat.id}",
                placeholder = rememberVectorPainter(Icons.Default.Image),
                error = rememberVectorPainter(Icons.Default.BrokenImage),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillWidth,

                )
            if (cat.tags.isNotEmpty()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    cat.tags.forEach { tag ->
                        SuggestionChip(
                            onClick = { },
                            label = { Text(text = tag) }
                        )
                    }
                }
            }
        }
    }
}