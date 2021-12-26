import org.junit.Test
import java.security.MessageDigest
import java.util.*


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

    @Test
    fun day6() = test(1) { lines ->
        val orders = lines.map { it.removePrefix("turn ").split(" ") }.map { (type, tl, _, br) ->
            LightOrder(LightOrder.Type.valueOf(type), tl.toIntPair(), br.toIntPair())
        }
        val size = 1000

        //part1
        val binaryLights = BitSet(size * size)
        orders.forEach {
            for (i in it.tl.first..it.br.first) {
                for (j in it.tl.second..it.br.second) {
                    val index = i * size + j
                    when (it.type) {
                        LightOrder.Type.toggle -> binaryLights.flip(index)
                        LightOrder.Type.on -> binaryLights.set(index)
                        LightOrder.Type.off -> binaryLights.clear(index)
                    }
                }
            }
        }
        binaryLights.cardinality().log()

        //part2
        val lights = Array<Int>(size * size) { 0 }
        orders.forEach {
            for (i in it.tl.first..it.br.first) {
                for (j in it.tl.second..it.br.second) {
                    val index = i * size + j
                    when (it.type) {
                        LightOrder.Type.toggle -> lights[index] += 2
                        LightOrder.Type.on -> lights[index]++
                        LightOrder.Type.off -> if (lights[index] > 0) lights[index]--
                    }
                }
            }
        }
        lights.sum().log()
    }

    private fun String.toIntPair() = split(",").toInts().zipWithNext().first()

    data class LightOrder(val type: Type, val tl: Pair<Int, Int>, val br: Pair<Int, Int>) {
        enum class Type { toggle, on, off }
    }

    @Test
    fun day7() = test(1) { lines ->
        val circuit = Circuit()
        lines.map { it.split(" -> ") }.forEach { (input, output) ->
            circuit.elements[output] = input.split(" ").let { operation ->
                Circuit.Element(when (operation.size) {
                    1 -> { -> circuit.operand(operation[0]) } // 123
                    2 -> { -> circuit.operand(operation[1]).inv() } // not a -> b
                    3 -> when (operation[1]) {
                        "AND" -> { -> (circuit.operand(operation[0]) and circuit.operand(operation[2])) }
                        "OR" -> { -> (circuit.operand(operation[0]) or circuit.operand(operation[2])) }
                        "LSHIFT" -> { -> (circuit.operand(operation[0]) shl circuit.operand(operation[2])) }
                        "RSHIFT" -> { -> (circuit.operand(operation[0]) shr circuit.operand(operation[2])) }
                        else -> error("invalid operation")
                    }
                    else -> error("invalid size")
                })
            }
        }
        val a = circuit.operand("a").log()
        circuit.elements.forEach { it.value.cache = null }
        circuit.elements.getValue("b").cache = a
        circuit.operand("a").log()
    }


    class Circuit {
        data class Element(val eval: () -> Int) {
            var cache: Int? = null
            val value get() = cache ?: eval().apply { cache = this and 0xFFFF }
        }

        val elements = mutableMapOf<String, Element>()
        fun operand(input: String) = input.toIntOrNull() ?: elements.getValue(input).value
    }

    @Test
    fun day8() = test(2) { lines ->
        val hexa = "\\\\x(..)".toRegex()
        (lines.sumOf { it.length } - lines.map {
            it.substring(1, it.length - 1).replace("\\\"", "\"").split("\\\\").joinToString("\\") {
                it.replace(hexa) {
                    it.groupValues[1].toInt(16).toChar().toString()
                }
            }
        }.sumOf { it.length }).log()
        (lines.map { it.replace("\\", "\\\\").replace("\"", "\\\"") }.sumOf { it.length + 2 } - lines.sumOf { it.length }).log()
    }

    @Test
    fun day9() = test(2) { lines ->
        lines.map { it.split(" to ", " = ") }.forEach { (a, b, d) ->
            cities.add(a)
            cities.add(b)
            d.toInt().run {
                distances[listOf(a, b)] = this
                distances[listOf(b, a)] = this
            }
        }
        distances.log()
    }

    private val distances = mutableMapOf<List<String>, Int>()
    private val cities = mutableSetOf<String>()
}
