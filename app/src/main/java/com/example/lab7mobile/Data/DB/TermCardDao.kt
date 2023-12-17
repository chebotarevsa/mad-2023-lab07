package com.example.lab7mobile.Data.DB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lab7mobile.Data.TermCard


@Dao
interface TermCardDao {
    @Insert
    fun insert(cards: List<TermCardDB>)

    @Insert
    fun insert(card: TermCardDB)

    @Update
    suspend fun update(card: TermCardDB): Int

    @Delete
    suspend fun delete(card: TermCardDB): Int

    @Query("SELECT * FROM term_cards")
    fun getAll(): LiveData<List<TermCardDB>>

    @Query("SELECT * FROM term_cards WHERE id=:id LIMIT 1")
    fun getId(id: String): LiveData<TermCardDB>
}



