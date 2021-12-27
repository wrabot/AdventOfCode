package aoc2020

import forEachInput
import repeat
import tools.log

object Day25 {
    fun solve() = forEachInput(2020, 25, 2) { lines ->
        log("part 1: ")
        val card = lines[0].toInt()
        val door = lines[1].toInt()

        var count = 0
        var value = 1
        while (true) {
            value = next(value, 7)
            count++
            when (value) {
                card -> repeat(count, 1) { next(it, door) }.log()
                door -> repeat(count, 1) { next(it, card) }.log()
                else -> continue
            }
            break
        }
    }

    private fun next(value: Int, subject: Int) = ((value.toLong() * subject) % 20201227).toInt()
}
