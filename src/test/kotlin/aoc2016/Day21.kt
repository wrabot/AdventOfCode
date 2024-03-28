package aoc2016

import Day
import tools.match

class Day21(test: Int? = null) : Day(test) {
    override fun solvePart1() = instructions.scramble("abcdefgh")
    override fun solvePart2() = instructions.unscramble("fbgdceah")

    private fun List<Instruction>.scramble(password: String): String {
        val sb = StringBuilder(password)
        forEach { instruction ->
            when (instruction) {
                is MovePosition -> sb.move(instruction.s, instruction.d)
                is ReversePositions -> sb.reverse(instruction.r)
                is Rotate -> sb.rotate(instruction.a)
                is RotateLetter -> sb.rotate(-(1 + sb.indexOf(instruction.a).let { it + if (it >= 4) 1 else 0 }))
                is SwapLetter -> sb.swap(sb.indexOf(instruction.a), sb.indexOf(instruction.b))
                is SwapPosition -> sb.swap(instruction.a, instruction.b)
            }
        }
        return sb.toString()
    }

    // reverse are all obvious except rotate letter
    // origin -> destination (rotation)
    // 0 -> 1 (1)
    // 1 -> 3 (2)
    // 2 -> 5 (3)
    // 3 -> 7 (4)
    // 4 -> 2 (6)
    // 5 -> 4 (7)
    // 6 -> 6 (8)
    // 7 -> 0 (9)
    private fun List<Instruction>.unscramble(scrambled: String): String {
        val sb = StringBuilder(scrambled)
        val rotateLetter = mapOf(1 to 1, 3 to 2, 5 to 3, 7 to 4, 2 to 6, 4 to 7, 6 to 8, 0 to 9)
        reversed().forEach { instruction ->
            when (instruction) {
                is MovePosition -> sb.move(instruction.d, instruction.s)
                is ReversePositions -> sb.reverse(instruction.r)
                is Rotate -> sb.rotate(-instruction.a)
                is RotateLetter -> sb.rotate(rotateLetter[sb.indexOf(instruction.a)]!!)
                is SwapLetter -> sb.swap(sb.indexOf(instruction.a), sb.indexOf(instruction.b))
                is SwapPosition -> sb.swap(instruction.a, instruction.b)
            }
        }
        return sb.toString()
    }

    private fun StringBuilder.rotate(r: Int) = r.mod(length).let { append(substring(0, it)).deleteRange(0, it) }
    private fun StringBuilder.swap(a: Int, b: Int) = apply { set(a, get(b).also { set(b, get(a)) }) }
    private fun StringBuilder.reverse(r: IntRange) = replace(r.first, r.last + 1, substring(r).reversed())
    private fun StringBuilder.move(s: Int, d: Int) = insert(d, get(s).apply { deleteCharAt(s) })

    sealed interface Instruction
    private data class SwapPosition(val a: Int, val b: Int) : Instruction
    private data class SwapLetter(val a: Char, val b: Char) : Instruction
    private data class Rotate(val a: Int) : Instruction
    private data class RotateLetter(val a: Char) : Instruction
    private data class ReversePositions(val r: IntRange) : Instruction
    private data class MovePosition(val s: Int, val d: Int) : Instruction

    private val instructions = lines.map { line ->
        line.match("swap position (\\d+) with position (\\d+)".toRegex())?.let {
            SwapPosition(it[0].toInt(), it[1].toInt())
        } ?: line.match("swap letter (\\w) with letter (\\w)".toRegex())?.let {
            SwapLetter(it[0].single(), it[1].single())
        } ?: line.match("rotate right (\\d+) steps?".toRegex())?.let {
            Rotate(-it[0].toInt())
        } ?: line.match("rotate left (\\d+) steps?".toRegex())?.let {
            Rotate(it[0].toInt())
        } ?: line.match("rotate based on position of letter (\\w)".toRegex())?.let {
            RotateLetter(it[0].single())
        } ?: line.match("reverse positions (\\d+) through (\\d+)".toRegex())?.let {
            ReversePositions(it[0].toInt()..it[1].toInt())
        } ?: line.match("move position (\\d+) to position (\\d+)".toRegex())?.let {
            MovePosition(it[0].toInt(), it[1].toInt())
        }!!
    }
}
