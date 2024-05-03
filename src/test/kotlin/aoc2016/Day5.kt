package aoc2016

import Day
import java.security.MessageDigest

class Day5(test: Int? = null) : Day(test) {
    override fun solvePart1(): String {
        val password = StringBuilder()
        var index = 0
        repeat(8) { index = nextHash(index) { password.append(it[5]) } }
        return password.toString()
    }

    override fun solvePart2(): String {
        val password = CharArray(8)
        var index = 0
        while (password.any { !it.isLetterOrDigit() }) {
            index = nextHash(index) {
                val i = it[5] - '0'
                if (i in password.indices && !password[i].isLetterOrDigit()) password[i] = it[6]
            }
        }
        return String(password)
    }

    private fun nextHash(index: Int, block: (String) -> Unit): Int {
        var i = index
        while (true) {
            val hash = hash(i++)
            if (hash.startsWith("00000")) {
                block(hash)
                return i
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun hash(index: Int): String = md5.digest("$input$index".toByteArray()).toHexString()
    private val md5 = MessageDigest.getInstance("MD5")
}
