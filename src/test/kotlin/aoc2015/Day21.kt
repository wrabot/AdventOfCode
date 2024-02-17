package aoc2015

import Day
import tools.toWords

class Day21(test: Int? = null) : Day(test) {
    override fun solvePart1() = equipments.first.minOf { it.cost }
    override fun solvePart2() = equipments.second.maxOf { it.cost }

    private val myHitPoint = 100
    private val enemyHitPoint = lines[0].toWords().last().toInt()
    private val enemyDamage = lines[1].toWords().last().toInt()
    private val enemyArmor = lines[2].toWords().last().toInt()

    data class Item(val cost: Int = 0, val damage: Int = 0, val armor: Int = 0) {
        private constructor(list: List<Int>) : this(list[0], list[1], list[2])
        constructor(line: String) : this(line.toWords().takeLast(3).map { it.toInt() })

        operator fun plus(other: Item) = Item(cost + other.cost, damage + other.damage, armor + other.armor)
    }

    private val weapons = """    
            Dagger        8     4       0
            Shortsword   10     5       0
            Warhammer    25     6       0
            Longsword    40     7       0
            Greataxe     74     8       0
    """.trimIndent().lines().map(::Item)
    private val armor = listOf(Item()) + """
            Leather      13     0       1
            Chainmail    31     0       2
            Splintmail   53     0       3
            Bandedmail   75     0       4
            Platemail   102     0       5
    """.trimIndent().lines().map(::Item)
    private val rings = listOf(Item()) + """
            Damage +1    25     1       0
            Damage +2    50     2       0
            Damage +3   100     3       0
            Defense +1   20     0       1
            Defense +2   40     0       2
            Defense +3   80     0       3
    """.trimIndent().lines().map(::Item)

    private val equipments = weapons.flatMap { weapon ->
        armor.flatMap { armor ->
            rings.dropLast(1).flatMapIndexed { index, ring1 ->
                rings.drop(if (index == 0) 0 else index + 1).map { ring2 ->
                    weapon + armor + ring1 + ring2
                }
            }
        }
    }.partition {
        val myTurns = turnsToDie(myHitPoint, enemyDamage, it.armor)
        val enemyTurns = turnsToDie(enemyHitPoint, it.damage, enemyArmor)
        myTurns >= enemyTurns
    }

    private fun turnsToDie(hitPoint: Int, damage: Int, armor: Int): Int {
        val loss = (damage - armor).coerceAtLeast(1)
        return (hitPoint + loss - 1) / loss
    }
}
