package aoc2017

import Day
import java.util.*

class Day16(test: Int? = null) : Day(test) {
    override fun solvePart1(): String {
        val programs = init.toMutableList()
        dance(programs)
        return programs.joinToString("") { it.toString() }
    }

    override fun solvePart2(): String {
        val cache = mutableMapOf<String, Int>()
        val programs = init.toMutableList()
        var index = 1
        var previous: Int?
        while (true) {
            dance(programs)
            val key = programs.joinToString("") { it.toString() }
            previous = cache[key]
            if (previous != null) break
            cache[key] = index
            index++
        }
        val value = (1_000_000_000 - previous!!) % (index - previous) + previous
        return cache.firstNotNullOf { if (it.value == value) it.key else null }
    }

    private fun dance(programs: MutableList<Char>) {
        moves.forEach { move ->
            when {
                move.startsWith("s") -> Collections.rotate(programs, move.drop(1).toInt())
                move.startsWith("x") -> {
                    val (i, j) = move.drop(1).split("/").map { it.toInt() }
                    val a = programs[i]
                    val b = programs[j]
                    programs[j] = a
                    programs[i] = b
                }

                move.startsWith("p") -> {
                    val (a, b) = move.drop(1).split("/").map { it.first() }
                    val i = programs.indexOf(a)
                    val j = programs.indexOf(b)
                    programs[j] = a
                    programs[i] = b
                }
            }
        }
    }

    val init = "abcdefghijklmnop"
    val moves = input.split(",")
}
