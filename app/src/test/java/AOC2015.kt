import org.junit.Test
import java.security.MessageDigest


class AOC2015 : BaseTest("AOC2015") {
    @Test
    fun day1() = test(1) { lines ->
        lines[0].fold(0) { acc, c -> if (c == '(') acc + 1 else acc - 1 }.log()
        lines[0].runningFold(0) { acc, c -> if (c == '(') acc + 1 else acc - 1 }.indexOfFirst { it < 0 }.log()
    }

    @Test
    fun day2() = test(1) { lines ->
        val boxes = lines.map { box -> box.split("x").map { it.toInt() } }
        boxes.map { (l, w, h) -> listOf(l * w, w * h, h * l).run { 2 * sum() + minOrNull()!! } }.sum().log()
        boxes.map { it.sorted().take(2).sum() * 2 + it.reduce { acc, i -> acc * i } }.sum().log()
    }

    @Test
    fun day3() = test(1) { lines ->
        lines[0].day3part1()
        lines[0].day3part2()
    }

    private fun String.day3part1() {
        var x = 0
        var y = 0
        val houses = mutableSetOf(Pair(x, y))
        forEach {
            when (it) {
                '<' -> x--
                '>' -> x++
                '^' -> y--
                'v' -> y++
                else -> error("invalid direction")
            }
            houses.add(Pair(x, y))
        }
        houses.count().log()
    }

    private fun String.day3part2() {
        val x = mutableListOf(0, 0)
        val y = mutableListOf(0, 0)
        val houses = mutableSetOf(Pair(0, 0))
        forEachIndexed { index, c ->
            val i = index % 2
            when (c) {
                '<' -> x[i]--
                '>' -> x[i]++
                '^' -> y[i]--
                'v' -> y[i]++
                else -> error("invalid direction")
            }
            houses.add(Pair(x[i], y[i]))
        }
        houses.count().log()
    }

    @Test
    fun day4() = test(1) { lines ->
        val md5 = MessageDigest.getInstance("MD5")
        lines.forEach { key ->
            var n = 0
            while (true) {
                val (a, b, c) = md5.digest((key + n).toByteArray())
                if (a.toInt() == 0 && b.toInt() == 0 && c.toInt() and 0xf0 == 0) {
                    n.log()
                    break
                }
                n++
            }
            while (true) {
                val (a, b, c) = md5.digest((key + n).toByteArray())
                if (a.toInt() == 0 && b.toInt() == 0 && c.toInt() == 0) {
                    n.log()
                    break
                }
                n++
            }
        }
    }

    @Test
    fun day5() = test(2) { lines ->
        val required = List(26) { ('a' + it).toString().repeat(2) }
        val forbidden = listOf("ab", "cd", "pq", "xy")
        lines.count { line -> line.count { it in "aeiou" } >= 3 && line.findAnyOf(required) != null && line.findAnyOf(forbidden) == null }.log()
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
