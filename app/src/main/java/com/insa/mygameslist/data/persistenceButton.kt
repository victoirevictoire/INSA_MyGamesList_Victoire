package com.insa.mygameslist.data

import android.content.Context
import androidx.room.*
import androidx.room.Room


@Entity
data class JeuBoutonPersistant(
    @PrimaryKey val idJeu: Long,
    val estEnFavori : Boolean
)

@Database(entities = [JeuBoutonPersistant::class], version = 1)
abstract class JeuxDatabase : RoomDatabase() {

    abstract fun jeuxDao(): JeuxDao

    companion object {
        @Volatile // Stable : Compose sait si la valeur a été modifiée
        private var INSTANCE: JeuxDatabase? = null

        fun getDatabase(context: Context): JeuxDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    JeuxDatabase::class.java,
                    "jeux_db" // nom du fichier de bdd crée et stocké
                ).build().also { INSTANCE = it }
            }
        }
    }
}
