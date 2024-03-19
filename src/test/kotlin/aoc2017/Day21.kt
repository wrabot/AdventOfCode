package aoc2017

import Day

class Day21(test: Int? = null) : Day(test) {
    override fun solvePart1() = solve(5)
    override fun solvePart2() = solve(18)

    private fun solve(iterations: Int): Int {
        var board = start
        repeat(iterations) { board = board.next() }
        return board.sumOf { it.count { it == '#' } }
    }

    // next board  
    private fun List<String>.next(): List<String> {
        val n = if (first().length % 2 == 0) 2 else 3
        val r = rules[n]!!
        return transform(n) { r[it]!! }
    }

    // transform board by transforming n x n blocs. warning: transformed block is transposed  
    private fun List<String>.transform(n: Int, transform: (List<String>) -> List<String>) = chunked(n) {
        it.transpose().chunked(n, transform).flatten().transpose()
    }.flatten()

    val start = """
            .#.
            ..#
            ###
        """.trimIndent().lines()

    // compute rules n=2/3 and add all rotation/split. value is transposed because transform transpose it
    private val rules = lines.map { it.split(" => ").run { first().split("/") to last().split("/") } }
        .groupBy { it.first.size }.mapValues { (_, rules) ->
            rules.flatMap { (k, v) ->
                val value = v.transpose()
                listOf(k, k.transpose()).flatMap { listOf(it, it.flipH()) }.flatMap { listOf(it, it.flipV()) }
                    .map { it to value }
            }.toMap()
        }

    private fun List<String>.flipV() = reversed()
    private fun List<String>.flipH() = map(String::reversed)
    private fun List<String>.transpose() = first().indices.map { c -> map { it[c] }.joinToString("") }
}
