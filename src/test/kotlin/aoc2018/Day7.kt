package aoc2018

import Day
import tools.text.match

class Day7(test: Int? = null) : Day(test) {
    override fun solvePart1(): StringBuilder {
        val path = StringBuilder()
        while (path.length < steps.size) path.append(steps.first { step ->
            step !in path && constraints[step].orEmpty().all { it in path }
        })
        return path
    }

    override fun solvePart2(): Int {
        var time = 0
        val tasks = steps.map { Task(it, constraints[it].orEmpty()) }
        while (tasks.any { !it.isDone(time) }) {
            val done = tasks.filter { it.isDone(time) }.map { it.name }
            tasks.filter { it.isReady(done) }.take(5 - tasks.count { it.inProgress(time) }).forEach {
                it.end = it.duration + time
            }
            time++
        }
        return time
    }

    private data class Task(val name: Char, val constraints: List<Char>, var end: Int? = null) {
        val duration = name - 'A' + 61
        fun isDone(time: Int) = end?.let { it <= time } ?: false
        fun inProgress(time: Int) = end?.let { it > time } ?: false
        fun isReady(done: List<Char>) = end == null && constraints.all { it in done }
    }

    private val regex = "Step (.) must be finished before step (.) can begin.".toRegex()
    private val rules = lines.map {
        val (a, b) = it.match(regex)!!
        a.first() to b.first()
    }
    private val steps = rules.flatMap { it.toList() }.distinct().sorted()
    private val constraints = rules.groupBy({ it.second }, { it.first })
}
