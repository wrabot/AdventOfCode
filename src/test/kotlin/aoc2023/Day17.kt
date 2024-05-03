package aoc2023

import Day
import tools.board.Board
import tools.board.Direction4
import tools.board.Direction4.*
import tools.board.toBoard

class Day17(test: Int? = null) : Day(test) {
    override fun solvePart1() = findCost(1, 3)
    override fun solvePart2() = findCost(4, 10)

    private fun findCost(min: Int, max: Int): Int {
        map.cells.forEach { it.clear() }
        val todo = mutableListOf<Board.XY>()
        Direction4.entries.forEach {
            val next = start + it.xy
            val cell = map.getOrNull(next)
            if (cell != null) {
                cell.fromStartHeatLoss[it]!![0] = cell.heatLoss
                cell.todo = true
                todo.add(next)
            }
        }
        while (true) {
            val current = todo.removeFirstOrNull() ?: break
            val currentCell = map[current]
            currentCell.todo = false
            Direction4.entries.forEach { direction ->
                val next = current + direction.xy
                val nextCell = map.getOrNull(next)
                if (nextCell != null) {
                    //same direction
                    currentCell.fromStartHeatLoss[direction]!!.forEachIndexed { index, fromStartHeatLoss ->
                        if (index < max - 1 && fromStartHeatLoss != null) {
                            if (nextCell.update(direction, index + 1, fromStartHeatLoss)) todo.add(next)
                        }
                    }
                    // other direction
                    val otherDirections = when (direction) {
                        North, South -> listOf(East, West)
                        West, East -> listOf(North, South)
                        else -> error("should not happen")
                    }
                    val newFromStartHeatLossFromOtherDirection = otherDirections.mapNotNull {
                        currentCell.fromStartHeatLoss[it]!!.drop(min - 1).filterNotNull().minOrNull()
                    }.minOrNull()
                    if (newFromStartHeatLossFromOtherDirection != null) {
                        if (nextCell.update(direction, 0, newFromStartHeatLossFromOtherDirection)) todo.add(next)
                    }
                }
            }
        }
        return map[end].fromStartHeatLoss.mapNotNull { it.value.filterNotNull().minOrNull() }.min()
    }

    private data class Cell(val heatLoss: Int, val fromStartHeatLoss: Map<Direction4, Array<Int?>>) {
        override fun toString(): String {
            return "[$heatLoss ${
                fromStartHeatLoss.map { entry ->
                    entry.key.c.toString() + entry.value.toList().dropLastWhile { it == null }
                }
            }]"
        }

        fun clear() = fromStartHeatLoss.values.forEach { it.fill(null) }

        fun update(direction: Direction4, index: Int, neighborFromStartHeatLoss: Int): Boolean {
            val currentFromStartHeatLoss = fromStartHeatLoss[direction]!![index]
            val newFromStartHeatLoss = neighborFromStartHeatLoss + heatLoss
            if (currentFromStartHeatLoss == null || newFromStartHeatLoss < currentFromStartHeatLoss) {
                fromStartHeatLoss[direction]!![index] = newFromStartHeatLoss
                if (!todo) {
                    todo = true
                    return true
                }
            }
            return false
        }

        var todo = false
    }

    private val map = lines.toBoard {
        Cell(it.toString().toInt(), Direction4.entries.associateWith { arrayOfNulls(10) })
    }
    private val start = map.xy.first()
    private val end = map.xy.last()
}
