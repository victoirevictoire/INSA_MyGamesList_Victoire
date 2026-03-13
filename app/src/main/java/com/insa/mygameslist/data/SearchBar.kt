package com.insa.mygameslist.data

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.insa.mygameslist.data.IGDB.covers
import kotlin.Any
@Composable
fun SearchScreen(pad: PaddingValues, backStack: SnapshotStateList<Any>, onGameClick: (Long) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }
    Column {
        TextField(value = query, onValueChange = { query = it }, placeholder = { Text("Search") }, modifier = Modifier.fillMaxWidth())
        if (query.isEmpty()) {
            affichageTousFilms(pad, backStack, onGameClick)
        }else{
            val gamesToShow = IGDB.games.filter {
                it.name.contains(query, ignoreCase = true)
            }

            LazyColumn(
                modifier = Modifier
                    .padding(pad)
                    .background(Color.White)
            ) {

                items(gamesToShow) { game ->
                    affichageUnFilm(game.id, game.name, covers.find { it.id == game.cover }?.url, game.genres, pad, backStack) { onGameClick(game.id) }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}