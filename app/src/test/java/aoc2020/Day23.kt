package aoc2020

import forEachInput
import tools.log
import java.util.*

object Day23 {
    fun solve() = forEachInput(2020, 23, 1) { lines ->
        val cups = lines[1].map { it.toString().toInt() }

        log("part 1: ")
        day23part1(cups)

        log("part 2: ")
        day23part2(cups)
    }

    data class Cup(val value: Int) {
        lateinit var next: Cup
    }

    private fun day23part1(firstCups: List<Int>) {
        val cups = firstCups.toMutableList()
        repeat(100) {
            val triple = listOf(cups.removeAt(1), cups.removeAt(1), cups.removeAt(1))
            var destination = cups[0]
            do {
                if (destination == 1) destination = 9 else destination--
            } while (destination in triple)
            cups.addAll(cups.indexOf(destination) + 1, triple)
            cups.add(cups.removeFirst())
        }
        Collections.rotate(cups, -cups.indexOf(1))
        cups.drop(1).joinToString("").log()
    }

    private fun day23part2(firstCups: List<Int>) {
        val cups = List(1000000) { Cup(if (it < firstCups.size) firstCups[it] else it + 1) }
        cups.forEachIndexed { index, cup -> cup.next = cups[(index + 1) % cups.size] }
        var current = cups.first()

        repeat(10000000) {
            val first = current.next
            val second = first.next
            val third = second.next

            var destinationValue = current.value
            do {
                if (destinationValue == 1) destinationValue = cups.size else destinationValue--
            } while (destinationValue == first.value || destinationValue == second.value || destinationValue == third.value)
            val destinationCup = if (destinationValue <= firstCups.size) cups.find { it.value == destinationValue }!! else cups[destinationValue - 1]

            current.next = third.next // remove first to third
            third.next = destinationCup.next.apply { destinationCup.next = first } // insert first to third after destination
            current = current.next // next
        }

        cups.find { it.value == 1 }!!.run { next.value * next.next.value }.log()
    }
}
