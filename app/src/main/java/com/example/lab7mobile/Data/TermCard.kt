package com.example.lab7mobile.Data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "term_cards")
data class TermCard(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val question: String,
    val example: String,
    val answer: String,
    val translate: String,
    val image: Bitmap?
)