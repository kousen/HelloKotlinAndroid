package com.oreilly.hellokotlin

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.oreilly.hellokotlin.astro.AstroResult
import com.oreilly.hellokotlin.databinding.WelcomeFragmentBinding
import com.oreilly.hellokotlin.db.AppDatabase
import com.oreilly.hellokotlin.db.User
import com.oreilly.hellokotlin.db.UserDAO
import com.oreilly.hellokotlin.db.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class WelcomeFragment : Fragment() {

    private lateinit var userDao: UserDAO
    private lateinit var binding: WelcomeFragmentBinding

    private val viewModel: WelcomeViewModel by lazy {
        ViewModelProvider(this).get(WelcomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = WelcomeFragmentBinding.inflate(layoutInflater)

//        binding.welcomeText.text = String.format(
//                getString(R.string.greeting),
//                name)

        userDao = AppDatabase.getInstance(requireContext()).userDao()
//        insertUserAndUpdateView(name)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun insertUserAndUpdateView(name: String) {
        lifecycleScope.launch {
            val names = withContext(Dispatchers.IO) {
                val userRepository = UserRepository(userDao)
                userRepository.insertUser(name)
                userRepository.allUsers.map(User::name)
            }
            binding.namesList.adapter = ArrayAdapter(
                    requireContext(),
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
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    arrayListOf())
            binding.welcomeText.text = getString(R.string.hello_world)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.update_astronauts -> getAstronauts()
            R.id.clear_database -> deleteAllUsers()
            R.id.stackoverflow -> goToPage()
            R.id.about -> Toast.makeText(context, "Hello Kotlin v1.0",
                    Toast.LENGTH_SHORT).show()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun goToPage(site: String = "http://stackoverflow.com") =
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
            binding.numPeopleText.text = String.format(
                    getString(R.string.num_in_space),
                    result.number)
            binding.astronautNamesList.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    astronauts)
        }
    }

}