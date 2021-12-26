package aoc2021

import Block
import Point
import forEachInput
import log

object Day22 {
    fun solve() = forEachInput(2021, 22, 1, 2) { lines ->
        val procedure = lines.map { line ->
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

        log("part 1: ")
        part1(procedure)

        log("part 2: ")
        part2(procedure)
    }

    private fun part1(procedure: List<Step>) {
        val reactorSize = 50
        val reactorFullSize = reactorSize * 2 + 1
        val reactor = Array(reactorFullSize * reactorFullSize * reactorFullSize) { false }
        val reactorBlock = Block(Point(-reactorSize, -reactorSize, -reactorSize), Point(reactorSize, reactorSize, reactorSize))
        procedure.forEach {
            val block = it.cuboid.intersect(reactorBlock)
            if (block != null)
                for (x in block.start.x..block.end.x)
                    for (y in block.start.y..block.end.y)
                        for (z in block.start.z..block.end.z)
                            reactor[((z + reactorSize) * reactorFullSize + (y + reactorSize)) * reactorFullSize + (x + reactorSize)] = it.mode
        }
        reactor.count { it }.log()
    }

    private fun part2(procedure: List<Step>) {
        var count = 0L
        val exclusions = mutableListOf<Block>()
        procedure.reversed().forEach { step ->
            if (step.mode) {
                count += step.cuboid.size() - exclusions.mapNotNull { it.intersect(step.cuboid) }.sum()
            }
            exclusions.add(step.cuboid)
        }
        count.log()
    }

    private val regex = "(on|off) x=(-?\\d*)\\.\\.(-?\\d*),y=(-?\\d*)\\.\\.(-?\\d*),z=(-?\\d*)\\.\\.(-?\\d*)".toRegex()

    data class Step(val mode: Boolean, val cuboid: Block)

    private fun List<Block>.sum(): Long =
        if (isEmpty()) 0 else first().size() + drop(1).sum() - drop(1).mapNotNull { it.intersect(first()) }.sum()
}
