package com.oreilly.hellokotlin.astro

import kotlinx.serialization.Serializable

@Serializable
data class AstroResult(
    val message: String,
    val number: Int,
    val people: List<Assignment>
)

@Serializable
data class Assignment(
    val name: String,
    val craft: String,
)