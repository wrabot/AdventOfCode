package aoc2021

import tools.Day


class Day24 : Day(2021, 24) {
    override fun solvePart1() = "99919692496939".takeIf { test(it) == 0 } ?: ""
    override fun solvePart2() = "81914111161714".takeIf { test(it) == 0 } ?: ""

    init {
        if (test(lines, "13579246899999") != 76981240) error("invalid raw algo")
        if (test("13579246899999") != 76981240) error("invalid compiled algo")
    }

    private fun test(input: String) = test(input.map { it.toString().toInt() }.toIntArray())

    private fun test(input: IntArray): Int {
        var z = 0
        input.forEachIndexed { index, w ->
            z = step(z, w, index)
        }
        return z
    }

    /*
        w0=w7+7
        w1=w6
        w2=w3+8
        w4=w5+3
        w9=w8+5
        w11=w12+6
        w13=w10+3
    */
    private fun step(z: Int, w: Int, index: Int) = when (index) {
        0 -> step1(z, w, 10, 5) // 26^1 w0+5
        1 -> step1(z, w, 13, 9) // 26^2 (w0+5)*26+w1+9
        2 -> step1(z, w, 12, 4) // 26^3 ((w0+5)*26+w1+9)*26+w2+4
        3 -> step2(z, w, -12, 4) // 26^2 w2+4-12=w3 (w0+5)*26+w1+9
        4 -> step1(z, w, 11, 10) // 26^3 ((w0+5)*26+w1+9)*26+w4+10
        5 -> step2(z, w, -13, 14) // 26^2 w4+10-13=w5 (w0+5)*26+w1+9
        6 -> step2(z, w, -9, 14) // 26^1 w1+9-9=w6 w0+5
        7 -> step2(z, w, -12, 12) // 26^0 w0+5-12=w7 0
        8 -> step1(z, w, 14, 14) // 26^1 w8+14
        9 -> step2(z, w, -9, 14) // 26^1 w8+14-9=w9 0
        10 -> step1(z, w, 15, 5) // 26^1 w10+5
        11 -> step1(z, w, 11, 10) // 26^2 (w10+5)*26+w11+10
        12 -> step2(z, w, -16, 8) // 26^1 w11+10-16=w12 w10+5
        13 -> step2(z, w, -2, 15) // 26^0 w10+5-2=w13
        else -> error("!!!")
    }

    // normally
    // if (z % 26 + dx != w) z * 26 + (w + dy) else z
    // but dx > 10
    private fun step1(z: Int, w: Int, dx: Int, dy: Int) = z * 26 + w + dy

    private fun step2(z: Int, w: Int, dx: Int, dy: Int) = if (z % 26 + dx != w) (z / 26) * 26 + w + dy else z / 26

    private fun test(lines: List<String>, input: String): Int {
        val program = listOf("w", "x", "y", "z").map { it to 0 }.toMap().toMutableMap()
        var index = 0
        lines.map { it.split(" ") }.forEach {
            program[it[1]] = if (it[0] == "inp") {
                input[index++].toString().toInt()
            } else {
                val a = program[it[1]]!!
                val b = program.getOrElse(it[2], { it[2].toInt() })
                when (it[0]) {
                    "add" -> a + b
                    "mul" -> a * b
                    "div" -> a / b
                    "mod" -> a % b
                    "eql" -> if (a == b) 1 else 0
                    else -> error("invalid instruction")
                }
            }
        }
        return program["z"]!!
    }

    // useless but fun

    private fun Instruction.eval(input: String): Int = when (this) {
        is Instruction.Value -> value
        is Instruction.Inp -> input[index].toString().toInt()
        is Instruction.Add -> value + values.sumOf { it.eval(input) }
        is Instruction.Mul -> a.eval(input) * b.eval(input)
        is Instruction.Div -> a.eval(input) / b.eval(input)
        is Instruction.Mod -> a.eval(input) % b.eval(input)
        is Instruction.Eql -> if (a.eval(input) == b.eval(input)) 1 else 0
    }


    private fun reduce(lines: List<String>): Instruction {
        val program =
            listOf("w", "x", "y", "z").map { it to Instruction.Value(0) }.toMap<String, Instruction>().toMutableMap()
        var index = 0
        lines.map { it.split(" ") }.forEach {
            program[it[1]] = if (it[0] == "inp") {
                Instruction.Inp(index++)
            } else {
                val a = program[it[1]]!!
                val b = program.getOrElse(it[2], { Instruction.Value(it[2].toInt()) })
                when (it[0]) {
                    "add" -> add(a, b)
                    "mul" -> mul(a, b)
                    "div" -> div(a, b)
                    "mod" -> mod(a, b)
                    "eql" -> eql(a, b)
                    else -> error("invalid instruction")
                }
            }
        }
        return program["z"]!!
    }

    companion object {
        fun List<Int>.toRange() = minOf { it }..maxOf { it }
        fun div(a: Int, b: Int) = if (b == 0) Int.MAX_VALUE else a / b
    }

    sealed class Instruction(val range: IntRange) {
        data class Value(val value: Int) : Instruction(value..value)
        data class Inp(val index: Int) : Instruction(1..9)
        data class Add(val values: List<Instruction>, val value: Int = 0) :
            Instruction((value + values.sumOf { it.range.first })..(value + values.sumOf { it.range.last }))

        data class Mul(val a: Instruction, val b: Instruction) : Instruction(
            listOf(
                a.range.first * b.range.first,
                a.range.first * b.range.last,
                a.range.last * b.range.first,
                a.range.last * b.range.last
            ).toRange()
        )

        data class Div(val a: Instruction, val b: Instruction) : Instruction(
            listOf(
                div(a.range.first, b.range.first),
                div(a.range.first, b.range.last),
                div(a.range.last, b.range.first),
                div(a.range.last, b.range.last)
            ).toRange()
        )

        data class Mod(val a: Instruction, val b: Instruction) : Instruction(0..25) // trick b is always 26
        data class Eql(val a: Instruction, val b: Instruction) : Instruction(0..1)
    }

    private fun add(a: Instruction, b: Instruction) = when {
        a is Instruction.Value && b is Instruction.Value -> Instruction.Value(a.value + b.value)
        a is Instruction.Value -> add(b, a)
        b is Instruction.Value -> add(a, b)
        a is Instruction.Add && b is Instruction.Add -> Instruction.Add(a.values + b.values, a.value + b.value)
        a is Instruction.Add -> Instruction.Add(a.values + b, a.value)
        b is Instruction.Add -> Instruction.Add(b.values + a, b.value)
        else -> Instruction.Add(listOf(a, b))
    }

    private fun add(a: Instruction, b: Instruction.Value) = when {
        b.value == 0 -> a
        a is Instruction.Add -> Instruction.Add(a.values, a.value + b.value)
        else -> Instruction.Add(listOf(a), b.value)
    }

    private fun mul(a: Instruction, b: Instruction) = when {
        a is Instruction.Value && b is Instruction.Value -> Instruction.Value(a.value * b.value)
        b is Instruction.Value -> mul(a, b)
        a is Instruction.Value -> mul(b, a)
        else -> Instruction.Mul(a, b)
    }

    private fun mul(a: Instruction, b: Instruction.Value): Instruction = when {
        b.value == 0 -> Instruction.Value(0)
        b.value == 1 -> a
        a is Instruction.Mul && a.b is Instruction.Value -> Instruction.Mul(a.a, Instruction.Value(a.b.value * b.value))
        a is Instruction.Add -> Instruction.Add(a.values.map { mul(it, b) }, a.value * b.value)
        else -> Instruction.Mul(a, b)
    }

    private fun div(a: Instruction, b: Instruction.Value) = when (b.value) {
        1 -> a
        else -> Instruction.Div(a, b)
    }

    private fun div(a: Instruction, b: Instruction) = when {
        a is Instruction.Value && b is Instruction.Value -> Instruction.Value(a.value / b.value)
        b is Instruction.Value -> div(a, b)
        a is Instruction.Value -> div(b, a)
        else -> Instruction.Div(a, b)
    }

    private fun mod(a: Instruction, b: Instruction): Instruction {
        if (b !is Instruction.Value || b.value != 26) error("invalid mod") // trick b is always 26
        return when {
            a.range.last < 26 -> a
            a is Instruction.Value -> Instruction.Value(a.value % 26)
            else -> Instruction.Mod(a, b)
        }
    }

    private fun eql(a: Instruction, b: Instruction) = when {
        a is Instruction.Value && b is Instruction.Value -> Instruction.Value(if (a.value == b.value) 1 else 0)
        a.range.first !in b.range && a.range.last !in b.range && b.range.first !in a.range -> Instruction.Value(0)
        else -> Instruction.Eql(a, b)
    }
}
