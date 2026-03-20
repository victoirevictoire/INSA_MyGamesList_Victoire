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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.insa.mygameslist.data.IGDB.covers
import com.insa.mygameslist.data.IGDB.games
import com.insa.mygameslist.data.IGDB.genres
import kotlin.Any
@Composable
fun SearchScreen(pad: PaddingValues, backStack: SnapshotStateList<Any>, onGameClick: (Long) -> Unit,dao: JeuxDao) {
    var query by rememberSaveable { mutableStateOf("") }
    Column {
        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (query.isNotEmpty()) {
                    androidx.compose.material3.IconButton(onClick = { query = "" }) {
                        androidx.compose.material3.Icon(
                           painter = painterResource(com.insa.mygameslist.R.drawable.croix),
                            contentDescription = "Clear"
                        )
                    }
                }
            }
        )

        if (query.isEmpty()) {
            affichageTousFilms(pad, backStack, onGameClick,dao)
        }else{
            val gamesToShow = IGDB.games.filter { game ->
                //on garde seulement les jeux qui matchent
                val matchNom = game.name.contains(query, ignoreCase = true)
                //tri par genre
                val matchGenre = game.genres.any { genreId ->
                    IGDB.genres.find { it.id == genreId }?.name?.contains(
                        query,
                        ignoreCase = true
                    ) == true
                }

                val matchPlatform = game.platforms.any { platformId ->
                    IGDB.platforms.find {
                        it.id == platformId
                    }?.name?.contains(query, ignoreCase = true) == true
                }

                matchPlatform || matchGenre || matchNom

            }

            LazyColumn(
                modifier = Modifier
                    .padding(pad)
                    .background(Color.White)
            ) {

                items(gamesToShow) { game ->
                    affichageUnFilm(game.id, game.name, covers.find { it.id == game.cover }?.url, game.genres, pad,backStack, { onGameClick(game.id) },dao)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}