package aoc2020

import Day
import java.util.*

class Day23 : Day() {
    override fun solvePart1(): Any {
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
        return cups.drop(1).joinToString("")
    }

    override fun solvePart2(): Any {
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
            val destinationCup =
                if (destinationValue <= firstCups.size) cups.find { it.value == destinationValue }!! else cups[destinationValue - 1]

            current.next = third.next // remove first to third
            third.next =
                destinationCup.next.apply { destinationCup.next = first } // insert first to third after destination
            current = current.next // next
        }

        return cups.find { it.value == 1 }!!.run { next.value * next.next.value }
    }

    private data class Cup(val value: Int) {
        lateinit var next: Cup
    }

    private val firstCups = lines[1].map { it.toString().toInt() }
}
