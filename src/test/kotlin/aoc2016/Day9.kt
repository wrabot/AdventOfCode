package aoc2016

import Day
import tools.match

class Day9(test: Int? = null) : Day(test) {
    override fun solvePart1() = input.decompress1()

    private fun String.decompress1(): Int = match(regex)?.let {
        val l = it[1].toInt()
        val r = it[2].toInt()
        it[0].length + r * l + it[3].drop(l).decompress1()
    } ?: length

    override fun solvePart2() = input.decompress2()

    private fun String.decompress2(): Long = match(regex)?.let {
        val l = it[1].toInt()
        val r = it[2].toInt()
        it[0].length.toLong() + r * it[3].take(l).decompress2() + it[3].drop(l).decompress2()
    } ?: length.toLong()

    val regex = "(.*?)\\((\\d+?)x(\\d+?)\\)(.*)".toRegex()
}
