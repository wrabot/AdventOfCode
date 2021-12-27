package aoc2021

import forEachInput
import tools.log

object Day2 {
    fun solve() = forEachInput(2021, 2, 1, 2) { lines ->
        val orders = lines.map {
            val (action, value) = it.split(" ")
            action to value.toInt()
        }

        var position = 0
        var depth = 0

        log("part 1: ")
        orders.forEach {
            when (it.first) {
                "forward" -> position += it.second
                "up" -> depth -= it.second
                "down" -> depth += it.second
            }
        }
        (position * depth).log()

        log("part 2: ")
        position = 0
        depth = 0
        var aim = 0
        orders.forEach {
            when (it.first) {
                "forward" -> {
                    position += it.second
                    depth += it.second * aim
                }
                "up" -> aim -= it.second
                "down" -> aim += it.second
            }
        }
        (position * depth).log()
    }
}
