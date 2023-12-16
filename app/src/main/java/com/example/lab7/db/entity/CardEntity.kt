package com.example.lab7.db.entity

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class CardEntity(

    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val question: String,
    val example: String,
    val answer: String,
    val translation: String,
    val image: Bitmap? = null
)

fun Card.toDb(): CardEntity =
    CardEntity(id, question, example, answer, translation, image)