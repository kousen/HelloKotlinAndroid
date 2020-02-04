package com.oreilly.hellokotlin.astro

import com.google.gson.Gson
import java.net.URL

class AstroRequest {
    fun execute() : AstroResult =
        Gson().fromJson(
                URL("http://api.open-notify.org/astros.json").readText(),
                AstroResult::class.java)
}