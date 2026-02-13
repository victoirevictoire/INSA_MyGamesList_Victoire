package com.insa.mygameslist.data

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.insa.mygameslist.data.IGDB.covers
import com.insa.mygameslist.data.IGDB.games
import com.insa.mygameslist.data.IGDB.genres
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun affichageUnFilm(idFilm : Long, nomFilm : String?, URL : String?, Genres : List<Long>,pad : PaddingValues,backStack : SnapshotStateList<Any>,onClick:()->Unit={}):Unit{

        var afficherGenres : String ="Genres : "
        for (idGenre in Genres){
            afficherGenres += genres.find { it.id == idGenre}?.name
            afficherGenres +=" "
        }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 25.dp))
            .background(Color.LightGray)
            .clickable(onClick=onClick)

    ){
        Column{
            AsyncImage(
                model="https:"+ URL,
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )
        }
        Column{
            if (nomFilm != null) {
                Text(nomFilm,fontWeight = FontWeight.Bold)
            }
            if (afficherGenres != null) {
                Text(afficherGenres)
            }
        }

        //Button(onClick = { backStack.add(GameExample(idFilm)) }) {
          //  Text(text = "->")
        //}

    }
}

@Composable
fun affichageTousFilms(pad: PaddingValues,backStack : SnapshotStateList<Any>,onGameClick:(Long)->Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(pad)
            .background(Color(android.graphics.Color.parseColor("#FFFFFF")))
    ) {
        items(games) { game ->
            affichageUnFilm(game.id, game.name, covers.find { it.id == game.cover }?.url,game.genres, pad,backStack, {onGameClick(game.id)})
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun ecranSecondaire(identifiant : Long,pad: PaddingValues,backStack: SnapshotStateList<Any>){
    Row(
        modifier = Modifier
            .padding(pad)
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 25.dp))
            .background(Color.LightGray)

    ){
        Text(identifiant.toString(),fontSize=24.sp)
    }
}



