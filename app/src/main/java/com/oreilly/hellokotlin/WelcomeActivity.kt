package com.oreilly.hellokotlin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.oreilly.hellokotlin.astro.AstroResult
import com.oreilly.hellokotlin.db.AppDatabase
import com.oreilly.hellokotlin.db.User
import com.oreilly.hellokotlin.db.UserDAO
import com.oreilly.hellokotlin.db.UserRepository
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.toast.toast
import java.net.URL

class WelcomeActivity : AppCompatActivity() {

    private lateinit var userDao: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra("user") ?: "World"

        welcome_text.text = String.format(
                getString(R.string.greeting),
                name)

        userDao = AppDatabase.getInstance(this.applicationContext).userDao()
        insertUserAndUpdateView(name)
    }

    private fun insertUserAndUpdateView(name: String) {
        lifecycleScope.launch {
            val names = withContext(Dispatchers.IO) {
                val userRepository = UserRepository(userDao)
                userRepository.insertUser(name)
                userRepository.allUsers.map(User::name)
            }
            names_list.adapter = ArrayAdapter<String>(
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
            names_list.adapter = ArrayAdapter<String>(
                    this@WelcomeActivity,
                    android.R.layout.simple_list_item_1,
                    arrayListOf())
            welcome_text.text = getString(R.string.hello_world)
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
            R.id.stackoverflow -> goToPage("http://stackoverflow.com")
            R.id.about -> toast("Hello Kotlin v1.0")
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    fun goToPage(site: String) =
            startActivity(Intent(Intent.ACTION_VIEW, site.toUri()))

    private suspend fun downloadAstroData(): AstroResult =
            withContext(Dispatchers.IO) {
                Gson().fromJson(
                        URL("http://api.open-notify.org/astros.json").readText(),
                        AstroResult::class.java)
            }

    private fun getAstronauts() {
        lifecycleScope.launch {
            val result = downloadAstroData()
            val astronauts = result.people.map { "${it.name} on the ${it.craft}" }
            num_people_text.text = String.format(
                    getString(R.string.num_in_space),
                    result.number)
            astronaut_names_list.adapter = ArrayAdapter<String>(
                    this@WelcomeActivity,
                    android.R.layout.simple_list_item_1,
                    astronauts)
        }
    }
}
