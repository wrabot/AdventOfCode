package aoc2020

import forEachInput
import tools.log

object Day16 {
    fun solve() = forEachInput(2020, 16, 2) { lines ->
        val rules = lines.takeWhile { it.isNotBlank() }.map { it.split(": ") }.map { (name, rule) ->
            name to rule.split(" or ").map { range -> range.split("-").map { it.toInt() } }
                .map { (first, last) -> first..last }
        }.toMap()
        val myTicket = lines[2 + rules.size].split(",").map { it.toInt() }.log()
        val otherTickets = lines.takeLastWhile { it.isNotBlank() }.drop(1)
            .map { line -> line.split(",").map { it.toInt() } }

        log("part 1: ")
        val validRules = rules.values.flatten()
        otherTickets.flatten().filter { value -> validRules.none { value in it } }.sum().log()

        log("part 2: ")
        val validTickets = otherTickets.filter { ticket -> ticket.none { value -> validRules.none { value in it } } }
        val othersValues = myTicket.indices.map { index -> validTickets.map { it[index] } }
        val validIndices = rules.map { rule ->
            rule.key to othersValues.indices.filter { index -> othersValues[index].all { value -> rule.value.any { value in it } } }.toMutableList()
        }.toMap()
        while (true) {
            val singles = validIndices.filterValues { it.size == 1 }
            if (singles.size == validIndices.size) break
            val singlesValues = singles.values.flatten()
            validIndices.forEach { if (it.value.size > 1) it.value.removeAll(singlesValues) }
        }
        validIndices.filter { it.key.startsWith("departure") }.log().map { myTicket[it.value.first()].toLong() }.log().reduce { acc, s -> acc * s }.log()
    }
}
