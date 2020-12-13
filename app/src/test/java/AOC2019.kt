import org.junit.Test
import kotlin.math.abs


class AOC2019 : BaseTest("AOC2019") {
    @Test
    fun day1() = test(2) { lines ->
        lines.map { it.toInt() / 3 - 2 }.sum().log()
        lines.map { generateSequence(it.toInt()) { (it / 3 - 2).takeIf { it > 0 } }.drop(1).sum() }.sum().log()
    }

    @Test
    fun day2() = test(1) { lines ->
        val code = lines.first().split(",").map { it.toInt() }
        code.execute(12, 2).log()
        (0..9999).find { code.execute(it / 100, it % 100) == 19690720 }.log()
    }

    fun List<Int>.execute(noun: Int, verb: Int): Int {
        val code = toMutableList()
        code[1] = noun
        code[2] = verb
        for (i in code.indices step 4) {
            when (code[i]) {
                1 -> code[code[i + 3]] = code[code[i + 1]] + code[code[i + 2]]
                2 -> code[code[i + 3]] = code[code[i + 1]] * code[code[i + 2]]
                99 -> break
                else -> error("invalid op")
            }
        }
        return code.first()
    }

    @Test
    fun day3() = test(4) { lines ->
        val wires = lines.map { wire ->
            wire.split(",").map { it.first() to it.drop(1).toInt() }.fold(listOf(0 to 0)) { acc, v ->
                acc + acc.last().let { pos ->
                    when (v.first) {
                        'R' -> (1..v.second).map { (pos.first + it) to pos.second }
                        'L' -> (1..v.second).map { (pos.first - it) to pos.second }
                        'U' -> (1..v.second).map { pos.first to (pos.second - it) }
                        'D' -> (1..v.second).map { pos.first to (pos.second + it) }
                        else -> error("invalid direction")
                    }
                }
            }
        }
        val intersections = wires.reduce { acc, list -> (acc intersect list).toList() }.minus(0 to 0).minus(0 to 0)
        intersections.map { abs(it.first) + abs(it.second) }.min().log()
        intersections.map { point -> wires.map { it.indexOf(point) }.sum() }.min().log()
    }
}
