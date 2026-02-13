package com.insa.mygameslist.data

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.insa.mygameslist.MonBeauScaffoldRoiDsScaffolds
import com.insa.mygameslist.ScaffoldSecondaire
import kotlinx.serialization.Serializable


// Define keys that will identify content
@Serializable
data object GameList
@Serializable// destination without arguments
data class GameExample(val Gameid: Long) // destination with arguments

@Composable
fun MyApp() {
    val backStack = remember { mutableStateListOf<Any>(GameList) }
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is GameList -> NavEntry(key) { MonBeauScaffoldRoiDsScaffolds(backStack) }
                is GameExample -> NavEntry(key) {
                    ScaffoldSecondaire(key.Gameid,backStack)
                }
                else -> NavEntry(Unit) { Text("Unknown") }
            }
        }
    )
}