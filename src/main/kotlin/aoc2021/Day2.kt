package aoc2021

import Day

class Day2(test: Int? = null) : Day(test) {
    override fun solvePart1(): Any {
        var position = 0
        var depth = 0
        orders.forEach {
            when (it.first) {
                "forward" -> position += it.second
                "up" -> depth -= it.second
                "down" -> depth += it.second
            }
        }
        return position * depth
    }

    override fun solvePart2(): Any {
        var position = 0
        var depth = 0
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
        return position * depth
    }

    private val orders = lines.map {
        val (action, value) = it.split(" ")
        action to value.toInt()
    }
}
