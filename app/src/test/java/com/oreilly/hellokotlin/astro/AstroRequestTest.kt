package com.oreilly.hellokotlin.astro

import org.junit.Assert.*
import org.junit.Test

class AstroRequestTest {

    @Test
    fun execute() {
        val result = AstroRequest().execute()
        println(result)

        assertEquals("success", result.message)
        assertTrue(result.number.toInt() >= 0)
        assertEquals(result.number.toInt(), result.people.size)
    }
}