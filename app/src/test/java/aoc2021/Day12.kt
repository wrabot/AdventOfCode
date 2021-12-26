package aoc2021

import forEachInput
import log

object Day12 {
    fun solve() = forEachInput(2021, 12, 1, 2, 3, 4) { lines ->
        val links = lines.flatMap { it.split("-").let { (a, b) -> listOf(a to b, b to a) } }
            .filter { it.second != "start" }

        log("part 1: ")
        countPaths(links, listOf("start"), false).log()

        log("part 2: ")
        countPaths(links, listOf("start"), true).log()
    }

    private fun countPaths(links: List<Pair<String, String>>, path: List<String>, acceptTwoSmallInPath: Boolean): Int =
        links.sumOf {
            when {
                it.first != path.last() -> 0
                it.second == "end" -> 1
                it.second[0].isUpperCase() || it.second !in path -> countPaths(links, path + it.second, acceptTwoSmallInPath)
                acceptTwoSmallInPath -> countPaths(links, path + it.second, false)
                else -> 0
            }
        }
}
