package aoc2018

import Day
import tools.match
import java.util.*

class Day9(test: Int? = null) : Day(test) {
    override fun solvePart1() = game.play(1)

    override fun solvePart2() = game.play(100)

    private data class Game(val players: Int, val lastMarble: Int) {
        fun play(factor: Int): Long {
            val scores = LongArray(players)
            val circle = LinkedList<Int>()
            circle.add(0)
            for (marble in 1..lastMarble * factor) {
                if (marble % 23 != 0) {
                    repeat(2) { circle.addLast(circle.removeFirst()) }
                    circle.addFirst(marble)
                } else {
                    repeat(7) { circle.addFirst(circle.removeLast()) }
                    scores[(marble - 1) % scores.size] += marble.toLong() + circle.removeFirst()
                }
            }
            return scores.max()
        }
    }

    private val game: Game

    init {
        val (players, lastMarble) = input.match("(.*) players; last marble is worth (.*) points".toRegex())!!
        game = Game(players.toInt(), lastMarble.toInt())
    }
}
