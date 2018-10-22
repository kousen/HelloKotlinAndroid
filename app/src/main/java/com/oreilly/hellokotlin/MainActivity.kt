package com.oreilly.hellokotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hello_button.setOnClickListener { v -> sayHello(v) }
    }

    fun sayHello(v: View?) {
        val name = edit_text.text.toString()

        startActivity<WelcomeActivity>("user" to name)
    }
}
