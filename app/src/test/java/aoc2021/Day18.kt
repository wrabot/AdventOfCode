package aoc2021

import log

object Day18 {
    fun solve(lines: List<String>) {
        log("part 1: ")
        lines.map { parseNumber(it).number }.reduce { acc, number -> acc + number }.magnitude().log()

        log("part 2: ")
        lines.indices.maxOf { first ->
            lines.indices.maxOf { second ->
                if (first == second) 0 else (parseNumber(lines[first]).number + parseNumber(lines[second]).number).magnitude()
            }
        }.log()
    }

    data class ParseResult(val number: NumberPart, val offset: Int)

    private fun parseNumber(input: String): ParseResult = when (input.first()) {
        '[' -> {
            val first = parseNumber(input.drop(1))
            val second = parseNumber(input.drop(first.offset + 1))
            ParseResult(NumberPair(first.number, second.number), first.offset + 1 + second.offset + 1)
        }
        else -> ParseResult(NumberValue(input.first().toString().toInt()), 2)
    }

    abstract class NumberPart {
        abstract fun magnitude(): Int

        operator fun plus(other: NumberPart): NumberPart {
            val addition = NumberPair(this, other)
            do {
                addition.updateLeftNeighbor(null)
                addition.updateRightNeighbor(null)
            } while (addition.explode() || addition.split())
            return addition
        }

        abstract fun updateLeftNeighbor(outsideLeft: NumberValue? = null): NumberValue?
        abstract fun updateRightNeighbor(outsideRight: NumberValue? = null): NumberValue?
    }

    data class NumberValue(var value: Int) : NumberPart() {
        override fun toString() = value.toString()
        override fun magnitude() = value
        override fun updateLeftNeighbor(outsideLeft: NumberValue?) = this
        override fun updateRightNeighbor(outsideRight: NumberValue?) = this
    }

    data class NumberPair(var left: NumberPart, var right: NumberPart, var leftNeighbor: NumberValue? = null, var rightNeighbor: NumberValue? = null) : NumberPart() {
        override fun toString() = "[$left,$right]"
        override fun magnitude() = left.magnitude() * 3 + right.magnitude() * 2

        override fun updateLeftNeighbor(outsideLeft: NumberValue?): NumberValue? {
            leftNeighbor = outsideLeft
            return right.updateLeftNeighbor(left.updateLeftNeighbor(outsideLeft))
        }

        override fun updateRightNeighbor(outsideRight: NumberValue?): NumberValue? {
            rightNeighbor = outsideRight
            return left.updateRightNeighbor(right.updateRightNeighbor(outsideRight))
        }

        fun explode(level: Int = 0, onExplode: () -> Unit = {}): Boolean {
            return if (level >= 4 && left is NumberValue && right is NumberValue) {
                leftNeighbor?.apply { value += (left as NumberValue).value }
                rightNeighbor?.apply { value += (right as NumberValue).value }
                onExplode()
                true
            } else {
                left.let { it is NumberPair && it.explode(level + 1) { left = NumberValue(0) } } ||
                        right.let { it is NumberPair && it.explode(level + 1) { right = NumberValue(0) } }
            }
        }

        fun split(): Boolean = left.let {
            when {
                it is NumberPair -> it.split()
                it is NumberValue && it.value > 9 -> {
                    left = NumberPair(NumberValue(it.value / 2), NumberValue((it.value + 1) / 2))
                    true
                }
                else -> false
            }
        } || right.let {
            when {
                it is NumberPair -> it.split()
                it is NumberValue && it.value > 9 -> {
                    right = NumberPair(NumberValue(it.value / 2), NumberValue((it.value + 1) / 2))
                    true
                }
                else -> false
            }
        }
    }
}
