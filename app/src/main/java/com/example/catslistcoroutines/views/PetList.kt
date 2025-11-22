package com.example.catslistcoroutines.views

import android.R
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
                items(petsUIState.pets) { pet ->
                    PetListItem(cat = pet)
                }
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
            var statusMessage by remember { mutableStateOf("") }

            AsyncImage(
                //model = "http://www.anbo-easj.dk/cv/andersBorjesson.jpg",
                //model = "https://cataas.com/cat/${cat.id}",
                model = ImageRequest.Builder(LocalContext.current)
                    //.data("https://cataas.com/cat")
                    .data("https://cataas.com/cat/${cat.id}")
                    .crossfade(true)
                    .listener(
                        onStart = {},
                        onSuccess = { request, metadata -> statusMessage = "Loaded" },
                        onError = { request, throwable ->
                            statusMessage =
                                "error loading ${request.data}: ${throwable?.throwable?.message}"
                        }
                    )

                    .build(),
                contentDescription = "Cute cat",
                placeholder = painterResource(R.drawable.ic_menu_day),
                error = painterResource(R.drawable.arrow_down_float),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillWidth,

                )
            Text(text = "Id:" + cat.id)
            if (statusMessage.isNotEmpty()) Text(text = "status:" + statusMessage)
            FlowRow(
                modifier = Modifier
                    .padding(start = 6.dp, end = 6.dp)
            ) {
                repeat(cat.tags.size) {
                    SuggestionChip(
                        modifier = Modifier
                            .padding(start = 3.dp, end = 3.dp),
                        onClick = { },
                        label = {
                            Text(text = cat.tags[it])
                        }
                    )
                }
            }
        }
    }
}