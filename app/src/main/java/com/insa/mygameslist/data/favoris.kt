package com.insa.mygameslist.data

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val id_jeux_favoris = mutableStateListOf<Long>() // on garde les identifiants des jeux en favoris

@Composable
fun ToggleIconButton(idJeu: Long, dao: JeuxDao) {

    val estFavori by dao.isFavori(idJeu).collectAsState(initial = false)
    val scope = rememberCoroutineScope()

    IconButton(
        onClick = {
            scope.launch {
                if (estFavori) {
                    dao.delete(JeuBoutonPersistant(idJeu, true))
                } else {
                    dao.insert(JeuBoutonPersistant(idJeu, true))
                }
            }
        }
    ) {
        Icon(
            painter = painterResource(
                if (estFavori) com.insa.mygameslist.R.drawable.etoile_remplie
                else com.insa.mygameslist.R.drawable.etoile_vide
            ),
            contentDescription = "icone étoile",
            tint = if (estFavori) Color(0xFFFFA500) else Color.Gray
        )
    }
}
