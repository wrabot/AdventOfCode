package aoc2017

import Day
import tools.match

class Day25(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        val tape = StringBuilder("0")
        var offset = 0
        var state = start
        repeat(steps) {
            val action = states[state]!![tape[offset] - '0']
            tape[offset] = if (action.set) '1' else '0'
            when {
                action.right -> if (++offset == tape.length) tape.append('0')
                offset == 0 -> tape.insert(0, '0')
                else -> offset--
            }
            state = action.next
        }
        return tape.count { it == '1' }
    }

    override fun solvePart2() = Unit

    private data class Action(val set: Boolean, val right: Boolean, val next: String)

    private val start: String
    private val steps: Int
    private val states: Map<String, List<Action>>

    init {
        val blocks = input.split("\n\n").map { it.lines() }
        start = blocks.first()[0].match("Begin in state (.).".toRegex())!!.first()
        steps = blocks.first()[1].match("Perform a diagnostic checksum after (.*) steps.".toRegex())!!.first().toInt()
        states = blocks.drop(1).associate {
            it[0].match("In state (.):".toRegex())!!.first() to listOf(
                createAction(it.drop(2)),
                createAction(it.drop(6))
            )
        }
    }

    private fun createAction(text: List<String>) = Action(
        text[0].trim().match("- Write the value (.).".toRegex())!!.first() == "1",
        text[1].trim().match("- Move one slot to the (.*).".toRegex())!!.first() == "right",
        text[2].trim().match("- Continue with state (.).".toRegex())!!.first(),
    )
}
