package aoc2018

import Day


class Day14(test: Int? = null) : Day(test) {
    private val goal = 306281

    override fun solvePart1(): CharSequence {
        val state = State()
        while (state.scores.length < goal + 10) state.next()
        return state.scores.drop(goal).take(10)
    }

    override fun solvePart2(): Int {
        val pattern = goal.toString()
        val state = State()
        var index = -1
        while (index == -1) {
            state.next()
            index = state.scores.indexOf(pattern, state.scores.length - pattern.length - 1)
        }
        return index
    }

    private data class State(val scores: StringBuilder = StringBuilder("37")) {
        private var first: Int = 0
        private var second: Int = 1

        fun next() {
            val score1 = scores[first] - '0'
            val score2 = scores[second] - '0'
            scores.append((score1 + score2).toString())
            first = (first + score1 + 1) % scores.length
            second = (second + score2 + 1) % scores.length
        }
    }
}
