package aoc2023

import Day
import aoc2023.Day10.Type.*
import tools.XY
import tools.board.Board
import tools.board.Direction4
import tools.board.Direction4.*
import tools.graph.zone

class Day10(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        board[start].distance = 0
        val todo = Direction4.entries.map { Pipe(start, it, it) }.toMutableList()
        while (true) {
            val pipe = todo.removeFirstOrNull() ?: break
            val nextPipe = nextPipe(pipe.origin, pipe.direction) ?: continue
            val tile = board[nextPipe.origin]
            if (tile.distance != null) continue
            tile.distance = board[pipe.origin].distance!! + 1
            todo.add(nextPipe)
        }
        return board.cells.mapNotNull { it.distance }.max()
    }

    override fun solvePart2(): Int {
        var next = Direction4.entries.firstNotNullOf { nextPipe(start, it) }
        while (true) {
            setZone(next.origin, next.direction, true)
            setZone(next.origin, next.previousDirection, true)
            setZone(next.origin, next.direction, false)
            setZone(next.origin, next.previousDirection, false)
            next = nextPipe(next.origin, next.direction) ?: break
        }
        assert(board.cells.all { it.isClassified() })
        //println(board)
        return board.xy.filter { board[it].rightSide != null }
            .partition { board[it].rightSide!! }.toList()
            .first { zone -> zone.all { it.notOnBorder() } }.count()
    }

    private fun setZone(origin: XY, direction: Direction4, rightSide: Boolean) {
        val point = origin + (if (rightSide) direction.right else direction.left).xy
        val tile = board.getOrNull(point) ?: return
        if (tile.distance != null || tile.rightSide == rightSide) return
        zone(point) { xy -> 
            board.neighbors4(xy).filter { board[it].distance == null }
        }.forEach { board[it].rightSide = rightSide }
    }

    private fun XY.notOnBorder() = x in 1..<board.width - 1 && y in 1..<board.height - 1

    // common

    private fun nextPipe(origin: XY, direction: Direction4): Pipe? {
        val destination = origin + direction.xy
        val destinationTile = board.getOrNull(destination) ?: return null
        val newDirection = when (destinationTile.type) {
            V -> when (direction) {
                North, South -> direction
                else -> null
            }

            H -> when (direction) {
                West, East -> direction
                else -> null
            }

            NW -> when (direction) {
                South -> West
                East -> North
                else -> null
            }

            NE -> when (direction) {
                South -> East
                West -> North
                else -> null
            }

            SW -> when (direction) {
                North -> West
                East -> South
                else -> null
            }

            SE -> when (direction) {
                North -> East
                West -> South
                else -> null
            }

            else -> null
        }
        return newDirection?.let { Pipe(destination, it, direction) }
    }

    private data class Pipe(val origin: XY, val direction: Direction4, val previousDirection: Direction4)

    // Parse input

    private enum class Type { Ground, Start, V, H, NW, NE, SW, SE }
    private data class Tile(val type: Type, var distance: Int? = null, var rightSide: Boolean? = null) {

        fun isClassified() = distance != null || rightSide != null
        override fun toString() = if (distance != null) "#" else when (rightSide) {
            true -> "R"
            false -> "L"
            null -> "?"
        }
    }

    private val board = Board(lines[0].length, lines.size, lines.flatMap { line ->
        line.map {
            Tile(
                when (it) {
                    '.' -> Ground
                    'S' -> Start
                    '|' -> V
                    '-' -> H
                    'J' -> NW
                    'L' -> NE
                    '7' -> SW
                    'F' -> SE
                    else -> error("unexpected \"$it\"")
                }
            )
        }
    })

    private val start = board.xy.first { board[it].type == Start }
}
