package com.insa.mygameslist.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.insa.mygameslist.R
import okhttp3.internal.platform.Platform

//Objet chargé de charger et stocker les données IGDB nécessaires à l'application.
//Ici, il ne gère que la liste des couvertures (covers).
object IGDB {

    //Liste des couvertures chargées depuis le fichier JSON.
    // 'lateinit' signifie qu'elle sera initialisée plus tard, dans load().
    lateinit var covers: List<Cover>
    lateinit var games : List<Game>
    lateinit var genres : List<Genre>
    lateinit var platform_logos : List<Platform_logo>
    lateinit var platforms : List<Platform>

    //Charge les données depuis le fichier JSON situé dans /res/raw/covers.json.
    // Cette fonction doit être appelée une fois au démarrage de l'application.
    fun load(context: Context) {
        // Conversion du JSON en liste d'objets Cover grâce à Gson
        val coversFromJson: List<Cover> = Gson().fromJson(
            context.resources.openRawResource(R.raw.covers).bufferedReader(),
            object : TypeToken<List<Cover>>() {}.type
        )

        val gamesFromJson: List<Game> = Gson().fromJson(
            context.resources.openRawResource(R.raw.games).bufferedReader(),
            object : TypeToken<List<Game>>() {}.type
        )

        val genresFromJson: List<Genre> = Gson().fromJson(
            context.resources.openRawResource(R.raw.genres).bufferedReader(),
            object : TypeToken<List<Genre>>() {}.type
        )

        val platformlogosFromJson: List<Platform_logo> = Gson().fromJson(
            context.resources.openRawResource(R.raw.platform_logos).bufferedReader(),
            object : TypeToken<List<Platform_logo>>() {}.type
        )

        val platformsFromJson: List<Platform> = Gson().fromJson(
            context.resources.openRawResource(R.raw.platforms).bufferedReader(),
            object : TypeToken<List<Platform>>() {}.type
        )


        covers = coversFromJson
        games=gamesFromJson
        genres=genresFromJson
        platform_logos=platformlogosFromJson
        platforms=platformsFromJson


    }
}

//Représente une couverture de jeu avec un identifiant et une URL d'image.
data class Cover(val id: Long, val url: String)

data class Game(val id:Long, val cover : Long, val first_release_data : Long, val genres : List<Long>, val name : String, val platforms : List<Long>, val summary : String, val total_rating:Double)

data class Genre(val id:Long, val name : String)

data class Platform_logo (val id:Long, val url : String)

data class Platform (val id:Long, val name : String, val platform_logos : Long)

