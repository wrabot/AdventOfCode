package aoc2021

import kotlin.test.Test

class Year2021 {
    @Test
    fun day1() {
        //Day1(1).check(7, 5)
        Day1().check(1624, 1653)
    }

    @Test
    fun day2() {
        //Day2(1).check(150, 900)
        Day2().check(1459206, 1320534480)
    }

    @Test
    fun day3() {
        //Day3(1).check(198, 230)
        Day3().check(2595824, 2135254)
    }

    @Test
    fun day4() {
        //Day4(1).check(4512, 1924)
        Day4().check(58374, 11377)
    }

    @Test
    fun day5() {
        //Day5(1).check(5, 12)
        Day5().check(5169, 22083)
    }

    @Test
    fun day6() {
        //Day6(1).check(5934, 26984457539)
        Day6().check(379414, 1705008653296)
    }

    @Test
    fun day7() {
        //Day7(1).check(37, 168)
        Day7().check(356179, 99788435)
    }

    @Test
    fun day8() {
        //Day8(1).check(26, 61229)
        Day8().check(476, 1011823)
    }

    @Test
    fun day9() {
        //Day9(1).check(15, 1134)
        Day9().check(526, 1123524)
    }

    @Test
    fun day10() {
        //Day10(1).check(26397, 288957)
        Day10().check(469755, 2762335572)
    }

    @Test
    fun day11() {
        //Day11(1).check(1656, 195)
        Day11().check(1591, 314)
    }

    @Test
    fun day12() {
        //Day12(1).check(10, 36)
        //Day12(2).check(19, 103)
        //Day12(3).check(226, 3509)
        Day12().check(4691, 140718)
    }

    @Test
    fun day13() {
        Day13(1).check(
            17, """
#####
#   #
#   #
#   #
#####"""
        )
        Day13().check(
            693, """
#  #  ##  #    #### ###   ##  #### #  #
#  # #  # #       # #  # #  #    # #  #
#  # #    #      #  #  # #  #   #  #  #
#  # #    #     #   ###  ####  #   #  #
#  # #  # #    #    # #  #  # #    #  #
 ##   ##  #### #### #  # #  # ####  ## """
        )
    }

    @Test
    fun day14() {
        //Day14(1).check(1588, 2188189693529)
        Day14().check(2587, 3318837563123)
    }

    @Test
    fun day15() {
        //Day15(1).check(40, 315)
        Day15().check(562, 2874)
    }

    @Test
    fun day16() {
        //Day16(1).check("[16, 12, 23, 31]", "[15, 46, 46, 54]")
        //Day16(2).check("[14, 8, 15, 11, 13, 19, 16, 20]", "[3, 54, 7, 9, 1, 0, 0, 1]")
        Day16().check("[979]", "[277110354175]")
    }

    @Test
    fun day17() {
        //Day17(1).check(45, 112)
        Day17().check(10585, 5247)
    }

    @Test
    fun day18() {
        //Day18(1).check(4140, 3993)
        Day18().check(3411, 4680)
    }

    @Test
    fun day19() {
        //Day19(1).check(79, 3621)
        Day19().check(445, 13225)
    }

    @Test
    fun day20() {
        //Day20(1).check(35, 3351)
        Day20().check(5563, 19743)
    }

    @Test
    fun day21() {
        //Day21(1).check(739785, 444356092776315)
        Day21().check(918081, 158631174219251)
    }

    @Test
    fun day22() {
        //Day22(1).check(590784, 39769202357779)
        //Day22(2).check(474140, 2758514936282235)
        Day22().check(537042, 1304385553084863)
    }

    @Test
    fun day23() {
        //Day23(1).check(12521, 44169)
        Day23().check(13495, 53767)
    }

    @Test
    fun day24() {
        Day24().check(99919692496939, 81914111161714)
    }

    @Test
    fun day25() {
        //Day25(1).checkPart1(58)
        Day25().checkPart1(507)
    }
}
