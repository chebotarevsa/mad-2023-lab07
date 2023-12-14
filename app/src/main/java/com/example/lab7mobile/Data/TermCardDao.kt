package com.example.lab7mobile.Data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TermCardDao {
    @Insert
    suspend fun put(vararg cards: TermCard)

    @Insert
    suspend fun put(card: TermCard)

    @Update
    suspend fun update(card: TermCard): Int

    @Delete
    suspend fun delete(card: TermCard): Int

    @Query("SELECT * FROM term_cards")
    fun getAll(): LiveData<List<TermCard>>

    @Query("SELECT * FROM term_cards WHERE id=:id LIMIT 1")
    fun getId(id: Int): LiveData<TermCard>
}