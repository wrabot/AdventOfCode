package aoc2024

import kotlin.test.Test

class Year2024 {
    @Test
    fun day1() = Day1().check(2375403, 23082277)

    @Test
    fun day2() {
        //Day2(1).check(2, 4)
        Day2().check(559, 601)
    }

    @Test
    fun day3() = Day3().check(156388521, 75920122)

    @Test
    fun day4() {
        //Day4(1).check(18, 9)
        Day4().check(2378, 1796)
    }

    @Test
    fun day5() {
        //Day5(1).check(143, 123)
        Day5().check(5268, 5799)
    }

    @Test
    fun day6() {
        //Day6(1).check(41, 6)
        Day6().check(4982, 1663)
    }

    @Test
    fun day7() {
        //Day7(1).check(3749, 11387)
        Day7().check(28730327770375, 424977609625985)
    }

    @Test
    fun day8() {
        //Day8(1).check(14, 34)
        Day8().check(285, 944)
    }

    @Test
    fun day9() {
        //Day9(1).check(1928, 2858)
        Day9().check(6356833654075, 6389911791746)
    }

    @Test
    fun day10() {
        //Day10(1).check(36, 81)
        Day10().check(574, 1238)
    }

    @Test
    fun day11() {
        //Day11(1).checkPart1(55312)
        Day11().check(198089, 236302670835517)
    }

    @Test
    fun day12() {
        //Day12(1).check(140, 80)
        //Day12(2).check(772, 436)
        //Day12(3).check(1930, 1206)
        //Day12(4).checkPart2(236)
        //Day12(5).checkPart2(368)
        Day12().check(1471452, 863366)
    }

    @Test
    fun day13() {
        //Day13(1).checkPart1(480)
        Day13().check(26005, 105620095782547)
    }

    @Test
    fun day14() {
        //Day14(1).checkPart1(12)
        Day14().check(215476074, 6285)
    }

    @Test
    fun day15() {
        //Day15(1).checkPart1(2028)
        //Day15(3).checkPart2(618)
        //Day15(2).check(10092, 9021)
        Day15().check(1415498, 1432898)
    }

    @Test
    fun day16() {
        //Day16(1).check(7036, 45)
        Day16().check(143580, 645)
    }

    @Test
    fun day17() {
        //Day17(1).checkPart1("4,6,3,5,6,3,5,2,1,0")
        Day17().check("3,1,5,3,7,4,2,7,5", "190593310997519")
    }

    @Test
    fun day18() {
        //Day18(1).check(22, "6,1")
        Day18().check(454, "8,51")
    }

    @Test
    fun day19() {
        //Day19(1).check(6, 16)
        Day19().check(298, 572248688842069)
    }

    @Test
    fun day20() {
        //Day20(1).check(0, 0)
        Day20().check(1311, 961364)
    }

    @Test
    fun day21() {
        //Day21(1).checkPart1(126384)
        Day21().check(278748, 337744744231414)
    }

    @Test
    fun day22() {
        //Day22(1).checkPart1(37327623)
        //Day22(2).checkPart2(23)
        Day22().check(13004408787, 1455)
    }

    @Test
    fun day23() {
        //Day23(1).check(7, "co,de,ka,ta")
        Day23().check(1327, "df,kg,la,mp,pb,qh,sk,th,vn,ww,xp,yp,zk")
    }

    @Test
    fun day24() {
        //Day24(1).checkPart1(2024)
        Day24().check(51715173446832, "dpg,kmb,mmf,tvp,vdk,z10,z15,z25")
    }

    @Test
    fun day25() {
        Day25(1).checkPart1(3)
        Day25().checkPart1(3397)
    }
}
