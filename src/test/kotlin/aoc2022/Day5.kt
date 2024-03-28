package aoc2022

import Day
import tools.toWords

class Day5(test: Int? = null) : Day(test) {
    override fun solvePart1() = crates.map { it.toMutableList() }.apply {
        moves.forEach { move ->
            repeat(move.quantity) {
                this[move.destination].add(this[move.source].removeLast())
            }
        }
    }.joinToString("") { it.last().toString() }

    override fun solvePart2() = crates.map { it.toMutableList() }.apply {
        crates.map { it.toMutableList() }
        moves.forEach { move ->
            this[move.destination].addAll(this[move.source].takeLast(move.quantity))
            repeat(move.quantity) {
                this[move.source].removeLast()
            }
        }
    }.joinToString("") { it.last().toString() }

    private val crates = input.split("\n\n")[0].lines().let { lines ->
        val size = lines.last().length / 4 + 1
        Array(size) { mutableListOf<Char>() }.apply {
            lines.dropLast(1).reversed().map { line ->
                (0 until size).forEach {
                    val content = line.getOrNull(it * 4 + 1) ?: ' '
                    if (content != ' ') this[it].add(content)
                }
            }
        }.map { it.toList() }
    }

    private val moves = input.split("\n\n")[1].lines().map { line ->
        val (quantity, source, destination) = line.toWords().mapNotNull { it.toIntOrNull() }.toList()
        Move(quantity, source - 1, destination - 1)
    }

    private data class Move(val quantity: Int, val source: Int, val destination: Int)
}
