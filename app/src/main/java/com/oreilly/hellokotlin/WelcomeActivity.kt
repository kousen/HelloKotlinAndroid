package com.oreilly.hellokotlin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.oreilly.hellokotlin.astro.AstroApi
import com.oreilly.hellokotlin.databinding.ActivityWelcomeBinding
import com.oreilly.hellokotlin.db.UserDatabase
import com.oreilly.hellokotlin.db.User
import com.oreilly.hellokotlin.db.UserDAO
import com.oreilly.hellokotlin.db.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WelcomeActivity : AppCompatActivity() {

    private lateinit var userDao: UserDAO
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra("user") ?: "World"

        binding.welcomeText.text = String.format(
                getString(R.string.greeting),
                name)

        userDao = UserDatabase.getInstance(this.applicationContext).userDao
        insertUserAndUpdateView(name)
    }

    private fun insertUserAndUpdateView(name: String) {
        lifecycleScope.launch {
            val names = withContext(Dispatchers.IO) {
                val userRepository = UserRepository(userDao)
                userRepository.insertUser(name)
                userRepository.allUsers.map(User::name)
            }
            binding.namesList.adapter = ArrayAdapter(
                    this@WelcomeActivity,
                    android.R.layout.simple_list_item_1,
                    names)
        }
    }

    private fun deleteAllUsers() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                userDao.deleteAll()
            }
            binding.namesList.adapter = ArrayAdapter<String>(
                    this@WelcomeActivity,
                    android.R.layout.simple_list_item_1,
                    arrayListOf())
            binding.welcomeText.text = getString(R.string.hello_world)
        }
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
            R.id.about -> Toast.makeText(this, "Hello Kotlin v1.0",
                    Toast.LENGTH_SHORT).show()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun goToPage(site: String = "http://stackoverflow.com") =
            startActivity(Intent(Intent.ACTION_VIEW, site.toUri()))

    private fun getAstronauts() {
        lifecycleScope.launch {
            val result = AstroApi.retrofitService.getAstroResult()
            val astronauts = result.people.map { "${it.name} on the ${it.craft}" }
            binding.numPeopleText.text = String.format(
                    getString(R.string.num_in_space),
                    result.number)
            binding.astronautNamesList.adapter = ArrayAdapter(
                    this@WelcomeActivity,
                    android.R.layout.simple_list_item_1,
                    astronauts)
        }
    }
}
