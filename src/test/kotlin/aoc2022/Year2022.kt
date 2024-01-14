package aoc2022

import kotlin.test.Test

class Year2022 {
    @Test
    fun day1() {
        //Day1(1).check(24000, 45000)
        Day1().check(66487, 197301)
    }

    @Test
    fun day2() {
        //Day2(1).check(15, 12)
        Day2().check(14375, 10274)
    }

    @Test
    fun day3() {
        //Day3(1).check(157, 70)
        Day3().check(7811, 2639)
    }

    @Test
    fun day4() {
        //Day4(1).check(2, 4)
        Day4().check(483, 874)
    }

    @Test
    fun day5() {
        //Day5(1).check("CMZ", "MCD")
        Day5().check("TPGVQPFDH", "DMRDFRHHH")
    }

    @Test
    fun day6() {
        //Day6(1).check(listOf(7, 5, 6, 10, 11), listOf(19, 23, 23, 29, 26))
        Day6().check(listOf(1640), listOf(3613))
    }

    @Test
    fun day7() {
        //Day7(1).check(95437, 24933642)
        Day7().check(1743217, 8319096)
        //Day7b(1).check(95437, 24933642)
        Day7b().check(1743217, 8319096)
    }

    @Test
    fun day8() {
        //Day8(1).check(21, 8)
        Day8().check(1785, 345168)
    }

    @Test
    fun day9() {
        //Day9(1).check(13, 1)
        //Day9(2).check(88, 36)
        Day9().check(5907, 2303)
    }

    @Test
    fun day10() {
        /*
          Day10(1).check(
              13140, """
      ##..##..##..##..##..##..##..##..##..##..
      ###...###...###...###...###...###...###.
      ####....####....####....####....####....
      #####.....#####.....#####.....#####.....
      ######......######......######......####
      #######.......#######.......#######....."""
          )
          */
        Day10().check(
            14520, """
###..####.###...##..####.####...##.###..
#..#....#.#..#.#..#....#.#.......#.#..#.
#..#...#..###..#......#..###.....#.###..
###...#...#..#.#.##..#...#.......#.#..#.
#....#....#..#.#..#.#....#....#..#.#..#.
#....####.###...###.####.####..##..###.."""
        )
    }

    @Test
    fun day11() = Day11().check(90294, 18170818354)

    @Test
    fun day12() {
        //Day12(1).check(31, 29)
        Day12().check(361, 354)
    }

    @Test
    fun day13() {
        //Day13(1).check(13, 140)
        Day13().check(5938, 29025)
    }

    @Test
    fun day14() {
        //Day14(1).check(24, 93)
        Day14().check(964, 32041)
    }

    @Test
    fun day15() {
        //Day15(1).check(26, 56000011)
        Day15().check(4560025, 12480406634249)
    }

    @Test
    fun day16() {
        //Day16(1).check(1651, 1707)
        Day16().check(2077, 2741)
    }

    @Test
    fun day17() {
        //Day17(1).check(3068, 1514285714288)
        Day17().check(3149, 1553982300884)
    }

    @Test
    fun day18() {
        //Day18(1).check(64, 58)
        Day18().check(3364, 2006)
    }

    @Test
    fun day19() {
        //Day19(1).check(33, 56*62)
        Day19().check(1081, 2415)
    }

    @Test
    fun day20() {
        //Day20(1).check(3, 1623178306)
        Day20().check(8764, 535648840980)
    }

    @Test
    fun day21() {
        //Day21(1).check(152, 301)
        Day21().check(84244467642604, 3759569926192)
    }

    @Test
    fun day22() {
        //Day22(1).check(6032, 5031)
        Day22().check(67390, 95291)
    }

    @Test
    fun day23() {
        //Day23(1).check(110, 20)
        Day23().check(3917, 988)
    }

    @Test
    fun day24() {
        //Day24(1).check(18, 54)
        Day24().check(230, 713)
    }

    @Test
    fun day25() {
        //Day25(1).check("2=-1=0", Unit)
        Day25().checkPart1("2=01-0-2-0=-0==-1=01")
    }
}
