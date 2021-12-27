package aoc2021

import tools.log
import kotlin.math.absoluteValue

object Day23 {
    @Suppress("SpellCheckingInspection")
    fun solve() {
        log("part1: ")
        solve(mapOf('A' to "AB", 'B' to "DC", 'C' to "CB", 'D' to "AD"))
        solve(mapOf('A' to "CA", 'B' to "CD", 'C' to "DA", 'D' to "BB"))
        log("part2: ")
        solve(mapOf('A' to "ADDB", 'B' to "DBCC", 'C' to "CABB", 'D' to "ACAD"))
        solve(mapOf('A' to "CDDA", 'B' to "CBCD", 'C' to "DABA", 'D' to "BCAB"))
    }

    private const val empty = '.'
    private val costs = mapOf('A' to 1, 'B' to 10, 'C' to 100, 'D' to 1000)
    private val roomOutputs = mapOf('A' to 2, 'B' to 4, 'C' to 6, 'D' to 8)
    private val types = roomOutputs.keys.toCharArray()
    private var minCost = Int.MAX_VALUE

    private fun solve(rooms: Map<Char, String>) {
        minCost = Int.MAX_VALUE
        solve(0, 0, rooms, empty.toString().repeat(11))
        minCost.log()
    }

    private fun solve(level: Int, cost: Int, rooms: Map<Char, String>, hallway: String): Boolean {
        //log("$level $cost $hallway $rooms")
        if (cost >= minCost) return false

        if (rooms.all { room -> room.value.all { it == room.key } }) {
            minCost = cost
            return true
        }

        // not mandatory but improve
        val estimateCost = rooms.map { (roomName, roomContent) ->
            roomContent.filter { it != empty && it != roomName }.sumOf {
                (roomContent.length * 2 + (roomOutputs[it]!! - roomOutputs[roomName]!!).absoluteValue) * costs[it]!!
            }
        }.sum() + hallway.mapIndexed { index, current ->
            if (current != empty) rooms[current]!!.length + (roomOutputs[current]!! - index).absoluteValue * costs[current]!! else 0
        }.sum()
        if (cost + estimateCost >= minCost) return false

        var solved = false
        hallway.forEachIndexed { hallwayIndex, current ->
            if (current != empty) {
                val roomContent = rooms[current]!!
                if (roomContent.all { it == empty || it == current }) {
                    val roomOutput = roomOutputs[current]!!
                    val path = if (hallwayIndex < roomOutput) hallway.substring(hallwayIndex + 1, roomOutput) else hallway.substring(roomOutput + 1, hallwayIndex)
                    if (path.all { it == empty }) {
                        val roomPosition = roomContent.indexOf(empty)
                        if (solve(
                                level + 1,
                                cost + ((roomOutput - hallwayIndex).absoluteValue + roomContent.length - roomPosition) * costs[current]!!,
                                rooms.mapValues { if (it.key == current) it.value.replaceFirst(empty, current) else it.value },
                                hallway.take(hallwayIndex) + empty + hallway.drop(hallwayIndex + 1)
                            )
                        ) {
                            solved = true
                            //log("$level $cost $hallway $rooms")
                        }
                    }
                }
            }
        }
        rooms.forEach { (roomName, roomContent) ->
            if (roomContent.any { it != empty && it != roomName }) {
                val roomPosition = roomContent.indexOfLast { it != empty }
                val roomOutput = roomOutputs[roomName]!!
                val start = hallway.lastIndexOfAny(types, roomOutput) + 1
                val end = hallway.indexOfAny(types, roomOutput).takeIf { it != -1 } ?: hallway.length
                for (hallwayIndex in start until end) {
                    if (hallwayIndex !in roomOutputs.values) {
                        val current = roomContent[roomPosition]
                        if (solve(
                                level + 1,
                                cost + ((roomOutput - hallwayIndex).absoluteValue + roomContent.length - roomPosition) * costs[current]!!,
                                rooms.mapValues { if (it.key == roomName) roomContent.replaceRange(roomPosition..roomPosition, empty.toString()) else it.value },
                                hallway.take(hallwayIndex) + current + hallway.drop(hallwayIndex + 1)
                            )
                        ) {
                            solved = true
                            //log("$level $cost $hallway $rooms")
                        }
                    }
                }
            }
        }
        return solved
    }
}
