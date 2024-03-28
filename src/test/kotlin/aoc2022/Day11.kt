package aoc2022

import Day

class Day11(test: Int? = null) : Day(test) {
    override fun solvePart1() = monkeys.map { it.copy() }.run {
        repeat(20) {
            forEach { monkey ->
                monkey.items.forEach {
                    monkey.inspectCount++
                    val level = monkey.operation(it) / 3
                    this[monkey.destination(level)].items.add(level)
                }
                monkey.items.clear()
            }
        }
        businessLevel()
    }

    override fun solvePart2() = monkeys.map { it.copy() }.run {
        val modulo = map { it.modulo }.reduce { acc, bigInteger -> acc * bigInteger }
        repeat(10000) {
            forEach { monkey ->
                monkey.items.forEach {
                    monkey.inspectCount++
                    val level = monkey.operation(it) % modulo
                    this[monkey.destination(level)].items.add(level)
                }
                monkey.items.clear()
            }
        }
        businessLevel()
    }

    private fun List<Monkey>.businessLevel() =
        map { it.inspectCount }.sortedDescending().take(2).reduce { acc, i -> acc * i }

    // for debug
    @Suppress("unused")
    private val testMonkeys = listOf(
        Monkey(mutableListOf(79, 98), { it * 19 }, 23, 2, 3),
        Monkey(mutableListOf(54, 65, 75, 74), { it + 6 }, 19, 2, 0),
        Monkey(mutableListOf(79, 60, 97), { it * it }, 13, 1, 3),
        Monkey(mutableListOf(74), { it + 3 }, 17, 0, 1),
    )

    private val inputMonkeys = listOf(
        Monkey(listOf(66, 59, 64, 51), { it * 3 }, 2, 1, 4),
        Monkey(listOf(67, 61), { it * 19 }, 7, 3, 5),
        Monkey(listOf(86, 93, 80, 70, 71, 81, 56), { it + 2 }, 11, 4, 0),
        Monkey(listOf(94), { it * it }, 19, 7, 6),
        Monkey(listOf(71, 92, 64), { it + 8 }, 3, 5, 1),
        Monkey(listOf(58, 81, 92, 75, 56), { it + 6 }, 5, 3, 6),
        Monkey(listOf(82, 98, 77, 94, 86, 81), { it + 7 }, 17, 7, 2),
        Monkey(listOf(54, 95, 70, 93, 88, 93, 63, 50), { it + 4 }, 13, 2, 0),
    )

    // to select input
    //private val monkeys = testMonkeys
    private val monkeys = inputMonkeys

    private data class Monkey(
        val startingItems: List<Long>,
        val operation: (Long) -> Long,
        val modulo: Long,
        val ifTrue: Int,
        val ifFalse: Int,
    ) {
        var inspectCount = 0L
        var items = startingItems.toMutableList()

        fun destination(level: Long) = if (level % modulo == 0L) ifTrue else ifFalse
    }
}
