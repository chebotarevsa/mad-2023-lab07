package com.example.lab7mobile.Data

import android.graphics.Bitmap

data class TermCard(
    val id: String,
    val question: String,
    val example: String,
    val answer: String,
    val translate: String,
    val image: Bitmap?
)