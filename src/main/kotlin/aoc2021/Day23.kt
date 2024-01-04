package aoc2021

import Day
import kotlin.math.absoluteValue

class Day23(test: Int? = null) : Day(2021, 23, test) {
    override fun solvePart1() = solve(part1Rooms)
    override fun solvePart2() = solve(part2Rooms)

    private val empty = '.'
    private val costs = mapOf('A' to 1, 'B' to 10, 'C' to 100, 'D' to 1000)
    private val roomOutputs = mapOf('A' to 2, 'B' to 4, 'C' to 6, 'D' to 8)
    private val types = roomOutputs.keys.toCharArray()
    private var minCost = Int.MAX_VALUE

    private val part1Rooms = types.associateWith {
        "${lines[3][roomOutputs[it]!! + 1]}${lines[2][roomOutputs[it]!! + 1]}"
    }
    private val extra = mapOf('A' to "DD", 'B' to "BC", 'C' to "AB", 'D' to "CA")
    private val part2Rooms = types.associateWith {
        "${lines[3][roomOutputs[it]!! + 1]}${extra[it]}${lines[2][roomOutputs[it]!! + 1]}"
    }

    private fun solve(rooms: Map<Char, String>): Int {
        minCost = Int.MAX_VALUE
        solve(0, 0, rooms, empty.toString().repeat(11))
        return minCost
    }

    private fun solve(level: Int, cost: Int, rooms: Map<Char, String>, hallway: String): Boolean {
        //log("$level $cost $hallway $rooms")
        if (cost >= minCost) return false

        if (rooms.all { room -> room.value.all { it == room.key } }) {
            minCost = cost
            return true
        }

        if (minCost < Int.MAX_VALUE) {
            // not mandatory but fastest
            val minimalCost = rooms.map { (roomName, roomContent) -> // go to room entrance from room
                roomContent.withIndex().filter { it.value != empty && it.value != roomName }.sumOf {
                    (roomContent.length - it.index + (roomOutputs[it.value]!! - roomOutputs[roomName]!!).absoluteValue) * costs[it.value]!!
                }
            }.sum() + hallway.mapIndexed { index, current -> // go to room entrance from hallway
                if (current != empty) (roomOutputs[current]!! - index).absoluteValue * costs[current]!! else 0
            }.sum() + rooms.map { (roomName, roomContent) -> // enter in room (very minimal)
                roomContent.count { it != roomName }.let { it * (it + 1) / 2 } * costs[roomName]!!
            }.sum()
            if (cost + minimalCost >= minCost) return false
        }

        var solved = false
        hallway.forEachIndexed { hallwayIndex, current ->
            if (current != empty) {
                val roomContent = rooms[current]!!
                if (roomContent.all { it == empty || it == current }) {
                    val roomOutput = roomOutputs[current]!!
                    val path = if (hallwayIndex < roomOutput) hallway.substring(
                        hallwayIndex + 1,
                        roomOutput
                    ) else hallway.substring(roomOutput + 1, hallwayIndex)
                    if (path.all { it == empty }) {
                        val roomPosition = roomContent.indexOf(empty)
                        if (solve(
                                level + 1,
                                cost + ((roomOutput - hallwayIndex).absoluteValue + roomContent.length - roomPosition) * costs[current]!!,
                                rooms.mapValues {
                                    if (it.key == current) it.value.replaceFirst(
                                        empty,
                                        current
                                    ) else it.value
                                },
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
                                rooms.mapValues {
                                    if (it.key == roomName) roomContent.replaceRange(
                                        roomPosition..roomPosition,
                                        empty.toString()
                                    ) else it.value
                                },
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
