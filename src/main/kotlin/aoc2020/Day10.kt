package aoc2020

import forEachInput
import tools.log

object Day10 {
    fun solve() = forEachInput(2020, 10, 3) { lines ->
        val adapters = lines.map { it.toInt() }.sortedDescending()
        val links = adapters + 0
        val groups = links.groupBy({ it }) { v -> links.filter { it in v - 3 until v } }
            .mapValues { it.value.flatten() }

        log("part 1: ")
        val list = mutableListOf(links.first())
        while (list.last() != 0) {
            list.add(groups.getValue(list.last()).first())
        }
        val diff = list.zipWithNext { a, b -> a - b }
        val result = diff.count { it == 1 } * (diff.count { it == 3 } + 1)
        result.log()

        log("part 2: ")
        val counts = mutableMapOf(0 to 1L)
        adapters.reversed().forEach {
            counts[it] = groups.getValue(it).fold(0L) { acc, i -> acc + counts[i]!! }
        }
        counts[adapters.first()].log()
    }
}
