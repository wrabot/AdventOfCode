package aoc2022

import Day

class Day21(test: Int? = null) : Day(test) {

    // part1

    override fun solvePart1() = values.toMutableMap().eval("root")

    private fun MutableMap<String, Long>.eval(monkey: String): Long = getOrPut(monkey) {
        val (left, op, right) = operations[monkey]!!
        compute(eval(left), op, eval(right))
    }

    // part2

    override fun solvePart2(): Long {
        values.remove("humn")
        val (left, _, right) = operations["root"]!!
        return solve(buildTree(left), buildTree(right) as Long) as Long
    }

    private fun solve(tree: Any?, expected: Long): Any? {
        if (tree == null) return expected
        if (tree !is Triple<*, *, *>) error("!!!triple")
        val (left, op, right) = tree
        return when {
            left is Long -> solve(
                right,
                when (op) {
                    "+" -> expected - left
                    "-" -> left - expected
                    "*" -> expected / left
                    "/" -> left / expected
                    else -> error("!!!op")
                }
            )

            right is Long -> solve(
                left,
                when (op) {
                    "+" -> expected - right
                    "-" -> expected + right
                    "*" -> expected / right
                    "/" -> expected * right
                    else -> error("!!!op")
                }
            )

            else -> error("!!!long")
        }
    }

    private fun buildTree(monkey: String): Any? = values[monkey] ?: operations[monkey]?.let { (left, op, right) ->
        val leftTree = buildTree(left)
        val rightTree = buildTree(right)
        if (leftTree is Long && rightTree is Long) {
            compute(leftTree, op, rightTree).apply { values[monkey] = this }
        } else {
            Triple(leftTree, op, rightTree)
        }
    }

    // common

    private fun compute(leftValue: Long, op: String, rightValue: Long): Long = when (op) {
        "+" -> leftValue + rightValue
        "-" -> leftValue - rightValue
        "*" -> leftValue * rightValue
        "/" -> leftValue / rightValue
        else -> error("!!!op")
    }

    private val values = mutableMapOf<String, Long>()
    private val operations = lines.mapNotNull {
        it.split(": ").let { (monkey, action) ->
            val value = action.toLongOrNull()
            if (value != null) {
                values[monkey] = value
                null
            } else {
                action.split(" ").let { (left, op, right) -> monkey to Triple(left, op, right) }
            }
        }
    }.toMap()
}
