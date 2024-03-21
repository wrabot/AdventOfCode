package aoc2018

import Day

class Day2(test: Int? = null) : Day(test) {
    override fun solvePart1() = lines.map { id -> id.groupingBy { it }.eachCount().values.distinct() }.run {
        count { 2 in it } * count { 3 in it }
    }

    override fun solvePart2() = lines.mapIndexedNotNull { index, id1 ->
        val l = id1.length - 1
        lines.drop(index + 1).map { id2 ->
            id1.zip(id2).filter { it.first == it.second }.joinToString("") { it.first.toString() }
        }.firstOrNull { it.length == l }
    }.first()
}
