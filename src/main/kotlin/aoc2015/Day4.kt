package aoc2015

import forEachInput
import tools.log
import java.security.MessageDigest

object Day4 {
    fun solve() = forEachInput(2015, 4, 1) { lines ->
        val md5 = MessageDigest.getInstance("MD5")
        lines.forEach { key ->
            var n = 0
            while (true) {
                val (a, b, c) = md5.digest((key + n).toByteArray())
                if (a.toInt() == 0 && b.toInt() == 0 && c.toInt() and 0xf0 == 0) {
                    log("part 1: ")
                    n.log()
                    break
                }
                n++
            }
            while (true) {
                val (a, b, c) = md5.digest((key + n).toByteArray())
                if (a.toInt() == 0 && b.toInt() == 0 && c.toInt() == 0) {
                    log("part 2: ")
                    n.log()
                    break
                }
                n++
            }
        }
    }
}
