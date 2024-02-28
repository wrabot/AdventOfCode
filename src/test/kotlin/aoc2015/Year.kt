package aoc2015

import kotlin.test.Test

class Year2015 {
    @Test
    fun day1() = Day1().check(74, 1795)

    @Test
    fun day2() = Day2().check(1606483, 3842356)

    @Test
    fun day3() = Day3().check(2592, 2360)

    @Test
    fun day4() = Day4().check(117946, 3938038)

    @Test
    fun day5() = Day5().check(255, 55)

    @Test
    fun day6() = Day6().check(543903, 14687245)

    @Test
    fun day7() = Day7().check(3176, 14710)

    @Test
    fun day8() {
        //Day8(1).check(12,19)
        Day8().check(1350, 2085)
    }

    @Test
    fun day9() {
        //Day9(1).check(605, 982)
        Day9().check(207, 804)
    }

    @Test
    fun day10() = Day10().check(252594, 3579328)

    @Test
    fun day11() {
        //Day11(1).checkPart1("abcdffaa")
        Day11().check("cqjxxyzz", "cqkaabcc")
    }

    @Test
    fun day12() = Day12().check(119433, 68466)

    @Test
    fun day13() {
        //Day13(1).checkPart1(330)
        Day13().check(664, 640)
    }

    @Test
    fun day14() {
        //Day14(1).check(1120,689)
        Day14().check(2660, 1256)
    }

    @Test
    fun day15() {
        //Day15(1).check(62842880,57600000)
        Day15().check(222870, 117936)
    }

    @Test
    fun day16() = Day16().check("Sue 213", "Sue 323")

    @Test
    fun day17() = Day17().check(654, 57)

    @Test
    fun day18() = Day18().check(1061, 1006)

    @Test
    fun day19() {
        //Day19(1).checkPart1(7)
        Day19().check(509, 195)
    }

    @Test
    fun day20() = Day20().check(665280, 705600)

    @Test
    fun day21() = Day21().check(78, 148)

    @Test
    fun day22() = Day22().check(953, 1289)

    @Test
    fun day23() = Day23().check(307, 160)

    @Test
    fun day24() {
        //Day24(1).check(99, 44)
        Day24().check(10439961859, 72050269)
    }

    @Test
    fun day25() = Day25().checkPart1(19980801)
}
