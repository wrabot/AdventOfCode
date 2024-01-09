package aoc2019

import Day

class Day8(test: Int? = null) : Day(test) {
    override fun solvePart1() = input.chunked(25 * 6).minBy { image -> image.count { it == '0' } }.let { image ->
        image.count { it == '1' } * image.count { it == '2' }
    }

    override fun solvePart2() = input.toList().chunked(25 * 6).reduce { image, layer ->
        image.zip(layer).map { if (it.first == '2') it.second else it.first }
    }.joinToString("") { if (it == '0') " " else "#" }.chunked(25).joinToString("") {"\n$it" }
}
