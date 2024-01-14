package aoc2023

import Day
import tools.match
import tools.toWords

class Day4(test: Int? = null) : Day(test) {
    override fun solvePart1() = cards.sumOf { card ->
        card.myWinning.let { if (it <= 1) it else 1 shl (it - 1) }
    }

    override fun solvePart2(): Int {
        cards.forEach { card ->
            cards.subList(card.id, card.id + card.myWinning).forEach {
                it.count += card.count
            }
        }
        return cards.sumOf { it.count }
    }

    private val cards = Regex("Card\\s+(\\d+):(.*)\\|(.*)").run {
        lines.map { line ->
            line.match(this)!!.let { Card(it[0].toInt(), it[1].toIntSet(), it[2].toIntSet()) }
        }
    }

    data class Card(val id: Int, val winning: Set<Int>, val mine: Set<Int>) {
        val myWinning = winning.intersect(mine).count()
        var count = 1
    }

    private fun String.toIntSet() = toWords().map { it.toInt() }.toSet()
}
