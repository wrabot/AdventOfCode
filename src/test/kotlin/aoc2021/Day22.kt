package aoc2021

import Day
import tools.geometry.Block
import tools.geometry.Point
import tools.intRange

class Day22(test: Int? = null) : Day(test) {
    override fun solvePart1(): Any {
        val halfSize = 50
        val fullSize = halfSize * 2 + 1
        val reactor = Array(fullSize * fullSize * fullSize) { false }
        val reactorBlock = Block(Point(-halfSize, -halfSize, -halfSize), Point(halfSize, halfSize, halfSize))
        for (step in procedure) {
            val block = step.cuboid.intersect(reactorBlock) ?: continue
            for (x in intRange(block.start.x..block.end.x))
                for (y in intRange(block.start.y..block.end.y))
                    for (z in intRange(block.start.z..block.end.z))
                        reactor[((z + halfSize) * fullSize + (y + halfSize)) * fullSize + (x + halfSize)] = step.mode
        }
        return reactor.count { it }
    }

    override fun solvePart2(): Any {
        var count = 0L
        val exclusions = mutableListOf<Block>()
        procedure.reversed().forEach { step ->
            if (step.mode) {
                count += step.cuboid.size().toLong() - exclusions.mapNotNull { it.intersect(step.cuboid) }.sum()
            }
            exclusions.add(step.cuboid)
        }
        return count
    }

    private data class Step(val mode: Boolean, val cuboid: Block)

    private val regex = "(on|off) x=(-?\\d*)\\.\\.(-?\\d*),y=(-?\\d*)\\.\\.(-?\\d*),z=(-?\\d*)\\.\\.(-?\\d*)".toRegex()

    private val procedure = lines.map { line ->
        regex.matchEntire(line)?.groupValues.orEmpty().drop(1).let {
            Step(
                it[0] == "on",
                Block(
                    Point(it[1].toInt(), it[3].toInt(), it[5].toInt()),
                    Point(it[2].toInt(), it[4].toInt(), it[6].toInt())
                )
            )
        }
    }

    private fun Block.size() = (end - start).run { (x.toLong() + 1) * (y.toLong() + 1) * (z.toLong() + 1) }

    private fun List<Block>.sum(): Long = if (isEmpty()) 0 else
        first().size().toLong() + drop(1).sum() - drop(1).mapNotNull { it.intersect(first()) }.sum()
}
