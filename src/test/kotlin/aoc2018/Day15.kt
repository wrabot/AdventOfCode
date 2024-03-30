package aoc2018

import Day
import tools.board.Board.XY
import tools.board.toBoard
import tools.graph.shortPath

class Day15(test: Int? = null) : Day(test) {
    override fun solvePart1() = Battle(input, 3).score
    override fun solvePart2() = generateSequence(Battle(input, 4)) {
        if (it.elvesAlive) null else Battle(input, it.elfAttack + 1)
    }.last().score

    class Battle(input: String, val elfAttack: Int) {
        private val area = input.lines().toBoard {
            Cell(
                isWall = it == '#',
                warrior = when (it) {
                    'G' -> Warrior(Race.Goblin)
                    'E' -> Warrior(Race.Elf)
                    else -> null
                }
            )
        }

        val elvesAlive: Boolean
        val score: Int

        init {
            val elves = area.cells.mapNotNull { it.aliveWarrior }.count { it.race == Race.Elf }
            var turn = 0
            while (turn()) turn++
            score = turn * area.cells.mapNotNull { it.aliveWarrior?.hp }.sum()
            elvesAlive = area.cells.mapNotNull { it.aliveWarrior }.count { it.race == Race.Elf } == elves
        }

        private fun turn(): Boolean {
            area.xy.mapNotNull { area[it].aliveWarrior?.to(it) }.forEach { (warrior, xy) ->
                if (warrior.hp <= 0) return@forEach
                if (isOver()) return false
                val target = xy.target(warrior.enemy)
                if (target != null) {
                    target.hp -= warrior.attack
                } else {
                    val move = shortPath(
                        start = xy,
                        isEnd = { area[it].aliveWarrior?.race == warrior.enemy },
                        neighbors = { it.neighbors().filter { area[it].isEmpty(warrior.race) } }
                    ).getOrNull(1)
                    if (move != null) {
                        area[xy].warrior = null
                        area[move].warrior = warrior
                        move.target(warrior.enemy)?.run { hp -= warrior.attack }
                    }
                }
            }
            return true
        }

        private fun isOver() = area.cells.mapNotNull { it.aliveWarrior?.race }.distinct().size != 2

        private fun XY.target(race: Race) =
            neighbors().mapNotNull { area[it].aliveWarrior }.filter { it.race == race }.minByOrNull { it.hp }

        private fun XY.neighbors() = area.neighbors4(this).sorted()

        private val Warrior.attack get() = if (race == Race.Elf) elfAttack else 3

        private enum class Race { Elf, Goblin }
        private data class Warrior(val race: Race, var hp: Int = 200) {
            val enemy = when (race) {
                Race.Elf -> Race.Goblin
                Race.Goblin -> Race.Elf
            }

            override fun toString() = race.name.take(1) + hp
        }

        private data class Cell(val isWall: Boolean, var warrior: Warrior?) {
            val aliveWarrior get() = warrior?.takeIf { it.hp > 0 }
            fun isEmpty(race: Race) = !isWall && aliveWarrior?.race != race
            override fun toString() = if (isWall) "#" else aliveWarrior?.run { race.name.take(1) } ?: "."
        }
    }
}
