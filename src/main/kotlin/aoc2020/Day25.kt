package aoc2020

import tools.Day

class Day25(test: Int? = null) : Day(2020, 25, test) {
    override fun getPart1(): Any {
        val card = lines[0].toInt()
        val door = lines[1].toInt()

        var count = 0
        var value = 1
        while (true) {
            value = next(value, 7)
            count++
            return when (value) {
                card -> (1..count).fold(1) { acc, _ -> next(acc, door) }
                door -> (1..count).fold(1) { acc, _ -> next(acc, card) }
                else -> continue
            }
        }
    }

    override fun getPart2() = "done!"

    private fun next(value: Int, subject: Int) = ((value.toLong() * subject) % 20201227).toInt()
}
