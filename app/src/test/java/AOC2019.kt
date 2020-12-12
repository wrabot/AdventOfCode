import org.junit.Test


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
    fun day3() = test(1) { lines ->
    }
}
