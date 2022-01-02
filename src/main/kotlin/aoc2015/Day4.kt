package aoc2015

import tools.Day
import java.nio.ByteBuffer
import java.security.MessageDigest

class Day4 : Day(2015, 4) {
    override fun solvePart1() = find { it in 0..0xFFF }
    override fun solvePart2() = find { it in 0..0xFF }

    private val md5 = MessageDigest.getInstance("MD5")

    private fun find(isValid: (Int) -> Boolean): Int {
        var n = 0
        while (true) {
            if (isValid(ByteBuffer.wrap(md5.digest((lines[0] + n).toByteArray())).int)) return n
            n++
        }
    }
}
