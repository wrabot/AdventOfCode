package aoc2020

import tools.Day

class Day16(test: Int? = null) : Day(2020, 16, test) {
    override fun solvePart1() = otherTickets.flatten().filter { value -> validRules.none { value in it } }.sum()

    override fun solvePart2(): Any {
        val validTickets = otherTickets.filter { ticket -> ticket.none { value -> validRules.none { value in it } } }
        val othersValues = myTicket.indices.map { index -> validTickets.map { it[index] } }
        val validIndices = rules.map { rule ->
            rule.key to othersValues.indices.filter { index ->
                othersValues[index].all { value -> rule.value.any { value in it } }
            }.toMutableList()
        }.toMap()
        while (true) {
            val singles = validIndices.filterValues { it.size == 1 }
            if (singles.size == validIndices.size) break
            val singlesValues = singles.values.flatten()
            validIndices.forEach { if (it.value.size > 1) it.value.removeAll(singlesValues) }
        }
        return validIndices.filter { it.key.startsWith("departure") }
            .map { myTicket[it.value.first()].toLong() }.reduce { acc, s -> acc * s }
    }

    private val rules = lines.takeWhile { it.isNotBlank() }.map { it.split(": ") }
        .associate { (name, rule) ->
            name to rule.split(" or ").map { range ->
                range.split("-").let { (first, last) -> first.toInt()..last.toInt() }
            }
        }
    private val myTicket = lines[2 + rules.size].split(",").map { it.toInt() }
    private val otherTickets = lines.takeLastWhile { it.isNotBlank() }.drop(1)
        .map { line -> line.split(",").map { it.toInt() } }
    private val validRules = rules.values.flatten()
}
