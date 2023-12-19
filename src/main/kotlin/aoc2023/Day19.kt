package aoc2023

import tools.Day
import tools.size

class Day19(test: Int? = null) : Day(2023, 19, test) {
    override fun solvePart1() = parts.sumOf { it.countPart1("in") }

    private fun Part.countPart1(workflow: String): Int = when (workflow) {
        "A" -> categories.values.sum()
        "R" -> 0
        else -> countPart1(rules[workflow]!!(this))
    }

    override fun solvePart2() = PartRange(categories.associateWith { 1..4000 }).acceptedPart2("in")

    private fun PartRange.acceptedPart2(workflow: String): Long = when (workflow) {
        "A" -> categories.values.fold(1L) { acc, intRange -> acc * intRange.size() }
        "R" -> 0
        else -> {
            val rule = rules[workflow]!!
            rule.steps.fold(0L to this) { (accepted, partRange), step ->
                val (ok, ko) = step.test(partRange)
                accepted + ok.acceptedPart2(step.action) to ko
            }.let { (accepted, partRange) ->
                accepted + partRange.acceptedPart2(rule.defaultAction)
            }
        }
    }

    data class PartRange(val categories: Map<Char, IntRange>)

    data class Rule(val steps: List<Step>, val defaultAction: String) {
        operator fun invoke(part: Part) = steps.firstNotNullOfOrNull { it(part) } ?: defaultAction
    }

    data class Step(val test: Test, val action: String) {
        operator fun invoke(part: Part) = if (test(part)) action else null
    }

    data class Test(val category: Char, val operator: Char, val limit: Int) {
        operator fun invoke(part: Part): Boolean {
            val value = part.categories[category]!!
            return when (operator) {
                '>' -> value > limit
                '<' -> value < limit
                else -> error("unexpected operator")
            }
        }

        operator fun invoke(partRange: PartRange): Pair<PartRange, PartRange> {
            val (ok, ko) = with(partRange.categories[category]!!) {
                when (operator) {
                    '>' -> (limit + 1).coerceAtLeast(first)..last to first..limit.coerceAtMost(last)
                    '<' -> first..(limit - 1).coerceAtMost(last) to limit.coerceAtLeast(first)..last
                    else -> error("unexpected operator")
                }
            }
            return partRange.copy(
                categories = partRange.categories.toMap().toMutableMap().also { it[category] = ok }
            ) to partRange.copy(
                categories = partRange.categories.toMap().toMutableMap().also { it[category] = ko }
            )
        }
    }

    data class Part(val categories: Map<Char, Int>)

    private val categories = listOf('x', 'm', 'a', 's')
    private val rules: Map<String, Rule>
    private val parts: List<Part>

    init {
        val (r, p) = input.split("\n\n")
        rules = r.lines().associate { line ->
            val (workflow, rule) = line.dropLast(1).split('{')
            workflow to rule.split(',').let { ruleParts ->
                Rule(
                    ruleParts.dropLast(1).map {
                        val (t, a) = it.split(':')
                        Step(Test(t[0], t[1], t.drop(2).toInt()), a)
                    },
                    ruleParts.last()
                )
            }
        }
        parts = p.lines().map { line ->
            Part(line.drop(1).dropLast(1).split(",").associate { it[0] to it.drop(2).toInt() })
        }
    }
}
