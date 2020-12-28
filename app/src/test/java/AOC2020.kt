import org.junit.Test
import java.math.BigInteger
import java.util.*
import kotlin.math.abs
import kotlin.math.sqrt

class AOC2020 : BaseTest("AOC2020") {
    @Test
    fun day1() = test(1) { lines ->
        val numbers = lines.map { it.toInt() }.sorted()

        //part1
        numbers.product(2020)!!.log()

        //part2
        numbers.mapIndexedNotNull { index: Int, i: Int ->
            numbers.drop(index + 1).product(2020 - i)?.let { it * i }
        }.first().log()
    }

    private fun List<Int>.product(sum: Int) = find { v -> findLast { it + v == sum } != null }?.let { it * (sum - it) }

    @Test
    fun day2() = test(1) { lines ->
        val rules = lines.map { it.split("-", " ", ": ") }

        //part1
        rules.count { (min, max, letter, password) ->
            password.count { it.toString() == letter } in min.toInt()..max.toInt()
        }.log()

        //part2
        rules.count { (first, second, letter, password) ->
            listOf(first, second).count { password[it.toInt() - 1].toString() == letter } == 1
        }.log()
    }

    @Test
    fun day3() = test(1) { lines ->
        val width = lines[0].length
        val height = lines.size
        val map = lines.joinToString("")

        //part1
        map.slope(width, height, 3, 1).log()

        //part2
        listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2).map { map.slope(width, height, it.first, it.second) }
            .reduce { acc, i -> acc * i }.log()
    }

    private fun String.slope(width: Int, height: Int, dx: Int, dy: Int) =
        (0 until height step dy).count { this[it * width + (it / dy * dx) % width] == '#' }

    @Test
    fun day4() = test(1) { lines ->
        val passports = mutableListOf(mutableMapOf<String, String>())
        lines.forEach { line ->
            if (line.isBlank()) {
                passports.add(mutableMapOf())
            } else {
                line.split(" ").map { it.split(":") }.forEach { (key, value) ->
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

        //part1
        passports.count { (it.keys intersect mandatory.keys).size == 7 }.log()

        //part2
        passports.count { passport ->
            mandatory.all { it.value(passport[it.key].orEmpty()) }
        }.log()
    }

    @Test
    fun day5() = test(1) { lines ->
        val ids = lines.map {
            it.replace('F', '0')
                .replace('B', '1')
                .replace('L', '0')
                .replace('R', '1')
                .toInt(2)
        }.sorted()

        //part1
        ids.maxOrNull().log()

        //part2
        (ids.filterIndexed { index, i -> index + ids.first() != i }.first() - 1).log()
    }

    @Test
    fun day6() = test(1) { lines ->
        val groups = mutableListOf(mutableListOf<String>())
        lines.forEach {
            if (it.isBlank()) {
                groups.add(mutableListOf())
            } else {
                groups.last().add(it)
            }
        }

        //part1
        groups.map { it.reduce { acc, s -> acc + s }.toSet().count() }.sum().log()

        //part2
        groups.map { g -> g.map { it.toSet() }.reduce { acc, s -> acc intersect s }.count() }.sum().log()
    }

    @Test
    fun day7() = test(1) { lines ->
        val rules = lines.map { it.split(" bags contain ") }.map { (a, b) ->
            a to if (b.startsWith("no")) emptyList() else b.split(", ").map {
                it.split(" ").let { (n, u, v) -> n.toInt() to "$u $v" }
            }
        }.toMap()

        //part1
        val bags = mutableSetOf<String>()
        rules.keys.forEach {
            if (rules.contains(it, "shiny gold")) bags.add(it)
        }
        bags.count().log()

        //part2
        rules.count("shiny gold").log()
    }

    private fun Map<String, List<Pair<Int, String>>>.contains(container: String, name: String): Boolean =
        getValue(container).any { it.second == name || contains(it.second, name) }

    private fun Map<String, List<Pair<Int, String>>>.count(container: String): Int =
        getValue(container).map { it.first * (1 + count(it.second)) }.sum()

    @Test
    fun day8() = test(1) { lines ->
        val code = lines.map { it.split(" ") }.map { (op, arg) -> Ins(op, arg.toInt()) }

        //part1
        code.run().second.log()

        //part2
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

    private fun List<Ins>.run(): Pair<Int, Int> {
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

    @Test
    fun day9() = test(1) { lines ->
        val numbers = lines.map { it.toLong() }

        //part1
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

        //part2
        sum@ for (i in numbers.indices) {
            var sum = 0L
            for (j in i until numbers.size) {
                sum += numbers[j]
                if (sum > invalid) continue@sum
                if (sum == invalid) {
                    val block = numbers.subList(i, j)
                    val weakness = block.minOrNull()!! + block.maxOrNull()!!
                    weakness.log()
                    break@sum
                }
            }
            break
        }
    }

    @Test
    fun day10() = test(3) { lines ->
        val adapters = lines.map { it.toInt() }.sortedDescending()
        val links = adapters + 0
        val groups = links.groupBy({ it }) { v -> links.filter { it in v - 3 until v } }
            .mapValues { it.value.flatten() }

        //part1
        val list = mutableListOf(links.first())
        while (list.last() != 0) {
            list.add(groups.getValue(list.last()).first())
        }
        val diff = list.zipWithNext { a, b -> a - b }
        val result = diff.count { it == 1 } * (diff.count { it == 3 } + 1)
        result.log()

        //part2
        val counts = mutableMapOf(0 to 1L)
        adapters.reversed().forEach {
            counts[it] = groups.getValue(it).fold(0L) { acc, i -> acc + counts[i]!! }
        }
        counts[adapters.first()].log()
    }

    @Test
    fun day11() = test(2) { lines ->
        val width = lines[0].length
        val height = lines.size
        val initialMap = lines.flatMap { it.toList() }

        //part1
        var map = initialMap
        while (true) {
            map = map.step1(width, height) ?: break
        }
        map.count { it == '#' }.log()

        //part2
        map = initialMap
        while (true) {
            map = map.step2(width, height) ?: break
        }
        map.count { it == '#' }.log()
    }

    private fun List<Char>.step2(width: Int, height: Int): List<Char>? {
        var modified = false
        val map = mapIndexed { index, c ->
            val x = index % width
            val y = index / width
            when (c) {
                'L' -> if (adjacent2(width, height, x, y) == 0) '#' else 'L'
                '#' -> if (adjacent2(width, height, x, y) >= 5) 'L' else '#'
                else -> c
            }.apply { if (c != this) modified = true }
        }
        return if (modified) map else null
    }

    private fun List<Char>.adjacent2(width: Int, height: Int, x0: Int, y0: Int) = directions.count {
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

    private fun List<Char>.step1(width: Int, height: Int): List<Char>? {
        var modified = false
        val map = mapIndexed { index, c ->
            val x = index % width
            val y = index / width
            when (c) {
                'L' -> if (adjacent1(width, height, x, y) == 0) '#' else 'L'
                '#' -> if (adjacent1(width, height, x, y) >= 4) 'L' else '#'
                else -> c
            }.apply { if (c != this) modified = true }
        }
        return if (modified) map else null
    }

    private fun List<Char>.adjacent1(width: Int, height: Int, x0: Int, y0: Int) = directions.count {
        val x = x0 + it.first
        val y = y0 + it.second
        (x in 0 until width) && (y in 0 until height) && (get(y * width + x) == '#')
    }

    private val directions = listOf(1 to 0, 1 to -1, 0 to -1, -1 to -1, -1 to 0, -1 to 1, 0 to 1, 1 to 1)

    @Test
    fun day12() = test(2) { lines ->
        day12part1(lines)
        day12part2(lines)
    }

    private fun day12part1(lines: List<String>) {
        var x = 0
        var y = 0
        var dir = 0
        lines.map { Pair(it.first(), it.drop(1).toInt()) }.forEach { (op, value) ->
            when (op) {
                'R' -> dir = (dir - value + 360) % 360
                'L' -> dir = (dir + value) % 360
                'E' -> x += value
                'N' -> y -= value
                'W' -> x -= value
                'S' -> y += value
                'F' -> when (dir) {
                    0 -> x += value
                    90 -> y -= value
                    180 -> x -= value
                    270 -> y += value
                    else -> error("Should not happen")
                }
                else -> error("Should not happen")
            }
        }
        (abs(x) + abs(y)).log()
    }

    private fun day12part2(lines: List<String>) {
        var x = 0
        var y = 0
        var wx = 10
        var wy = -1
        lines.map { Pair(it.first(), it.drop(1).toInt()) }.forEach { (op, value) ->
            when (op) {
                'R' -> rotate(wx, wy, 360 - value).let {
                    wx = it.first
                    wy = it.second
                }
                'L' -> rotate(wx, wy, value).let {
                    wx = it.first
                    wy = it.second
                }
                'E' -> wx += value
                'N' -> wy -= value
                'W' -> wx -= value
                'S' -> wy += value
                'F' -> {
                    x += value * wx
                    y += value * wy
                }
                else -> error("Should not happen")
            }
        }
        (abs(x) + abs(y)).log()
    }

    private fun rotate(x: Int, y: Int, degree: Int) = when (degree) {
        90 -> y to -x
        180 -> -x to -y
        270 -> -y to x
        else -> error("Should not happen")
    }

    @Test
    fun day13() = test(2) { lines ->
        // all ids are prime !!!
        day13part1(lines)
        day13part2(lines)
    }

    private fun day13part1(lines: List<String>) {
        val min = lines[0].toInt()
        lines[1].split(",").filter { it != "x" }.map { it.toInt() }.map { it to (it - min % it) % it }
            .minByOrNull { it.second }!!.let { it.first * it.second }.log()
    }

    private fun day13part2(lines: List<String>) {
        val buses = lines[1].split(",")
            .mapIndexedNotNull { index, s -> if (s == "x") null else s.toBigInteger() to index.toBigInteger() }
        val timestamp = buses.reduce { acc, v ->
            val f = (1 until v.first.toInt()).map { it.toBigInteger() } // is always small
                .first { (it * acc.first) % v.first == BigInteger.ONE }
            val a = acc.first * v.first
            val b = acc.second - acc.first * f * (v.second + acc.second)
            a to (b % a + a) % a
        }.second.log()
        buses.all { (timestamp + it.second) % it.first == BigInteger.ZERO }.log()
    }

    @Test
    fun day14() = test(2) { lines ->
        day14part1(lines)
        day14part2(lines)
    }

    private fun day14part1(lines: List<String>) {
        var maskOff = 0L
        var maskOn = 0L
        val mem = mutableMapOf<Long, Long>()
        lines.forEach { line ->
            when {
                line.startsWith("mask") -> {
                    val mask = line.split(" = ")[1]
                    maskOff = mask.replace('X', '1').toLong(2)
                    maskOn = mask.replace('X', '0').toLong(2)
                }
                line.startsWith("mem") -> {
                    val (address, value) = line.removePrefix("mem[").split("] = ").map { it.toLong() }
                    mem[address] = ((value and maskOff) or maskOn)
                }
            }
        }
        mem.values.sum().log()
    }

    private fun day14part2(lines: List<String>) {
        var maskOff = 0L
        val maskOn = mutableListOf<Long>()
        val mem = mutableMapOf<Long, Long>()
        lines.forEach { line ->
            when {
                line.startsWith("mask") -> {
                    val mask = line.split(" = ")[1]
                    maskOff = mask.replace('0', '1').replace('X', '0').toLong(2)
                    maskOn.clear()
                    maskOn.add(mask.replace('X', '0').toLong(2))
                    mask.forEachIndexed { index, c ->
                        if (c == 'X') {
                            maskOn.addAll(maskOn.map { it or (1L shl (mask.length - 1 - index)) })
                        }
                    }
                }
                line.startsWith("mem") -> {
                    val (address, value) = line.removePrefix("mem[").split("] = ").map { it.toLong() }
                    maskOn.forEach { mem[(address and maskOff) or it] = value }
                }
            }
        }
        mem.values.sum().log()
    }

    @Test
    fun day15() = test(2) { lines ->
        lines.map { it.split(",").toInts() }.run {
            //part1
            forEach { it.memory(2020).log() }
            //part2
            forEach { it.memory(30000000).log() }
        }
    }

    private fun List<Int>.memory(turns: Int): Int {
        val numbers = mapIndexed { index, n -> n to index + 1 }.toMap().toMutableMap()
        var last = last()
        numbers.remove(last)
        (size until turns).forEach {
            val index = numbers[last]
            numbers[last] = it
            last = if (index == null) 0 else it - index
        }
        return last
    }

    @Test
    fun day16() = test(2) { lines ->
        val rules = lines.takeWhile { it.isNotBlank() }.map { it.split(": ") }.map { (name, rule) ->
            name to rule.split(" or ").map { it.split("-").toInts() }.map { (first, last) -> first..last }
        }.toMap()
        val myTicket = lines[2 + rules.size].split(",").toInts().log()
        val otherTickets = lines.takeLastWhile { it.isNotBlank() }.drop(1).map { it.split(",").toInts() }

        // part 1
        val validRules = rules.values.flatten()
        otherTickets.flatten().filter { value -> validRules.none { value in it } }.sum().log()

        // part2
        val validTickets = otherTickets.filter { ticket -> ticket.none { value -> validRules.none { value in it } } }
        val othersValues = myTicket.indices.map { index -> validTickets.map { it[index] } }
        val validIndices = rules.map { rule ->
            rule.key to othersValues.indices.filter { index -> othersValues[index].all { value -> rule.value.any { value in it } } }.toMutableList()
        }.toMap()
        while (true) {
            val singles = validIndices.filterValues { it.size == 1 }
            if (singles.size == validIndices.size) break
            val singlesValues = singles.values.flatten()
            validIndices.forEach { if (it.value.size > 1) it.value.removeAll(singlesValues) }
        }
        validIndices.filter { it.key.startsWith("departure") }.log().map { myTicket[it.value.first()].toLong() }.log().reduce { acc, s -> acc * s }.log()
    }

    @Test
    fun day17() = test(2) { lines ->
        val initialCells = lines.mapIndexed { y, s -> s.mapIndexedNotNull { x, c -> if (c == '#') Cell(x, y, 0, 0) else null } }.flatten()

        //part 1
        repeat(6, initialCells) { it.cycle(false) }.count().log()

        //part 2
        repeat(6, initialCells) { it.cycle(true) }.count().log()
    }

    data class Cell(val x: Int, val y: Int, val z: Int, val w: Int)

    private fun List<Cell>.cycle(hyper: Boolean): List<Cell> {
        val cells = mutableListOf<Cell>()
        for (x in range(Cell::x)) {
            for (y in range(Cell::y)) {
                for (z in range(Cell::z)) {
                    for (w in if (hyper) range(Cell::w) else 0..0) {
                        val cell = Cell(x, y, z, w)
                        when (neighbors(cell, hyper)) {
                            2 -> if (cell in this) cells.add(cell)
                            3 -> cells.add(cell)
                        }
                    }
                }
            }
        }
        return cells
    }

    private fun List<Cell>.range(block: Cell.() -> Int) = map(block).run { (minOrNull()!! - 1)..(maxOrNull()!! + 1) }

    private fun List<Cell>.neighbors(cell: Cell, hyper: Boolean): Int {
        var count = 0
        for (x in -1..1) {
            for (y in -1..1) {
                for (z in -1..1) {
                    for (w in if (hyper) -1..1 else 0..0) {
                        if (x != 0 || y != 0 || z != 0 || w != 0) {
                            if (Cell(cell.x + x, cell.y + y, cell.z + z, cell.w + w) in this) count++
                        }
                    }
                }
            }
        }
        return count
    }

    @Test
    fun day18() = test(2) { lines ->
        val list = lines.map { it.replace(" ", "") }
        list.map { Expr(it, false).eval() }.sum().log() //part1
        list.map { Expr(it, true).eval() }.sum().log() //part2
    }

    data class Expr(val text: String, val part2: Boolean) {
        fun eval(): BigInteger {
            val stack = mutableListOf(operand())
            while (index < text.length) {
                when (text[index++]) {
                    ')' -> break
                    '*' -> if (part2) stack.add(operand()) else stack[stack.lastIndex] *= operand()
                    '+' -> stack[stack.lastIndex] += operand()
                    else -> error("invalid operator")
                }
            }
            return stack.reduce { acc, bi -> acc * bi }
        }

        private var index: Int = 0
        private fun operand() = if (text[index++] == '(') eval() else text[index - 1].toString().toBigInteger()
    }

    @Test
    fun day19() = test(2) { lines ->
        val rules = lines.takeWhile { it.isNotBlank() }.map { it.split(": ") }.map { (id, rule) ->
            id to rule.replace("\"", "").split(" | ").map { it.split(" ") }
        }.toMap()
        val messages = lines.takeLastWhile { it.isNotBlank() }

        //part1
        val regex0 = rules.createRegex("0").toRegex()
        messages.count { it.matches(regex0) }.log()

        //part2
        // rule 8 and 11 only used by rule 0
        // 0: 8 11
        // 8: 42 | 8
        // 11: 42 11 31
        // the rule 0 can be transform as 0: 42 8 31 | 42 0 31
        val regex42 = rules.createRegex("42")
        val regex31 = rules.createRegex("31")
        val regex8 = "$regex42+".toRegex()
        val regex0Recursive = "$regex42(.*)$regex31".toRegex()
        messages.count { regex0Recursive.matches(it) && regex8.matches(it.recursiveMatches(regex0Recursive)) }.log()
    }

    private fun CharSequence.recursiveMatches(regex: Regex): CharSequence =
        regex.matchEntire(this)?.groupValues?.getOrNull(1)?.recursiveMatches(regex) ?: this

    private fun Map<String, List<List<String>>>.createRegex(id: String): String =
        this[id]?.joinToString("|", "(?:", ")") { rule -> rule.joinToString("") { createRegex(it) } } ?: id

    @Test
    fun day20() = test(2) { lines ->
        val chunks = lines.chunked(12)
        val size = sqrt(chunks.size.toFloat()).toInt()
        val blocks = chunks.flatMap { data ->
            val id = data[0].split(" ", ":")[1].toLong()
            data.drop(1).dropLast(1).allOrientations().map {
                val flipD = it.flipD()
                Block(id, it, it.first(), it.last(), flipD.first(), flipD.last())
            }
        }
        val links = blocks.map { block ->
            block to listOf(
                blocks.find { it.id != block.id && it.bottom == block.top },
                blocks.find { it.id != block.id && it.top == block.bottom },
                blocks.find { it.id != block.id && it.right == block.left },
                blocks.find { it.id != block.id && it.left == block.right }
            )
        }.toMap()

        // check input: borders are unique !!!
        (links.count { it.value.countNotNull() == 2 } + links.count { it.value.countNotNull() == 3 } + links.count { it.value.countNotNull() == 4 } == blocks.size).log()

        //part1
        val corners = links.filter { it.value.countNotNull() == 2 }
        corners.map { it.key.id }.distinct().reduce { acc, i -> acc * i }.log()

        //part2

        // creates block image
        val blockImage = mutableListOf<Block>()
        blockImage.add(corners.filter { it.value[1] != null && it.value[3] != null }.keys.first())
        (0 until size - 1).forEach { blockImage.add(links.getValue(blockImage[it])[3]!!) }
        for (i in 0 until size * size - size) {
            blockImage.add(links.getValue(blockImage[i])[1]!!)
        }

        val blockIndices = 0..9

        /*
        val debugImage = blockImage.chunked(size).joinToString("\n\n") { row ->
            blockIndices.joinToString("\n") { index ->
                row.joinToString(" ") { it.image[index] }
            }
        }
        println(debugImage)
        println("-----------------")
        */

        val image = blockImage.chunked(size).flatMap { row ->
            blockIndices.map { index ->
                row.joinToString("") { it.image[index].drop(1).dropLast(1) }
            }.drop(1).dropLast(1)
        }
        //println(image.joinToString("\n"))

        val imageWeight = image.map { line -> line.count { it == '#' } }.sum()
        val pattern = listOf("                  #", "#    ##    ##    ###", " #  #  #  #  #  #")
        val patternWeight = pattern.map { line -> line.count { it == '#' } }.sum()
        val patternWidth = pattern.maxOf { it.length }
        var count = 0
        image.allOrientations().forEach { i ->
            for (y in 0 until image.size - pattern.size) {
                for (x in 0 until image.size - patternWidth) {
                    if (pattern[0].match(i[y], x) && pattern[1].match(i[y + 1], x) && pattern[2].match(i[y + 2], x)) count++
                }
            }
        }
        //count.log()
        (imageWeight - count * patternWeight).log()
    }

    data class Block(val id: Long, val image: List<String>, val top: String, val bottom: String, val left: String, val right: String)

    private fun List<Block?>.countNotNull() = count { it != null }
    private fun List<String>.allOrientations() = listOf(this, flipD(), flipV(), flipD().flipV(), flipH(), flipD().flipH(), flipV().flipH(), flipD().flipV().flipH())
    private fun String.match(s: String, start: Int) = zip(s.substring(start)).all { it.first == ' ' || it.first == it.second }
    private fun List<String>.flipD() = indices.map { index -> joinToString("") { it[index].toString() } }
    private fun List<String>.flipH() = map { it.reversed() }
    private fun List<String>.flipV() = reversed()

    @Test
    fun day21() = test(2) { lines ->
        val foods = lines.map { it.removeSuffix(")").split(" (contains ") }.map { (ingredients, allergens) ->
            ingredients.split(" ") to allergens.split(", ")
        }
        val allergens = foods.flatMap { it.second }.distinct()
        val allergensMap = allergens.map { allergen ->
            allergen to foods.filter { allergen in it.second }.map { it.first }.reduce { acc, list -> (acc intersect list).toList() }.toMutableList()
        }.toMap()
        while (true) {
            var modified = false
            allergensMap.values.mapNotNull { it.singleOrNull() }.forEach { found ->
                allergensMap.forEach { if (it.value.size != 1) modified = modified || it.value.remove(found) }
            }
            if (!modified) break
        }
        val foodWithAllergens = allergensMap.toSortedMap().flatMap { it.value } // sorted for part2

        //part1
        foods.map { it.first.minus(foodWithAllergens).size }.sum().log()

        //part2
        foodWithAllergens.joinToString(",").log()
    }

    @Test
    fun day22() = test(2) { lines ->
        val deck1 = lines.takeWhile { it.isNotBlank() }.drop(1).map { it.toInt() }.toMutableList()
        val deck2 = lines.takeLastWhile { it.isNotBlank() }.drop(1).map { it.toInt() }.toMutableList()
        day22part1(deck1.toMutableList(), deck2.toMutableList()).log()
        day22part2(deck1.toMutableList(), deck2.toMutableList(), 1).log()
    }

    private fun day22part1(deck1: MutableList<Int>, deck2: MutableList<Int>): Pair<Boolean, Int> {
        while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
            val card1 = deck1.removeAt(0)
            val card2 = deck2.removeAt(0)
            if (card1 > card2) {
                deck1 += card1
                deck1 += card2
            } else {
                deck2 += card2
                deck2 += card1
            }
        }
        return score(deck1, deck2)
    }

    private fun day22part2(deck1: MutableList<Int>, deck2: MutableList<Int>, level: Int): Pair<Boolean, Int> {
        //log("$level $deck1 $deck2")
        val deck1s = mutableListOf<List<Int>>()
        val deck2s = mutableListOf<List<Int>>()
        while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
            if (deck1 in deck1s && deck2 in deck2s) return Pair(true, 0)
            deck1s.add(deck1.toList())
            deck2s.add(deck2.toList())
            val card1 = deck1.removeAt(0)
            val card2 = deck2.removeAt(0)
            //log("$level $card1 $card2")
            val player1Wins = if (card1 <= deck1.size && card2 <= deck2.size) day22part2(deck1.take(card1).toMutableList(), deck2.take(card2).toMutableList(), level + 1).first else card1 > card2
            if (player1Wins) {
                deck1 += card1
                deck1 += card2
            } else {
                deck2 += card2
                deck2 += card1
            }
            //log("$level $deck1 $deck2")
        }
        return score(deck1, deck2)
    }

    private fun score(deck1: List<Int>, deck2: List<Int>): Pair<Boolean, Int> {
        val player1Wins = deck1.isNotEmpty()
        return player1Wins to (if (player1Wins) deck1 else deck2).reversed().mapIndexed { index, card -> (index + 1) * card }.sum()
    }

    @Test
    fun day23() = test(1) { lines ->
        val cups = lines[1].map { it.toString().toInt() }
        day23part1(cups)
        day23part2(cups)
    }

    private fun day23part1(firstCups: List<Int>) {
        val cups = firstCups.toMutableList()
        repeat(100) {
            val triple = listOf(cups.removeAt(1), cups.removeAt(1), cups.removeAt(1))
            var destination = cups[0]
            do {
                if (destination == 1) destination = 9 else destination--
            } while (destination in triple)
            cups.addAll(cups.indexOf(destination) + 1, triple)
            cups.add(cups.removeFirst())
        }
        Collections.rotate(cups, -cups.indexOf(1))
        cups.drop(1).joinToString("").log()
    }

    private fun day23part2(firstCups: List<Int>) {
        val cups = List(1000000) { Cup(if (it < firstCups.size) firstCups[it] else it + 1) }
        cups.forEachIndexed { index, cup -> cup.next = cups[(index + 1) % cups.size] }
        var current = cups.first()

        repeat(10000000) {
            val first = current.next
            val second = first.next
            val third = second.next

            var destinationValue = current.value
            do {
                if (destinationValue == 1) destinationValue = cups.size else destinationValue--
            } while (destinationValue == first.value || destinationValue == second.value || destinationValue == third.value)
            val destinationCup = if (destinationValue <= firstCups.size) cups.find { it.value == destinationValue }!! else cups[destinationValue - 1]

            current.next = third.next // remove first to third
            third.next = destinationCup.next.apply { destinationCup.next = first } // insert first to third after destination
            current = current.next // next
        }

        cups.find { it.value == 1 }!!.run { next.value * next.next.value }.log()
    }

    data class Cup(val value: Int) {
        lateinit var next: Cup
    }

    @Test
    fun day24() = test(3) { lines ->
        //part1
        val blacks = mutableSetOf<Pair<Int, Int>>()
        lines.forEach { it.goToTile(Pair(0, 0)).run { if (!blacks.remove(this)) blacks.add(this) } }
        blacks.size.log()

        //part2
        repeat(100) {
            val blacksNeighbors = blacks.map { it to neighbors(it).partition(blacks::contains) }
            blacks.addAll(blacksNeighbors.flatMap { it.second.second }.filter { neighbors(it).count(blacks::contains) == 2 })
            blacks.removeAll(blacksNeighbors.filter { it.second.first.size !in 1..2 }.map { it.first })
        }
        blacks.size.log()
    }

    private fun neighbors(start: Pair<Int, Int>) = listOf("e", "w", "ne", "nw", "se", "sw").map { it.goToTile(start) }
    private fun String.goToTile(start: Pair<Int, Int>): Pair<Int, Int> {
        var x = start.first
        var y = start.second
        var previous: Char? = null
        forEach {
            when (it) {
                'n' -> y--
                's' -> y++
                'e' -> if (previous != 's') x++
                'w' -> if (previous != 'n') x--
            }
            previous = it
        }
        return Pair(x, y)
    }

    @Test
    fun day25() = test(2) { lines ->
        val card = lines[0].toInt()
        val door = lines[1].toInt()

        var count = 0
        var value = 1
        while (true) {
            value = next(value, 7)
            count++
            when (value) {
                card -> repeat(count, 1) { next(it, door) }.log()
                door -> repeat(count, 1) { next(it, card) }.log()
                else -> continue
            }
            break
        }
    }

    private fun next(value: Int, subject: Int) = ((value.toLong() * subject) % 20201227).toInt()
}
