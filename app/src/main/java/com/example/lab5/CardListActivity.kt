package com.example.lab5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab5.databinding.ActivityMainBinding

class CardListActivity : AppCompatActivity() { //AppCompatActivity() - наследование от активности
    lateinit var binding: ActivityMainBinding //тип ActivityMainBinding - связь с разметкой xml (LAYOUT)

    override fun onCreate(savedInstanceState: Bundle?) { //onCreate - родительский метод, параметр - для сохранения состояния
        super.onCreate(savedInstanceState) //Передача работы родителю
        binding = ActivityMainBinding.inflate(layoutInflater) //"Раздутие" из xml во view
        setContentView(binding.root) //Установка того, что будет видно в этом активити
    }
}

