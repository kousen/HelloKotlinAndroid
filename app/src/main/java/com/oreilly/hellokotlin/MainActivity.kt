package com.oreilly.hellokotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.oreilly.hellokotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
