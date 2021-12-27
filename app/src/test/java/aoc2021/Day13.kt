package aoc2021

import forEachInput
import tools.log

object Day13 {
    fun solve() = forEachInput(2021, 13, 1, 2) { lines ->
        val dots = lines.takeWhile { it != "" }.map { line -> line.split(",").map { it.toInt() } }
        val folds = lines.takeLastWhile { it != "" }.map {
            it.split("=").let { (a, b) -> a.removePrefix("fold along ") to b.toInt() }
        }

        log("part 1: ")
        folds.take(1).foldDots(dots).count().log()

        log("part 2: ")
        val code = folds.foldDots(dots)
        val width = code.maxOf { it[0] } + 1
        val height = code.maxOf { it[1] } + 1
        List(width * height) {
            if (listOf(it % width, it / width) in code) '#' else ' '
        }.log(width)
    }

    private fun List<Pair<String, Int>>.foldDots(dots: List<List<Int>>) = fold(dots) { acc, f ->
        val index = if (f.first == "x") 0 else 1
        val value = f.second
        acc.mapNotNull { dot ->
            when (dot[index].compareTo(value)) {
                -1 -> dot[index]
                1 -> 2 * value - dot[index]
                else -> null
            }?.let { if (index == 0) listOf(it, dot[1]) else listOf(dot[0], it) }
        }.distinct()
    }
}
