package aoc2015

import Day

class Day2 : Day(2015, 2) {
    private val boxes = lines.map { box -> box.split("x").map { it.toInt() } }

    override fun solvePart1() =
        boxes.sumOf { (l, w, h) -> listOf(l * w, w * h, h * l).run { 2 * sum() + minOrNull()!! } }

    override fun solvePart2() =
        boxes.sumOf { it.sorted().take(2).sum() * 2 + it.reduce { acc, i -> acc * i } }
}
