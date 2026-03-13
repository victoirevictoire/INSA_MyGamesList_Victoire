package com.insa.mygameslist.data

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
val id_jeux_favoris = mutableStateListOf<Long>()

@Composable
fun ToggleIconButton(idJeu: Long) {
    val isToggled = id_jeux_favoris.contains(idJeu)

    IconButton(
        onClick = {
            if (isToggled) {
                id_jeux_favoris.remove(idJeu)
            } else {
                id_jeux_favoris.add(idJeu)
            }
        }
    ) {
        Icon(
            painter = if (isToggled) painterResource(com.insa.mygameslist.R.drawable.etoile_remplie)
            else painterResource(com.insa.mygameslist.R.drawable.etoile_vide),
            contentDescription = if (isToggled) "Favori" else "Non favori",
            tint=if(isToggled) Color(0xFFFFA500) else Color.Gray
        )
    }
}