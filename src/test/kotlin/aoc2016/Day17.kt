package aoc2016

import Day
import tools.graph.shortPath
import java.security.MessageDigest

class Day17(test: Int? = null) : Day(test) {
    override fun solvePart1() = shortPath(State(""), isEnd = State::isEnd) { it.neighbors() }.last().path

    override fun solvePart2() = State("").longestPath()
    private fun State.longestPath(): Int =
        if (isEnd) path.length else neighbors().maxOfOrNull { it.longestPath() } ?: -1

    data class State(val path: String) {
        val x = path.count { it == 'R' } - path.count { it == 'L' }
        val y = path.count { it == 'D' } - path.count { it == 'U' }
        val isEnd = x == 3 && y == 3
        val invalidDirections = setOfNotNull(
            if (x == 0) 'L' else null,
            if (x == 3) 'R' else null,
            if (y == 0) 'U' else null,
            if (y == 3) 'D' else null,
        )
    }

    fun State.neighbors() = (openedDoors(path) - invalidDirections).map { State(path + it) }

    @OptIn(ExperimentalStdlibApi::class)
    private fun openedDoors(path: String) =
        md5.digest("$input$path".toByteArray()).toHexString().take(4).map {
            it in 'b'..'f'
        }.zip(doors).filter { it.first }.map { it.second }

    private val doors = listOf('U', 'D', 'L', 'R')
    private val md5 = MessageDigest.getInstance("MD5")
}
