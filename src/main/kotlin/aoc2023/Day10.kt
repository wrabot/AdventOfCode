package aoc2023

import aoc2023.Day10.Type.*
import tools.board.Board
import Day
import tools.board.Point

class Day10(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        board[start].distance = 0
        val todo = Direction.entries.map { Pipe(start, it, it) }.toMutableList()
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
        var next = Direction.entries.firstNotNullOf { nextPipe(start, it) }
        while (true) {
            setZone(next.origin, next.direction, true)
            setZone(next.origin, next.previousDirection, true)
            setZone(next.origin, next.direction, false)
            setZone(next.origin, next.previousDirection, false)
            next = nextPipe(next.origin, next.direction) ?: break
        }
        assert(board.cells.all { it.isClassified() })
        //println(board)
        return board.points.filter { board[it].rightSide != null }
            .partition { board[it].rightSide!! }.toList()
            .first { zone -> zone.all { it.notOnBorder() } }.count()
    }

    private fun setZone(origin: Point, direction: Direction, rightSide: Boolean) {
        val point = origin + if (rightSide) direction.right else direction.left
        val tile = board.getOrNull(point) ?: return
        if (tile.distance != null || tile.rightSide == rightSide) return
        board.zone4(point) { board[it].distance == null && tile.rightSide == null }
            .forEach { board[it].rightSide = rightSide }
    }

    private fun Point.notOnBorder() = x in 1..<board.width - 1 && y in 1..<board.height - 1

    // common

    private fun nextPipe(origin: Point, direction: Direction): Pipe? {
        val destination = origin + direction.delta
        val destinationTile = board.getOrNull(destination) ?: return null
        val newDirection = when (destinationTile.type) {
            Vertical -> when (direction) {
                Direction.North, Direction.South -> direction
                else -> null
            }

            Horizontal -> when (direction) {
                Direction.West, Direction.East -> direction
                else -> null
            }

            NorthWest -> when (direction) {
                Direction.South -> Direction.West
                Direction.East -> Direction.North
                else -> null
            }

            NorthEast -> when (direction) {
                Direction.South -> Direction.East
                Direction.West -> Direction.North
                else -> null
            }

            SouthWest -> when (direction) {
                Direction.North -> Direction.West
                Direction.East -> Direction.South
                else -> null
            }

            SouthEast -> when (direction) {
                Direction.North -> Direction.East
                Direction.West -> Direction.South
                else -> null
            }

            else -> null
        }
        return newDirection?.let { Pipe(destination, it, direction) }
    }


    data class Pipe(val origin: Point, val direction: Direction, val previousDirection: Direction)

    enum class Direction(val delta: Point, val right: Point, val left: Point) {
        North(Point(0, -1), Point(1, 0), Point(-1, 0)),
        West(Point(-1, 0), Point(0, -1), Point(0, 1)),
        East(Point(1, 0), Point(0, 1), Point(0, -1)),
        South(Point(0, 1), Point(-1, 0), Point(1, 0))
    }

    // Parse input

    enum class Type { Ground, Start, Vertical, Horizontal, NorthWest, NorthEast, SouthWest, SouthEast }
    data class Tile(val type: Type, var distance: Int? = null, var rightSide: Boolean? = null) {

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
                    '|' -> Vertical
                    '-' -> Horizontal
                    'J' -> NorthWest
                    'L' -> NorthEast
                    '7' -> SouthWest
                    'F' -> SouthEast
                    else -> error("unexpected \"$it\"")
                }
            )
        }
    })

    private val start = board.points.first { board[it].type == Start }
}
