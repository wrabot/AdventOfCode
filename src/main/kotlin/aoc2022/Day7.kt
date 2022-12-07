package aoc2022

import tools.Day
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.div

class Day7(test: Int? = null) : Day(2022, 7, test) {
    override fun solvePart1() = sizes.filter { it <= 100000 }.sum()
    override fun solvePart2() = sizes.filter { space + it >= 30000000 }.min()

    private val sizes: List<Int>
    private val space: Int

    init {
        val dirs = mutableListOf<Path>()
        val files = mutableMapOf<Path, Int>()
        var path = Path("/")
        var ls = false
        lines.drop(1).forEach { line ->
            when {
                line == "$ ls" -> ls = true
                line == "$ cd .." -> {
                    ls = false
                    path = path.parent
                }
                line.startsWith("$ cd") -> {
                    ls = false
                    path /= line.removePrefix("$ cd ")
                }
                ls && line.startsWith("dir ") -> dirs.add(path / line.removePrefix("dir "))
                ls -> {
                    val (size, name) = line.split(" ")
                    files[path / name] = size.toInt()
                }
                else -> error("should not happen")
            }
        }
        sizes = dirs.map { p -> files.filter { it.key.startsWith(p) }.values.sum() }
        space = 70000000 - files.values.sum()
    }
}
