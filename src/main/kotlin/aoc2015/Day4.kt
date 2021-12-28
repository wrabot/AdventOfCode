package aoc2015

import tools.Day
import java.math.BigInteger
import java.security.MessageDigest

class Day4 : Day(2015, 4) {
    override fun getPart1() = find(5)
    override fun getPart2() = find(6)

    private val md5 = MessageDigest.getInstance("MD5")

    private fun find(size: Int): Int {
        var n = 0
        while (true) {
            if (BigInteger(md5.digest((lines[0] + n).toByteArray())).shiftRight(128 - size * 4) == BigInteger.ZERO) return n
            n++
        }
    }
}
