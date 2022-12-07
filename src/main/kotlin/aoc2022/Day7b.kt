package aoc2022

import tools.Day

class Day7b(test: Int? = null) : Day(2022, 7, test) {
    override fun solvePart1() = sizes.log().filter { it <= 100000 }.sum()
    override fun solvePart2() = sizes.filter { space + it >= 30000000 }.min()

    private val sizes = mutableListOf(0)
    private val space: Int

    init {
        val path = mutableListOf<Int>()
        lines.forEach { line ->
            when {
                line == "$ ls" -> {}
                line == "$ cd .." -> sizes.add(path.removeLast())
                line.startsWith("$ cd") -> path.add(0)
                line.startsWith("dir ") -> {}
                else -> {
                    val size = line.split(" ")[0].toInt()
                    path.indices.forEach {
                        path[it] += size
                    }
                }
            }
        }
        sizes.addAll(path)
        space = 70000000 - sizes.max()
    }
}
