package aoc2021

import tools.Block
import tools.Day
import tools.Point

class Day22(test: Int? = null) : Day(2021, 22, test) {
    override fun getPart1(): Any {
        val halfSize = 50
        val fullSize = halfSize * 2 + 1
        val reactor = Array(fullSize * fullSize * fullSize) { false }
        val reactorBlock = Block(Point(-halfSize, -halfSize, -halfSize), Point(halfSize, halfSize, halfSize))
        procedure.forEach {
            val block = it.cuboid.intersect(reactorBlock)
            if (block != null)
                for (x in block.start.x..block.end.x)
                    for (y in block.start.y..block.end.y)
                        for (z in block.start.z..block.end.z)
                            reactor[((z + halfSize) * fullSize + (y + halfSize)) * fullSize + (x + halfSize)] =
                                it.mode
        }
        return reactor.count { it }
    }

    override fun getPart2(): Any {
        var count = 0L
        val exclusions = mutableListOf<Block>()
        procedure.reversed().forEach { step ->
            if (step.mode) {
                count += step.cuboid.size() - exclusions.mapNotNull { it.intersect(step.cuboid) }.sum()
            }
            exclusions.add(step.cuboid)
        }
        return count
    }

    data class Step(val mode: Boolean, val cuboid: Block)

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

    private fun List<Block>.sum(): Long =
        if (isEmpty()) 0 else first().size() + drop(1).sum() - drop(1).mapNotNull { it.intersect(first()) }.sum()
}
