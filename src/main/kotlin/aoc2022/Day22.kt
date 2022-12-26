package aoc2022

import tools.Board
import tools.Day
import tools.Point

class Day22(test: Int? = null) : Day(2022, 22, test) {
    override fun solvePart1(): Int {
        var position = start
        var directionIndex = 0
        moves.forEach { move ->
            val direction = directions[directionIndex]
            val directionChar = directionChars[directionIndex]
            repeat(move.first) {
                //board[position].move = directionChar
                var next = position
                while (true) {
                    next = Point((board.width + next.x + direction.x) % board.width, (board.height + next.y + direction.y) % board.height)
                    when (board[next].content) {
                        '#' -> break
                        '.' -> {
                            position = next
                            break
                        }
                    }
                }
            }
            directionIndex = (directions.size + directionIndex + move.second) % directions.size
        }
        return (position.y + 1) * 1000 + 4 * (position.x + 1) + directionIndex
    }

    override fun solvePart2(): Int {
        val mapping = mutableMapOf<Point, Pair<Point, Int>>()
        if (board.width < 150) {
            // test1
            val blockSize = 4
            repeat(blockSize) { blockIndex ->
                //4->6
                mapping[Point(16, 4 + blockIndex)] = Point(15 - blockIndex, 8) to 1
                //5->2
                mapping[Point(8 + blockIndex, 12)] = Point(3 - blockIndex, 7) to 3
                //3->1
                mapping[Point(4 + blockIndex, -1)] = Point(8, blockIndex) to 0
            }
        } else {
            val blockSize = 50
            repeat(blockSize) { blockIndex ->
                //1->6
                mapping[Point(50 + blockIndex, -1)] = Point(0, 150 + blockIndex) to 0
                //6->5
                mapping[Point(150, 150 + blockIndex)] = Point(50 + blockIndex, 149) to 3
                //2->6
                mapping[Point(100 + blockIndex, -1)] = Point(blockIndex, 199) to 3
                //4->1
                mapping[Point(-1, 100 + blockIndex)] = Point(50, 49 - blockIndex) to 0
                //1->4
                mapping[Point(-1, blockIndex)] = Point(0, 149 - blockIndex) to 0
                //4->3
                mapping[Point(blockIndex, -1)] = Point(50, 50 + blockIndex) to 0
                //6->2
                mapping[Point(blockIndex, 200)] = Point(100 + blockIndex, 0) to 1
                //2->3
                mapping[Point(100 + blockIndex, 200)] = Point(99, 50 + blockIndex) to 2
                //6->1
                mapping[Point(-1, 150 + blockIndex)] = Point(50 + blockIndex, 0) to 1
                //3->4
                mapping[Point(-1, 50 + blockIndex)] = Point(blockIndex, 100) to 1
                //5->6
                mapping[Point(50 + blockIndex, 200)] = Point(49, 150 + blockIndex) to 2
                //3->2
                mapping[Point(150, 50 + blockIndex)] = Point(100 + blockIndex, 49) to 3
                //2->5
                mapping[Point(150, blockIndex)] = Point(99, 149 - blockIndex) to 2
                //5->2
                mapping[Point(150, 100 + blockIndex)] = Point(149, 49 - blockIndex) to 2
            }
        }

        var position = start
        var directionIndex = 0
        moves.forEach { move ->
            //log("$position $directionIndex $move")
            repeat(move.first) {
                val direction = directions[directionIndex]
                val directionChar = directionChars[directionIndex]
                board[position].move = directionChar
                var next = position
                while (true) {
                    next = Point(next.x + direction.x, next.y + direction.y)
                    val mapping = mapping[next]
                    if (mapping != null) {
                        next = mapping.first
                    }
                    when (board[next].content) {
                        '#' -> break
                        '.' -> {
                            position = next
                            if (mapping != null) {
                                directionIndex = mapping.second
                            }
                            break
                        }
                    }
                }
            }
            directionIndex = (directions.size + directionIndex + move.second) % directions.size
        }
        return (position.y + 1) * 1000 + 4 * (position.x + 1) + directionIndex
    }

    // common

    data class Cell(val content: Char, var move: Char? = null) {
        override fun toString() = (move ?: content).toString()
    }

    private val directionChars = listOf('>', 'v', '<', '^')
    private val directions = listOf(Point(1, 0), Point(0, 1), Point(-1, 0), Point(0, -1))

    val start = Point(lines[0].indexOfFirst { it != ' ' }, 0)

    val board = lines.dropLast(2).run {
        val width = maxOf { it.length }
        Board(width, size, joinToString("") { it.padEnd(width, ' ') }.map { Cell(it) })
    }

    private val moves = lines.last().replace("R", " R ").replace("L", " L ").split(" ").plus(" ")
        .chunked(2) {
            it[0].toInt() to when (it[1]) {
                "R" -> 1
                "L" -> -1
                else -> 0
            }
        }
}
