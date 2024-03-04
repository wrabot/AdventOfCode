package aoc2016

import Day
import java.security.MessageDigest

class Day14(test: Int? = null) : Day(test) {
    override fun solvePart1() = solve(::hash)

    override fun solvePart2() = solve(::hash2016)

    private val cache2016 = mutableMapOf<Int, String>()
    private fun hash2016(index: Int) = cache2016.getOrPut(index) {
        var h = hash(index)
        repeat(2016) { h = hash(h) }
        h
    }

    fun solve(hash: (Int) -> String): Int {
        var index = 0
        repeat(64) {
            do {
                val s = hash(index++).windowed(3).firstNotNullOfOrNull { dict[it] }
            } while (s == null || (0..999).find { hash(index + it).contains(s) } == null)
        }
        return index - 1
    }

    private val dict = (0..15).map { it.toString(16) }.associate { it.repeat(3) to it.repeat(5) }

    private val cache = mutableMapOf<Int, String>()
    private fun hash(index: Int) = cache.getOrPut(index) { hash("$input$index") }

    @OptIn(ExperimentalStdlibApi::class)
    private fun hash(s: String): String = md5.digest(s.toByteArray()).toHexString()
    private val md5 = MessageDigest.getInstance("MD5")
}
