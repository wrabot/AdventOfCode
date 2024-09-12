package aoc2018

import Day


import tools.text.match
import tools.range.size
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

class Day4(test: Int? = null) : Day(test) {
    override fun solvePart1() = guards.maxBy { it.value.sumOf(IntRange::size) }.run { key * value.maxMinute()!!.first }

    override fun solvePart2() =
        guards.mapValues { it.value.maxMinute() }.maxBy { it.value?.second ?: 0 }.run { key * value!!.first }

    private fun List<IntRange>.maxMinute() =
        flatMap { i -> i.map { it % 60 } }.groupingBy { it }.eachCount().maxByOrNull { it.value }?.toPair()

    private val guards = mutableMapOf<Int, MutableList<IntRange>>()

    init {
        val mainRegex = "\\[1518-(.*)] (.*)".toRegex()
        val guardRegex = "Guard #(.*) begins shift".toRegex()
        val dateFormat = SimpleDateFormat("MM-dd HH:mm")
        fun String.toMinutes() = TimeUnit.MILLISECONDS.toMinutes(dateFormat.parse(this).time).toInt()
        var guard: MutableList<IntRange>? = null
        var asleep: Int? = null
        lines.sorted().forEach { line ->
            val (date, action) = line.match(mainRegex)!!
            when (action) {
                "falls asleep" -> asleep = date.toMinutes()
                "wakes up" -> guard!!.add(asleep!! until date.toMinutes())
                else -> guard = guards.getOrPut(action.match(guardRegex)!!.first().toInt()) { mutableListOf() }
            }
        }
    }
}
