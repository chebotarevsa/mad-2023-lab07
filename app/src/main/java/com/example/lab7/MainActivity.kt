package com.example.lab7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab7.databinding.ActivityListCardBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityListCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}


