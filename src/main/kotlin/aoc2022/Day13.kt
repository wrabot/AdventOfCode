package aoc2022

import tools.Day

class Day13(test: Int? = null) : Day(2022, 13, test) {
    override fun solvePart1() =
        packets.mapIndexedNotNull { index, pair -> if (compare(pair[0], pair[1]) >= 0) index + 1 else null }.sum()

    override fun solvePart2() =
        (packets.flatten() + divider1 + divider2).sortedWith { left, right -> -compare(left, right) }
            .run { packetIndex(divider1) * packetIndex(divider2) }

    private val packets = input.split("\n\n").map { block ->
        block.split("\n").map { it.parse(0).second.first() }
    }

    private val divider1 = listOf(listOf(2))
    private val divider2 = listOf(listOf(6))

    private fun List<Any>.packetIndex(packet: Any) = indexOfFirst { compare(it, packet) == 0 } + 1

    private fun compare(left: Any, right: Any): Int = when {
        left is Int && right is Int -> right - left
        left is List<*> && right is Int -> compare(left, listOf(right))
        left is Int && right is List<*> -> compare(listOf(left), right)
        left is List<*> && right is List<*> -> left.zip(right).map { compare(it.first!!, it.second!!) }
            .dropWhile { it == 0 }.run {
                firstOrNull() ?: (right.size - left.size)
            }
        else -> error("!!!!")
    }

    private fun String.parse(start: Int): Pair<Int, List<Any>> {
        val siblings = mutableListOf<Any>()
        var index = start
        while (index < length) {
            when (this[index]) {
                '[' -> parse(index + 1).let {
                    index = it.first
                    siblings.add(it.second)
                }
                ']' -> return Pair(index + 1, siblings)
                ',' -> index++
                else -> indexOfAny("],".toCharArray(), index).let {
                    siblings.add(substring(index, it).toInt())
                    index = it
                }
            }
        }
        return Pair(index, siblings)
    }
}
