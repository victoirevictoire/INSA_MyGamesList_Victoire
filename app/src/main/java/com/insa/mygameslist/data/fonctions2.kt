package com.insa.mygameslist.data
import android.graphics.Color.red
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

@Composable
fun affichageUnFilm(idFilm: Long, nomFilm: String?, URL: String?, Genres: List<Long>, pad: PaddingValues, backStack: SnapshotStateList<Any>, onClick: () -> Unit = {}, dao: JeuxDao):Unit{

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
            .background(color = Color.Green) //moche mais contrasté pour l'accessibilité
            .clickable(onClick=onClick)
            .semantics { role = Role.Button } // on précjse le rôle pour l'accessibilité

    ){
        Column{
            AsyncImage(
                model="https:"+ URL,
                contentDescription = "affichage du film $nomFilm",
                modifier = Modifier.size(70.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)){
            if (nomFilm != null) {
                Text(nomFilm,fontWeight = FontWeight.Bold,fontSize = 15.sp)
            }
            Text(afficherGenres,fontSize = 10.sp)
        }
        Column{
            ToggleIconButton(idFilm,dao)
            }
        }

    }

@Composable
fun affichageTousFilms(pad: PaddingValues,backStack : SnapshotStateList<Any>,onGameClick:(Long)->Unit,dao: JeuxDao) {
    LazyColumn(
        modifier = Modifier
            .padding(pad)
            .background(Color(android.graphics.Color.parseColor("#FFFFFF")))
    ) {
        items(games) { game ->
            affichageUnFilm(game.id, game.name, covers.find { it.id == game.cover }?.url,game.genres, pad,backStack, {onGameClick(game.id)},dao)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun ecranSecondaire(identifiant: Long, pad: PaddingValues, backStack: SnapshotStateList<Any>,dao: JeuxDao) {

    val game1 = games.find { it.id == identifiant }

    Column( // Ligne du nom
        modifier = Modifier
            .padding(top = pad.calculateTopPadding())
            .fillMaxWidth()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = game1?.name ?: "",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            ToggleIconButton(game1?.id ?: 0,dao)
        }

        // NOM DU FILM


        // COVER DU JEU
        Spacer(modifier = Modifier.height(20.dp))
        AsyncImage(
            model = "https:" + covers.find { it.id == game1?.cover }?.url,
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp),

            contentDescription = "couverture du jeu $game1?.name ?:"
        )

        // GENRE DU JEU
        Spacer(modifier = Modifier.height(20.dp))
        val genres1 = game1?.genres
        var stringGenres = ""
        if (genres1 != null) {
            for (idGenre in genres1) {
                stringGenres += genres.find { it.id == idGenre }?.name
                stringGenres += ", "
            }
        }
        stringGenres=stringGenres.removeSuffix(", ")

        Text(
            stringGenres,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic
        )

        // LAZY ROW : LOGOS DES PLATEFORMES

        val platforms = mutableListOf<String>()
        val platforms_id1 = game1?.platforms
        val platforms_id2=mutableListOf<Long>()

        for(plat1 in (platforms_id1!!)){
            platforms_id2.add(IGDB.platforms.firstOrNull{it.id==plat1}?.platform_logo?:0)
        }
        for (plat2 in (platforms_id2)){
            platforms.add(IGDB.platform_logos.firstOrNull{it.id==plat2}?.url?:"")
        }

        LazyRow(
            modifier = Modifier.semantics { contentDescription = "Plateformes disponibles" },
            horizontalArrangement = Arrangement.spacedBy(4.dp)

        ) {
            items(platforms) { plat ->
                AsyncImage(
                    model = "https:" + plat,
                    contentDescription = "Image depuis URL",
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                )
            }
        }

        //DESCRIPTION DU JEU

        Spacer(modifier = Modifier.height(20.dp))
        FlowRow {
            game1?.summary?.let {
                Text(it, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }
        }
    }
}


