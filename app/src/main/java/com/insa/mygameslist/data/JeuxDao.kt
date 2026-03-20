package com.insa.mygameslist.data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JeuxDao { //interface jeux
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: JeuBoutonPersistant)

    @Delete
    suspend fun delete(item: JeuBoutonPersistant)

    @Query("SELECT * FROM jeuBoutonPersistant") // fonctionne à peu près comme du sql ??
    fun getAll(): Flow<List<JeuBoutonPersistant>>

    @Query("SELECT EXISTS(SELECT 1 FROM jeuBoutonPersistant WHERE idJeu = :id)")
    fun isFavori(id: Long): Flow<Boolean>
}


