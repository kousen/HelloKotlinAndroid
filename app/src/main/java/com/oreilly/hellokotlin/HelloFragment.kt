package com.oreilly.hellokotlin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oreilly.hellokotlin.databinding.HelloFragmentBinding

class HelloFragment : Fragment() {
    private lateinit var binding: HelloFragmentBinding

    private val viewModel: HelloViewModel by lazy {
        ViewModelProvider(this).get(HelloViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = HelloFragmentBinding.inflate(inflater)
        binding.helloButton.setOnClickListener(this::sayHello)
        return binding.root
    }

    private fun sayHello(view: View?) {
        val name = binding.editText.text.toString()
        binding.textView.text = "Hello, $name!"
    }

}