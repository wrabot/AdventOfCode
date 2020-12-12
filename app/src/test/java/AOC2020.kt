import org.junit.Test

class AOC2020 {
    val root = "AOC2020"

    @Test
    fun test1() = test("$root/test1", 1, ::p1)

    @Test
    fun test2() = test("$root/test2", 1, ::p2)

    @Test
    fun test3() = test("$root/test3", 1, ::p3)

    @Test
    fun test4() = test("$root/test4", 1, ::p4)

    @Test
    fun test5() = test("$root/test5", 1, ::p5)

    @Test
    fun test6() = test("$root/test6", 1, ::p6)

    @Test
    fun test7() = test("$root/test7", 1, ::p7)

    @Test
    fun test8() = test("$root/test8", 1, ::p8)

    @Test
    fun test9() = test("$root/test9", 1, ::p9)

    @Test
    fun test10() = test("$root/test10", 3, ::p10)

    @Test
    fun test11() = test("$root/test11", 2, ::p11)

    @Test
    fun test12() = test("$root/test12", 1, ::p12)

    fun p1(lines: List<String>) {
        val numbers = lines.map { it.toInt() }.sorted()
        numbers.product(2020)!!.log()
        numbers.mapIndexedNotNull { index: Int, i: Int -> numbers.drop(index + 1).product(2020 - i)?.let { it * i } }.first().log()
    }

    fun List<Int>.product(sum: Int) = find { v -> findLast { it + v == sum } != null }?.let { it * (sum - it) }

    fun p2(lines: List<String>) {
        lines.map { it.split("-", " ", ": ") }.count { (min, max, letter, password) ->
            password.count { it.toString() == letter } in min.toInt()..max.toInt()
        }.log()
        lines.map { it.split("-", " ", ": ") }.count { (first, second, letter, password) ->
            listOf(first, second).count { password[it.toInt() - 1].toString() == letter } == 1
        }.log()
    }

    fun p3(lines: List<String>) {
        val width = lines[0].length
        val height = lines.size
        val map = lines.joinToString("")
        map.slope(width, height, 3, 1).log()
        listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2).map { map.slope(width, height, it.first, it.second) }.reduce { acc, i -> acc * i }.log()
    }

    fun String.slope(width: Int, height: Int, dx: Int, dy: Int) =
        (0 until height step dy).count { this[it * width + (it / dy * dx) % width] == '#' }

    fun p4(lines: List<String>) {
        val passports = mutableListOf(mutableMapOf<String, String>())
        lines.forEach {
            if (it.isBlank()) {
                passports.add(mutableMapOf())
            } else {
                it.split(" ").map { it.split(":") }.forEach { (key, value) ->
                    passports.last()[key] = value
                }
            }
        }
        val mandatory = mapOf<String, (String) -> Boolean>(
            "byr" to { (it.toIntOrNull() ?: 0) in 1920..2002 },
            "iyr" to { (it.toIntOrNull() ?: 0) in 2010..2020 },
            "eyr" to { (it.toIntOrNull() ?: 0) in 2020..2030 },
            "hgt" to {
                when {
                    it.endsWith("cm") -> it.removeSuffix("cm").toInt() in 150..193
                    it.endsWith("in") -> it.removeSuffix("in").toInt() in 59..76
                    else -> false
                }
            },
            "hcl" to { it.matches("#[0-9a-f]{6}".toRegex()) },
            "ecl" to { it in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") },
            "pid" to { it.matches("[0-9]{9}".toRegex()) }
        )
        passports.count { (it.keys intersect mandatory.keys).size == 7 }.log()
        passports.count { passport ->
            mandatory.all { it.value(passport[it.key].orEmpty()) }
        }.log()
    }

    fun p5(lines: List<String>) {
        val ids = lines.map {
            it.replace('F', '0')
                .replace('B', '1')
                .replace('L', '0')
                .replace('R', '1')
                .toInt(2)
        }.sorted()
        ids.max().log()
        (ids.filterIndexed { index, i -> index + ids.first() != i }.first() - 1).log()
    }

    fun p6(lines: List<String>) {
        val groups = mutableListOf(mutableListOf<String>())
        lines.forEach {
            if (it.isBlank()) {
                groups.add(mutableListOf())
            } else {
                groups.last().add(it)
            }
        }
        groups.map { it.reduce { acc, s -> acc + s }.toSet().count() }.sum().log()
        groups.map { it.map { it.toSet() }.reduce { acc, s -> acc intersect s }.count() }.sum().log()
    }

    fun p7(lines: List<String>) {
        val rules = lines.map { it.split(" bags contain ") }.map { (a, b) ->
            a to if (b.startsWith("no")) emptyList() else b.split(", ").map {
                it.split(" ").let { (n, u, v) -> n.toInt() to "$u $v" }
            }
        }.toMap()
        val bags = mutableSetOf<String>()
        rules.keys.forEach {
            if (rules.contains(it, "shiny gold")) bags.add(it)
        }
        bags.count().log()
        rules.count("shiny gold").log()
    }

    fun Map<String, List<Pair<Int, String>>>.contains(container: String, name: String): Boolean =
        getValue(container).any { it.second == name || contains(it.second, name) }

    fun Map<String, List<Pair<Int, String>>>.count(container: String): Int =
        getValue(container).map { it.first * (1 + count(it.second)) }.sum()

    fun p8(lines: List<String>) {
        val code = lines.map { it.split(" ") }.map { (op, arg) -> Ins(op, arg.toInt()) }
        code.run().second.log()
        for (fixIndex in code.indices) {
            val original = code[fixIndex].op
            val fixOp = when (original) {
                "nop" -> "jmp"
                "jmp" -> "nop"
                else -> null
            }
            if (fixOp != null) {
                code[fixIndex].op = fixOp
                val result = code.run()
                if (result.first == code.size) {
                    result.second.log()
                    break
                }
                code[fixIndex].op = original
            }
        }
    }

    fun List<Ins>.run(): Pair<Int, Int> {
        forEach { it.used = false }
        var acc = 0
        var index = 0
        while (index < size && !this[index].used) {
            this[index].used = true
            when (this[index].op) {
                "jmp" -> index += this[index].arg
                "acc" -> acc += this[index++].arg
                else -> index++
            }
        }
        return index to acc
    }

    data class Ins(var op: String, val arg: Int, var used: Boolean = false)

    fun p9(lines: List<String>) {
        val numbers = lines.map { it.toLong() }
        var invalid = 0L
        find@ for (i in 25 until numbers.size) {
            for (first in 1..25) {
                for (second in (first + 1)..25) {
                    if (numbers[i] == numbers[i - first] + numbers[i - second]) continue@find
                }
            }
            invalid = numbers[i]
            break
        }
        invalid.log()
        sum@ for (i in numbers.indices) {
            var sum = 0L
            for (j in i until numbers.size) {
                sum += numbers[j]
                if (sum > invalid) continue@sum
                if (sum == invalid) {
                    val block = numbers.subList(i, j)
                    val weakness = block.min()!! + block.max()!!
                    weakness.log()
                    break@sum
                }
            }
            break
        }
    }

    fun p10(lines: List<String>) {
        val adapters = lines.map { it.toInt() }.sortedDescending()
        val links = adapters + 0
        val groups = links.groupBy({ it }) { v -> links.filter { it in v - 3..v - 1 } }
            .mapValues { it.value.flatten() }
        val list = mutableListOf(links.first())
        while (list.last() != 0) {
            list.add(groups[list.last()]!!.first())
        }
        val diff = list.zipWithNext { a, b -> a - b }
        val result = diff.count { it == 1 } * (diff.count { it == 3 } + 1)
        result.log()
        val counts = mutableMapOf(0 to 1L)
        adapters.reversed().forEach {
            counts[it] = groups.getValue(it).fold(0L) { acc, i -> acc + counts[i]!! }
        }
        counts[adapters.first()].log()
    }

    fun p11(lines: List<String>) {
        val width = lines[0].length
        val height = lines.size

        var map = lines.flatMap { it.toList() }
        while (true) {
            map = map.step1(width, height) ?: break
        }
        map.count { it == '#' }.log()

        map = lines.flatMap { it.toList() }
        while (true) {
            map = map.step2(width, height) ?: break
        }
        map.count { it == '#' }.log()
    }

    fun List<Char>.step2(width: Int, height: Int): List<Char>? {
        var modified = false
        val map = mapIndexed { index, c ->
            val x = index % width
            val y = index / width
            when (c) {
                'L' -> if (adjacents2(width, height, x, y) == 0) '#' else 'L'
                '#' -> if (adjacents2(width, height, x, y) >= 5) 'L' else '#'
                else -> c
            }.apply { if (c != this) modified = true }
        }
        return if (modified) map else null
    }

    fun List<Char>.adjacents2(width: Int, height: Int, x0: Int, y0: Int) = directions.count {
        var d = 1
        var occupied: Boolean? = null
        while (occupied == null) {
            val x = x0 + it.first * d
            val y = y0 + it.second * d
            occupied = if ((x in 0 until width) && (y in 0 until height)) {
                when (get(y * width + x)) {
                    '#' -> true
                    'L' -> false
                    else -> null
                }
            } else false
            d++
        }
        occupied
    }

    fun List<Char>.step1(width: Int, height: Int): List<Char>? {
        var modified = false
        val map = mapIndexed { index, c ->
            val x = index % width
            val y = index / width
            when (c) {
                'L' -> if (adjacents1(width, height, x, y) == 0) '#' else 'L'
                '#' -> if (adjacents1(width, height, x, y) >= 4) 'L' else '#'
                else -> c
            }.apply { if (c != this) modified = true }
        }
        return if (modified) map else null
    }

    fun List<Char>.adjacents1(width: Int, height: Int, x0: Int, y0: Int) = directions.count {
        val x = x0 + it.first
        val y = y0 + it.second
        (x in 0 until width) && (y in 0 until height) && (get(y * width + x) == '#')
    }

    val directions = listOf(1 to 0, 1 to -1, 0 to -1, -1 to -1, -1 to 0, -1 to 1, 0 to 1, 1 to 1)

    fun List<Char>.log(width: Int) = apply { println(chunked(width) { it.joinToString("") }.joinToString("\n")) }

    fun p12(lines: List<String>) {
    }
}
