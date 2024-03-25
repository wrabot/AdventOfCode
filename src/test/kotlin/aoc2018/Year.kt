package aoc2018

import kotlin.test.Test

class Year2018 {
    @Test
    fun day1() = Day1().check(427, 341)

    @Test
    fun day2() = Day2().check(6723, "prtkqyluiusocwvaezjmhmfgx")

    @Test
    fun day3() = Day3().check(109716, 124)

    @Test
    fun day4() = Day4().check(77941, 35289)

    @Test
    fun day5() = Day5().check(9562, 4934)

    @Test
    fun day6() = Day6().check(3251, 47841)

    @Test
    fun day7() = Day7().check("BFKEGNOVATIHXYZRMCJDLSUPWQ", 1020)

    @Test
    fun day8() {
        //Day8(1).check(138, 66)
        Day8().check(42254, 25007)
    }

    @Test
    fun day9() {
        //Day9(1).checkPart1(32)
        Day9().check(382055, 3133277384)
    }

    @Test
    fun day10() {
        Day10(1).check(
            """
            #...#..###
            #...#...#.
            #...#...#.
            #####...#.
            #...#...#.
            #...#...#.
            #...#...#.
            #...#..###
            """.trimIndent(),
            3
        )
        Day10().check(
            """
            #####...#....#.....###..#....#.....###....##....######..#....#
            #....#..#....#......#...#....#......#....#..#...#.......#....#
            #....#...#..#.......#....#..#.......#...#....#..#........#..#.
            #....#...#..#.......#....#..#.......#...#....#..#........#..#.
            #####.....##........#.....##........#...#....#..#####.....##..
            #....#....##........#.....##........#...######..#.........##..
            #....#...#..#.......#....#..#.......#...#....#..#........#..#.
            #....#...#..#...#...#....#..#...#...#...#....#..#........#..#.
            #....#..#....#..#...#...#....#..#...#...#....#..#.......#....#
            #####...#....#...###....#....#...###....#....#..######..#....#
            """.trimIndent(),
            10605
        )
    }

    @Test
    fun day11() {
        //Day11(1).check("33,45", "90,269,16")
        //Day11(2).check("21,61", "232,251,12")
        Day11().check("21,93", "231,108,14")
    } 
    
    @Test
    fun day12() = Day12().check(Unit, Unit)

    @Test
    fun day13() = Day13().check(Unit, Unit)

    @Test
    fun day14() = Day14().check(Unit, Unit)

    @Test
    fun day15() = Day15().check(Unit, Unit)

    @Test
    fun day16() = Day16().check(Unit, Unit)

    @Test
    fun day17() = Day17().check(Unit, Unit)

    @Test
    fun day18() = Day18().check(Unit, Unit)

    @Test
    fun day19() = Day19().check(Unit, Unit)

    @Test
    fun day20() = Day20().check(Unit, Unit)

    @Test
    fun day21() = Day21().check(Unit, Unit)

    @Test
    fun day22() = Day22().check(Unit, Unit)

    @Test
    fun day23() = Day23().check(Unit, Unit)

    @Test
    fun day24() = Day24().check(Unit, Unit)

    @Test
    fun day25() = Day25().check(Unit, Unit)
}
