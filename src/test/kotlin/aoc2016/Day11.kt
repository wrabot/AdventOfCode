package aoc2016

import Day
import tools.graph.shortPath
import tools.subLists

class Day11(test: Int? = null) : Day(test) {
    override fun solvePart1() = solve(startPart1)
    override fun solvePart2() = solve(startPart2)
    private fun solve(start: State) = shortPath(
        start = start,
        isEnd = State::isEnd,
        neighbors = State::neighbors
    ).size - 1

    data class State(val elevator: Int, val devices: List<Device>) { // devices must be sorted
        val isEnd = devices.all { it.microchip == 4 && it.generator == 4 }

        fun neighbors(): List<State> {
            val moves = mutableSetOf<Pair<Move, Move?>>()
            moves.addAll(devices.filter { it.microchip == elevator && it.generator == elevator }
                .map { Move(it, null) to null })
            moves.addAll(devices.filter { it.generator == elevator }.map { Move(it, false) }.oneOrTwo())
            moves.addAll(devices.filter { it.microchip == elevator }.map { Move(it, true) }.oneOrTwo())

            val neighbors = mutableSetOf<State>()
            if (elevator > 1) neighbors.addAll(moves.map { State(elevator - 1, move(it, elevator - 1)) })
            if (elevator < 4) neighbors.addAll(moves.map { State(elevator + 1, move(it, elevator + 1)) })
            return neighbors.filter {
                (1..4).all { floor ->
                    devices.all { it.generator != floor } || devices.all { it.microchip != floor || it.generator == floor }
                }
            }
        }

        data class Move(val device: Device, val isMicrochip: Boolean?) // null means both

        private fun List<Move>.oneOrTwo() =
            subLists().filter { it.size in 1..2 }.map { it.first() to it.getOrNull(1) }

        private fun move(move: Pair<Move, Move?>, floor: Int) =
            move.second.move(move.first.move(devices, floor), floor).sortedBy { it.microchip }.sortedBy { it.generator }

        fun Move?.move(devices: List<Device>, floor: Int) = if (this == null) devices else devices - device + Device(
            microchip = if (isMicrochip != false) floor else device.microchip,
            generator = if (isMicrochip != true) floor else device.generator,
        )
    }

    // The name is useless and must be ignored otherwise, combinatorics explodes
    data class Device(val microchip: Int, val generator: Int)

    private val microchipRegex = " (\\w*?)-compatible microchip".toRegex()
    private val microchips = lines.flatMapIndexed { index, line ->
        microchipRegex.findAll(line).map { it.groupValues[1] to index + 1 }
    }.toMap()
    private val generatorRegex = " (\\w*?) generator".toRegex()
    private val generators = lines.flatMapIndexed { index, line ->
        generatorRegex.findAll(line).map { it.groupValues[1] to index + 1 }
    }.toMap()

    private val startPart1 = State(
        elevator = 1,
        devices = microchips.keys.map { Device(microchips[it]!!, generators[it]!!) }
    )

    private val startPart2 = State(
        elevator = 1,
        devices = startPart1.devices + Device(1, 1) + Device(1, 1)
    )
}
