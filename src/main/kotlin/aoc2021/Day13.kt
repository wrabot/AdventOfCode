package aoc2021

import tools.Day

class Day13(test: Int? = null) : Day(2021, 13, test) {
    override fun getPart1() = folds.take(1).foldDots(dots).count()

    override fun getPart2(): Any {
        val code = folds.foldDots(dots)
        val width = code.maxOf { it[0] } + 1
        val height = code.maxOf { it[1] } + 1
        return List(width * height) { if (listOf(it % width, it / width) in code) '#' else ' ' }
            .chunked(width) { it.joinToString("") }.joinToString("\n")
    }

    private val dots = lines.takeWhile { it != "" }.map { line -> line.split(",").map { it.toInt() } }
    private val folds = lines.takeLastWhile { it != "" }.map {
        it.split("=").let { (a, b) -> a.removePrefix("fold along ") to b.toInt() }
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
