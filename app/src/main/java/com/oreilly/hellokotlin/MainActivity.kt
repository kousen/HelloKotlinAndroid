package com.oreilly.hellokotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.oreilly.hellokotlin.databinding.ActivityMainBinding
import splitties.activities.start

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.helloButton.setOnClickListener(this::sayHello)
    }

    fun sayHello(v: View?) {
        val name = binding.editText.text.toString()
        start<WelcomeActivity> {
            putExtra("user", name)
        }
    }
}
