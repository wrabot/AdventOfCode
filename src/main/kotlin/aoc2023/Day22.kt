package aoc2023

import tools.*

class Day22(test: Int? = null) : Day(2023, 22, test) {
    override fun solvePart1() = cubes.count { cube ->
        cubes.forEach { it.offset = 0 }
        cubes.filter { it !== cube }.check()
    }

    fun List<Cube>.check(): Boolean {
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

    fun List<Cube>.move(): Boolean {
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

    data class Cube(val block: Block) {
        var offset = 0
            set(value) {
                field = value
                zRange = block.zRange.move(offset)
            }

        fun hasIntersection(other: Cube) =
            xRange.hasIntersection(other.xRange) &&
                    yRange.hasIntersection(other.yRange) && zRange.hasIntersection(other.zRange)

        private val xRange = block.xRange
        private val yRange = block.yRange
        var zRange = block.zRange
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
