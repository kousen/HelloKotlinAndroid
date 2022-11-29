package com.oreilly.hellokotlin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.oreilly.hellokotlin.databinding.ActivityWelcomeBinding
import com.oreilly.hellokotlin.db.User
import com.oreilly.hellokotlin.db.UserDatabase
import com.oreilly.hellokotlin.db.UserRepository
import com.oreilly.hellokotlin.ui.WelcomeViewModel
import com.oreilly.hellokotlin.ui.WelcomeViewModelFactory

class WelcomeActivity : AppCompatActivity() {

    private val viewModel: WelcomeViewModel by viewModels {
        WelcomeViewModelFactory(
            UserRepository(UserDatabase.getInstance(applicationContext).userDao)
        )
    }

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.allUsers.observe(this) { users ->
            users.let {
                binding.namesList.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    users.map(User::name)
                )
            }
        }

        // ?. == Safe call --> only do the RHS if the reference is not null
        // let is a scope function that executes a block of code on the current object
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let { name ->
            binding.welcomeText.text = String.format(getString(R.string.greeting), name)
            viewModel.insert(name)
        }
    }

    private fun deleteAllUsers() {
        viewModel.deleteAll()
        binding.welcomeText.text = getString(R.string.hello_world)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.update_astronauts -> getAstronauts()
            R.id.clear_database -> deleteAllUsers()
            R.id.stackoverflow -> goToPage()
            R.id.about -> Toast.makeText(
                this,
                "Hello Kotlin v2.0",
                Toast.LENGTH_SHORT
            ).show()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun goToPage(site: String = "http://stackoverflow.com") =
        startActivity(Intent(Intent.ACTION_VIEW, site.toUri()))

    private fun getAstronauts() {
        viewModel.astroResult.observe(this) { result ->
            val astronauts = result.people.map { "${it.name} on the ${it.craft}" }
            binding.numPeopleText.text = String.format(
                getString(R.string.num_in_space),
                result.number
            )
            binding.astronautNamesList.adapter = ArrayAdapter(
                this@WelcomeActivity,
                android.R.layout.simple_list_item_1,
                astronauts
            )
        }
    }
}
