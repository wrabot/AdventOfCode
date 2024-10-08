package aoc2023

import Day
import tools.geometry.Block
import tools.Point
import tools.range.hasIntersection
import tools.range.intRange
import tools.range.move

class Day22(test: Int? = null) : Day(test) {
    override fun solvePart1() = cubes.count { cube ->
        cubes.forEach { it.offset = 0 }
        cubes.filter { it !== cube }.check()
    }

    private fun List<Cube>.check(): Boolean {
        forEach { cube ->
            while (cube.zRange.first > 1) {
                cube.offset--
                if (any { cube !== it && cube.hasIntersection(it) }) {
                    cube.offset++
                    break
                }
                return false
            }
        }
        return true
    }

    override fun solvePart2() = cubes.sumOf { cube ->
        cubes.forEach { it.offset = 0 }
        cubes.filter { it !== cube }.move()
        cubes.count { it.offset != 0 }
    }

    private fun List<Cube>.move(): Boolean {
        var isMoved = false
        forEach { cube ->
            while (cube.zRange.first > 1) {
                cube.offset--
                if (any { cube !== it && cube.hasIntersection(it) }) {
                    cube.offset++
                    break
                }
                isMoved = true
            }
        }
        return isMoved
    }

    private data class Cube(val block: Block) {
        var offset = 0
            set(value) {
                zRange = zRange.move(value - field)
                field = value
            }

        fun hasIntersection(other: Cube) =
            xRange.hasIntersection(other.xRange) && yRange.hasIntersection(other.yRange) && zRange.hasIntersection(other.zRange)

        private val xRange = intRange(block.start.x..block.end.x)
        private val yRange = intRange(block.start.y..block.end.y)
        var zRange = intRange(block.start.z..block.end.z)
            private set
    }

    private val cubes: List<Cube>

    init {
        val regex = "(\\d+),(\\d+),(\\d+)~(\\d+),(\\d+),(\\d+)".toRegex()
        val snapshot = lines.map { line ->
            regex.matchEntire(line)!!.groupValues.asSequence().drop(1).map { it.toInt() }.chunked(3) { (a, b, c) ->
                Point(a, b, c)
            }.zipWithNext { a, b -> Cube(Block(a, b)) }.single()
        }.sortedBy { it.zRange.first }
        @Suppress("ControlFlowWithEmptyBody")
        do while (snapshot.move())
        cubes = snapshot.map {
            Cube(
                Block(
                    it.block.start.copy(z = it.block.start.z + it.offset),
                    it.block.end.copy(z = it.block.end.z + it.offset)
                )
            )
        }
    }
}
