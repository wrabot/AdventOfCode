package aoc2021

import tools.Day

class Day12(test: Int? = null) : Day(2021, 12, test) {
    override fun solvePart1() = countPaths(links, listOf("start"), false)

    override fun solvePart2() = countPaths(links, listOf("start"), true)

    private val links = lines.flatMap { it.split("-").let { (a, b) -> listOf(a to b, b to a) } }
        .filter { it.second != "start" }

    private fun countPaths(links: List<Pair<String, String>>, path: List<String>, acceptTwoSmallInPath: Boolean): Int =
        links.sumOf {
            when {
                it.first != path.last() -> 0
                it.second == "end" -> 1
                it.second[0].isUpperCase() || it.second !in path -> countPaths(
                    links,
                    path + it.second,
                    acceptTwoSmallInPath
                )
                acceptTwoSmallInPath -> countPaths(links, path + it.second, false)
                else -> 0
            }
        }
}
