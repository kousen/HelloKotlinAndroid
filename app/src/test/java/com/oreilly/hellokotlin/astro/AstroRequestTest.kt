package com.oreilly.hellokotlin.astro

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.net.URL

class AstroRequestTest {

    @Test
    fun `Access astronaut data`() {
        println(URL("http://api.open-notify.org/astros.json").readText())
    }

    @Test
    fun execute() {
        val result = AstroRequest().execute()
        println(result)

        assertEquals("success", result.message)
        assertTrue(result.number >= 0)
        assertEquals(result.number, result.people.size)
    }

    @Test
    fun `Use retrofit to get astro data`() = runBlocking {
        AstroApi.retrofitService.getAstroResult().run {
            println("There are $number people in space")
            for ((name,craft) in people) {
                println("$name aboard $craft")
            }
            assertTrue(number >= 0)
            assertEquals(number, people.size)
        }
    }
}