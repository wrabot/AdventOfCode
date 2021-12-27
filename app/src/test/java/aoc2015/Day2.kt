package aoc2015

import forEachInput
import tools.log

object Day2 {
    fun solve() = forEachInput(2015, 2, 1) { lines ->
        val boxes = lines.map { box -> box.split("x").map { it.toInt() } }

        log("part 1: ")
        boxes.map { (l, w, h) -> listOf(l * w, w * h, h * l).run { 2 * sum() + minOrNull()!! } }.sum().log()

        log("part 2: ")
        boxes.map { it.sorted().take(2).sum() * 2 + it.reduce { acc, i -> acc * i } }.sum().log()
    }
}
