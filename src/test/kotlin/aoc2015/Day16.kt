package aoc2015

import Day

@Suppress("SpellCheckingInspection")
class Day16(test: Int? = null) : Day(test) {
    override fun solvePart1() = aunts.single { aunt -> aunt.properties.all { it.value == clues[it.key] } }.name

    override fun solvePart2() = aunts.single { aunt ->
        aunt.properties.all {
            when (it.key) {
                "cats", "trees" -> it.value > clues[it.key]!!
                "pomeranians", "goldfish" -> it.value < clues[it.key]!!
                else -> it.value == clues[it.key]
            }
        }
    }.name

    data class Aunt(val name: String, val properties: Map<String, Int>)

    private val aunts = lines.map { line ->
        val (name, properties) = line.split(": ", limit = 2)
        Aunt(name, properties.split(", ").associate {
            val (property, value) = it.split(": ")
            property to value.toInt()
        })
    }

    private val clues = mapOf(
        "children" to 3,
        "cats" to 7,
        "samoyeds" to 2,
        "pomeranians" to 3,
        "akitas" to 0,
        "vizslas" to 0,
        "goldfish" to 5,
        "trees" to 3,
        "cars" to 2,
        "perfumes" to 1
    )
}
