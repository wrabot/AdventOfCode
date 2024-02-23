package aoc2016

import Day
import tools.match

class Day4(test: Int? = null) : Day(test) {
    override fun solvePart1() = rooms.sumOf { it.sectorId }

    override fun solvePart2() = rooms.single { room ->
        room.name.map { if (it == '-') ' ' else 'a' + ((it - 'a') + room.sectorId) % 26 }
            .joinToString("") { it.toString() }.contains("north")
    }.sectorId

    data class Room(val name: String, val sectorId: Int, val checksum: String) {
        fun check() = name.filter { it.isLowerCase() }.groupingBy { it }.eachCount().toList()
            .sortedBy { it.first }.sortedByDescending { it.second }.joinToString("") { it.first.toString() }
            .take(5) == checksum
    }

    private val regex = "(.*)-(\\d+)\\[(.*)]".toRegex()
    private val rooms = lines.map { it.match(regex)!!.let { (n, s, c) -> Room(n, s.toInt(), c) } }.filter { it.check() }
}
