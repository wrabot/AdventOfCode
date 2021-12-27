package aoc2020

import forEachInput
import tools.log

object Day9 {
    fun solve() = forEachInput(2020, 9, 1) { lines ->
        val numbers = lines.map { it.toLong() }

        log("part 1: ")
        var invalid = 0L
        find@ for (i in 25 until numbers.size) {
            for (first in 1..25) {
                for (second in (first + 1)..25) {
                    if (numbers[i] == numbers[i - first] + numbers[i - second]) continue@find
                }
            }
            invalid = numbers[i]
            break
        }
        invalid.log()

        log("part 2: ")
        sum@ for (i in numbers.indices) {
            var sum = 0L
            for (j in i until numbers.size) {
                sum += numbers[j]
                if (sum > invalid) continue@sum
                if (sum == invalid) {
                    val block = numbers.subList(i, j)
                    val weakness = block.minOrNull()!! + block.maxOrNull()!!
                    weakness.log()
                    break@sum
                }
            }
            break
        }
    }
}
