package aoc2020

import kotlin.test.Test

@Suppress("SpellCheckingInspection")
class Year2020 {
    @Test
    fun day1() = Day1().check(889779, 76110336)

    @Test
    fun day2() = Day2().check(500, 313)

    @Test
    fun day3() = Day3().check(191, 1478615040)

    @Test
    fun day4() = Day4().check(250, 158)

    @Test
    fun day5() = Day5().check(838, 714)

    @Test
    fun day6() = Day6().check(6662, 3382)

    @Test
    fun day7() = Day7().check(287, 48160)

    @Test
    fun day8() = Day8().check(1200, 1023)

    @Test
    fun day9() = Day9().check(375054920, 54142584)

    @Test
    fun day10() {
        //Day10(1).check(35, 8)
        //Day10(2).check(220, 19208)
        Day10().check(2346, 6044831973376)
    }

    @Test
    fun day11() {
        //Day11(1).check(37, 26)
        Day11().check(2368, 2124)
    }

    @Test
    fun day12() {
        //Day12(1).check(25, 286)
        Day12().check(879, 18107)
    }

    @Test
    fun day13() {
        //Day13(1).check(295, 1068781)
        Day13().check(2995, 1012171816131114)
    }

    @Test
    fun day14() = Day14().check(6631883285184, 3161838538691)

    @Test
    fun day15() {
        //Day15(1).check("[436, 1, 10, 27, 78, 438, 1836]", "[175594, 2578, 3544142, 261214, 6895259, 18, 362]")
        Day15().check("[1325]", "[59006]")
    }

    @Test
    fun day16() {
        //Day16(1).checkPart1(71)
        Day16().check(29851, 3029180675981)
    }

    @Test
    fun day17() {
        //Day17(1).check(112, 848)
        Day17().check(301, 2424)
    }

    @Test
    fun day18() = Day18().check(8929569623593, 231235959382961)

    @Test
    fun day19() = Day19().check(291, 409)

    @Test
    fun day20() {
        //Day20(1).check(20899048083289, 273)
        Day20().check(54755174472007, 1692)
    }

    @Test
    fun day21() {
        //Day21(1).check(5, "mxmxvkd,sqjhc,fvjkl")
        Day21().check(2072, "fdsfpg,jmvxx,lkv,cbzcgvc,kfgln,pqqks,pqrvc,lclnj")
    }

    @Test
    fun day22() {
        //Day22(1).check(306, 291)
        Day22().check(31629, 35196)
    }

    @Test
    fun day23() = Day23().check(89573246, 2029056128)

    @Test
    fun day24() {
        //Day24(1).check(10, 2208)
        Day24().check(459, 4150)
    }

    @Test
    fun day25() {
        //Day25(1).checkPart1(14897079)
        Day25().checkPart1(545789)
    }
}
