package aoc2015

import forEachInput
import tools.log

object Day5 {
    fun solve() = forEachInput(2015, 5, 2) { lines ->
        val required = List(26) { ('a' + it).toString().repeat(2) }
        val forbidden = listOf("ab", "cd", "pq", "xy")

        log("part 1: ")
        lines.count { line -> line.count { it in "aeiou" } >= 3 && line.findAnyOf(required) != null && line.findAnyOf(forbidden) == null }.log()

        log("part 2: ")
        lines.count { word ->
            var ok = false
            for (i in 0 until word.length - 1) {
                val sub = word.substring(i, i + 2)
                if (word.lastIndexOf(sub) >= i + 2) {
                    ok = true
                    break
                }
            }
            if (ok) {
                ok = false
                for (i in 0 until word.length - 2) {
                    if (word[i] == word[i + 2]) {
                        ok = true
                        break
                    }
                }
            }
            ok
        }.log()
    }
}
