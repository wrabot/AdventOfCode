package aoc2019

import Day
import tools.board.Board
import tools.board.Direction4
import tools.board.Direction4.*
import tools.board.toBoard
import tools.board.toGraph
import tools.graph.shortPath

class Day20(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        val graphPart1 = graph.entries.groupBy({ it.key.name }, { v -> v.value.mapKeys { it.key.name } })
            .mapValues { e -> e.value.flatMap { it.toList() }.toMap() }
        return shortPath("AA", "ZZ", { s, d -> graphPart1[s]!![d]!!.toDouble() }) {
            graphPart1[it]!!.keys.toList()
        }.zipWithNext { s, d -> graphPart1[s]!![d]!! }.sum() - 1
    }

    override fun solvePart2() =
        shortPath(LevelPortal("AA"), LevelPortal("ZZ"), { s, d -> cost(s, d).toDouble() }) {
            it.neighbors(graph[it.portal].orEmpty().keys)
        }.zipWithNext(::cost).sum() - 1

    data class LevelPortal(val portal: Portal, val level: Int) {
        constructor(name: String) : this(Portal(name, true), 0)

        override fun toString() = "$portal$level"
        fun neighbors(portals: Set<Portal>) = with(portals.map { LevelPortal(it, level) }) {
            when {
                !portal.out -> this + LevelPortal(Portal(portal.name, true), level + 1)
                level < 1 -> this
                else -> this + LevelPortal(Portal(portal.name, false), level - 1)
            }
        }
    }

    private fun cost(s: LevelPortal, d: LevelPortal) =
        if (s.portal.name == d.portal.name) 0 else graph[s.portal]!![d.portal]!!

    // common

    sealed interface Cell
    data object Wall : Cell
    data object Empty : Cell
    data class Portal(val name: String, val out: Boolean) : Cell {
        override fun toString() = "$name${if (out) '^' else 'v'}"
    }

    private val board = lines.toBoard { it }

    private val portals = board.cells.indices.mapNotNull { i ->
        val c = board.cells[i]
        if (!c.isLetter()) return@mapNotNull null
        val xy = board.xy[i]
        val d = Direction4.entries.find { board.getOrNull(xy + it.xy) == '.' } ?: return@mapNotNull null
        val s = board[xy - d.xy]
        val name = when (d) {
            East, South -> "$s$c"
            West, North -> "$c$s"
        }
        val out = board.getOrNull(xy - d.xy * 2) == null
        i to Portal(name, out)
    }.toMap()

    private val graph = Board(board.width, board.height, board.cells.mapIndexed { index, c ->
        if (c == '.') Empty else portals[index] ?: Wall
    }).toGraph(
        isStart = { this is Portal },
        isEnd = { this is Portal },
        isWall = { this is Wall },
    ) { (it.size - 1).coerceAtLeast(0) }
        .mapKeys { it.key as Portal }.mapValues { e -> e.value.mapKeys { it.key as Portal } }
}


