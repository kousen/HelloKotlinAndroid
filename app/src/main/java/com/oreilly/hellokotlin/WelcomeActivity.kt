package com.oreilly.hellokotlin

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.oreilly.hellokotlin.astro.AstroRequest
import com.oreilly.hellokotlin.db.NamesDAO
import kotlinx.android.synthetic.main.activity_welcome.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class WelcomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra("user")

        welcome_text.text = String.format(
                getString(R.string.greeting),
                name)

        insertNameAndUpdateView(name)
    }

    private fun insertNameAndUpdateView(name: String) {
        doAsync {
            val dao = NamesDAO(applicationContext)
            if (!dao.exists(name)) {
                dao.insertName(name)
            }
            val names = dao.getAllNames()

            uiThread {
                names_list.adapter = ArrayAdapter<String>(
                        this@WelcomeActivity,
                        android.R.layout.simple_list_item_1,
                        names)
            }
        }
    }

    private fun deleteAllNames() {
        doAsync {
            NamesDAO(applicationContext).deleteAllNames()

            uiThread {
                names_list.adapter = ArrayAdapter<String>(
                        this@WelcomeActivity,
                        android.R.layout.simple_list_item_1,
                        arrayListOf())
                welcome_text.text = getString(R.string.hello_world)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.update_astronauts -> updateAstronauts()
            R.id.clear_database -> deleteAllNames()
            R.id.stackoverflow -> browse("http://stackoverflow.com")
            R.id.about -> toast("Hello Kotlin v1.0")
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun updateAstronauts() {
        doAsync {
            val result = AstroRequest().execute()
            val astronauts = result.people.map { "${it.name} on the ${it.craft}" }
            uiThread {
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
}
