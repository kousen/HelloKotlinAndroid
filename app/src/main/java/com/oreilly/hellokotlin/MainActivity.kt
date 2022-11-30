package com.oreilly.hellokotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.oreilly.hellokotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.helloButton.setOnClickListener(this::sayHello)
    }

    @Suppress("UNUSED_PARAMETER")
    fun sayHello(v: View?) {
        Intent(this, WelcomeActivity::class.java).apply {
            putExtra(Intent.EXTRA_TEXT, binding.editText.text.toString())
            startActivity(this)
        }
    }
}
