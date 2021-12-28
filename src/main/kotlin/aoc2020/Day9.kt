package aoc2020

import tools.Day

class Day9 : Day(2020, 9) {
    override fun getPart1(): Any {
        find@ for (i in 25 until numbers.size) {
            for (first in 1..25) {
                for (second in (first + 1)..25) {
                    if (numbers[i] == numbers[i - first] + numbers[i - second]) continue@find
                }
            }
            invalid = numbers[i]
            break
        }
        return invalid
    }

    override fun getPart2(): Any {
        sum@ for (i in numbers.indices) {
            var sum = 0L
            for (j in i until numbers.size) {
                sum += numbers[j]
                if (sum > invalid) continue@sum
                if (sum == invalid) {
                    val block = numbers.subList(i, j)
                    return block.minOrNull()!! + block.maxOrNull()!!
                }
            }
            break
        }
        error("not found")
    }

    private val numbers = lines.map { it.toLong() }
    private var invalid = 0L
}
