package com.insa.mygameslist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.insa.mygameslist.data.GameExample
import com.insa.mygameslist.data.IGDB
import com.insa.mygameslist.data.IGDB.games
import com.insa.mygameslist.data.IGDB.genres
import com.insa.mygameslist.data.MyApp
import com.insa.mygameslist.data.affichageTousFilms
import com.insa.mygameslist.data.ecranSecondaire
import com.insa.mygameslist.ui.theme.MyGamesListTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IGDB.load(this)
        enableEdgeToEdge()
        setContent {
            MyGamesListTheme {
                MyApp()
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonBeauScaffoldRoiDsScaffolds(backStack:SnapshotStateList<Any>) {
    Scaffold(
        topBar = {
            // Barre supérieure de l'application
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color(android.graphics.Color.parseColor("#7D90D4")), // Couleur de fond de la top bar
                    titleContentColor = Color.Black, // Couleur du texte du titre
                ),
                title = { Text("My Games List") })  // Titre affiché dans la top bar
        },
        contentWindowInsets = WindowInsets.systemBars,
        modifier = Modifier
            .fillMaxSize() // Le Scaffold occupe tout l'écran
            .background(Color(android.graphics.Color.parseColor("#BFCAF2")))
    ) { innerPadding ->
        // Text("À remplir", modifier = Modifier.padding(innerPadding))
        //Test("ceci est un test !!", innerPadding)
        //affichageUnFilm(games.get(0).id,games.get(0).name, covers.find { it.id == games[0].cover }?.url,games.get(0).genres,innerPadding)
        affichageTousFilms(innerPadding,backStack,{gameId->backStack.add(GameExample(gameId))})
    }
}



@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldSecondaire(idGame : Long,backStack:SnapshotStateList<Any>) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color(android.graphics.Color.parseColor("#7D90D4")),
                    titleContentColor = Color.Black,
                ),
                navigationIcon = {
                    IconButton(onClick = {  backStack.removeLastOrNull() }) {
                        Icon(
                            painter = painterResource(R.drawable.icon),
                            contentDescription = stringResource(id=R.drawable.icon)

                        )
                    }
                },
                title = { games.find { it.id == idGame }?.name?.let { Text(it) } }
            )
        },

        contentWindowInsets = WindowInsets.systemBars,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor("#BFCAF2")))
    )
    { innerPadding ->
        ecranSecondaire(idGame,innerPadding,backStack)
    }
}