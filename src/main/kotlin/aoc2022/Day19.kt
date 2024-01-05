package aoc2022

import Day

class Day19(test: Int? = null) : Day(2022, 19, test) {
    override fun solvePart1() = blueprints.sumOf { it.solve(24) * it.id }
    override fun solvePart2() = blueprints.take(3).map { it.solve(32) }.reduce { acc, i -> acc * i }

    private fun Blueprint.solve(duration: Int): Int {
        //log("solve $id $duration")
        val maxOreCost = listOf(clayRobotOreCost, obsidianRobotOreCost, geodeRobotOreCost).max()
        var done = mutableSetOf(State(List(4) { 0 }, List(4) { if (it == oreIndex) 1 else 0 }))
        var maxGeodes = 0
        repeat(duration) { minute ->
            val remaining = duration - minute
            //log("minute ${minute + 1}")
            val todo = done.toMutableList()
            done = mutableSetOf()
            while (todo.isNotEmpty()) {
                val state = todo.removeLast()
                //log("state $state")
                choices(state).forEach { choice ->
                    val minerals = state.minerals.toMutableList()
                    val robots = state.robots.toMutableList()
                    turn(minerals, robots, choice)
                    maxGeodes = maxGeodes.coerceAtLeast(minerals[geodeIndex])

                    if ( // more robots is useless
                        robots[oreIndex] <= maxOreCost &&
                        robots[clayIndex] <= obsidianRobotClayCost &&
                        robots[obsidianIndex] <= geodeRobotObsidianCost &&
                        ((remaining - 1) * remaining) / 2 + minerals[geodeIndex] + remaining * robots[obsidianIndex] > maxGeodes
                    ) {
                        // avoid to keep too many resources
                        minerals[oreIndex] = minerals[oreIndex].coerceAtMost(maxOreCost * 3)
                        minerals[clayIndex] = minerals[clayIndex].coerceAtMost(obsidianRobotClayCost * 2)
                        minerals[obsidianIndex] = minerals[obsidianIndex].coerceAtMost(geodeRobotObsidianCost * 2)

                        done.add(State(minerals, robots))
                    }
                }
            }
        }
        return maxGeodes
    }

    private fun Blueprint.choices(state: State) = (0..3).filter {
        when (it) {
            geodeIndex -> geodeRobotOreCost <= state.minerals[oreIndex] && geodeRobotObsidianCost <= state.minerals[obsidianIndex]
            obsidianIndex -> obsidianRobotOreCost <= state.minerals[oreIndex] && obsidianRobotClayCost <= state.minerals[clayIndex]
            clayIndex -> clayRobotOreCost <= state.minerals[oreIndex]
            oreIndex -> oreRobotOreCost <= state.minerals[oreIndex]
            else -> false
        }
    }.plusElement(-1)

    private fun Blueprint.turn(minerals: MutableList<Int>, robots: MutableList<Int>, choice: Int) {
        //log("time $time choice $choice")
        when (choice) {
            geodeIndex -> {
                minerals[oreIndex] -= geodeRobotOreCost
                minerals[obsidianIndex] -= geodeRobotObsidianCost
            }
            obsidianIndex -> {
                minerals[oreIndex] -= obsidianRobotOreCost
                minerals[clayIndex] -= obsidianRobotClayCost
            }
            clayIndex -> minerals[oreIndex] -= clayRobotOreCost
            oreIndex -> minerals[oreIndex] -= oreRobotOreCost
            else -> {}
        }
        for (i in 0..3) minerals[i] += robots[i]
        if (choice in 0..3) robots[choice]++
    }

    data class State(val minerals: List<Int>, val robots: List<Int>) : Comparable<State> {
        override fun compareTo(other: State) = (0..3).reversed().map { index ->
            robots[index].compareTo(other.robots[index]).takeIf { it != 0 } ?: minerals[index].compareTo(other.minerals[index])
        }.dropWhile { it == 0 }.firstOrNull() ?: 0
    }

    data class Blueprint(
        val id: Int,
        val oreRobotOreCost: Int,
        val clayRobotOreCost: Int,
        val obsidianRobotOreCost: Int,
        val obsidianRobotClayCost: Int,
        val geodeRobotOreCost: Int,
        val geodeRobotObsidianCost: Int
    )

    private val oreIndex = 0
    private val clayIndex = 1
    private val obsidianIndex = 2
    private val geodeIndex = 3

    private val regex =
        Regex("Blueprint (.*): Each ore robot costs (.*) ore\\. Each clay robot costs (.*) ore\\. Each obsidian robot costs (.*) ore and (.*) clay\\. Each geode robot costs (.*) ore and (.*) obsidian\\.")

    private val blueprints = lines.map { line ->
        regex.matchEntire(line)!!.groupValues.drop(1).map { it.toInt() }.let { Blueprint(it[0], it[1], it[2], it[3], it[4], it[5], it[6]) }
    }
}
