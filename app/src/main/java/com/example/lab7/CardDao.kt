package com.example.lab7

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CardDao {
    @Insert
    fun insert(vararg cards: Card)

    @Insert
    fun insert(card: Card)

    @Query("SELECT * FROM card")
    fun findAll(): LiveData<List<Card>>

    @Query("SELECT * FROM card WHERE translation=:translation LIMIT 1")
    fun findByTranslation(translation: String): LiveData<Card>

    @Query("SELECT * FROM card WHERE id=:id LIMIT 1")
    fun findById(id: Int): LiveData<Card>

    @Update
    fun update(card: Card): Int

    @Delete
    fun delete(card: Card): Int
}