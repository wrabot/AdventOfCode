package aoc2016

import kotlin.test.Test

class Year {
    @Test
    fun day1() = Day1().check(300, 159)

    @Test
    fun day2() {
        //Day2(1).check("1985", "5DB3")
        Day2().check("82958", "B3DB8")
    }

    @Test
    fun day3() = Day3().check(917, 1649)

    @Test
    fun day4() {
        //Day4(1).checkPart1(1514)
        Day4().check(245102, 324)
    }

    @Test
    fun day5() = Day5().check("1a3099aa", "694190cd")

    @Test
    fun day6() {
        //Day6(1).checkPart1("easter")
        Day6().check("qtbjqiuq", "akothqli")
    }

    @Test
    fun day7() {
        Day7(1).checkPart1(2)
        Day7(2).checkPart2(3)
        Day7().check(115, 231)
    }

    @Test
    fun day8() = Day8().check(
        115,
        """
        #### #### #### #   ##  # #### ###  ####  ###   ## 
        #    #    #    #   ## #  #    #  # #      #     # 
        ###  ###  ###   # # ##   ###  #  # ###    #     # 
        #    #    #      #  # #  #    ###  #      #     # 
        #    #    #      #  # #  #    # #  #      #  #  # 
        #### #    ####   #  #  # #    #  # #     ###  ##  
        """.trimIndent()
    ) // EFEYKFRFIJ

    @Test
    fun day9() = Day9().check(115118, 11107527530)

    @Test
    fun day10() = Day10().check(181, 12567)

    @Test
    fun day11() {
        //Day11(1).checkPart1(11)
        Day11().check(31, 55)
    }

    @Test
    fun day12() = Day12().check(318020, 9227674)

    @Test
    fun day13() {
        //Day13(1).checkPart1(11)
        Day13().check(96, 141)
    }

    @Test
    fun day14() {
        //Day14(1).check(22728, 22551)
        Day14().check(25427, 22045)
    }

    @Test
    fun day15() {
        //Day15(1).checkPart1(5)
        Day15().check(16824, 3543984)
    }

    @Test
    fun day16() = Day16().check("10010110010011110", "01101011101100011")

    @Test
    fun day17() {
        //Day17(1).check("DDRRRD", 370)
        //Day17(2).check("DDUDRLRRUDRD", 492)
        //Day17(3).check("DRURDRUDDLLDLUURRDULRLDUUDDDRR", 830)
        Day17().check("RDRLDRDURD", 596)
    }

    @Test
    fun day18() {
        Day18(1).checkPart1(38)
        Day18().check(1951, 20002936)  
    } 

    @Test
    fun day19() = Day19().check(1830117, 1417887)

    @Test
    fun day20() = Day20().check(23923783, 125)

    @Test
    fun day21() = Day21().check("fdhbcgea", "egfbcadh")

    @Test
    fun day22() = Day22().check(872, 211)

    @Test
    fun day23() = Day23().check(11640, 479008200)

    @Test
    fun day24() = Day24().check(464, 652)

    @Test
    fun day25() = Day25().check(Unit, Unit)
}
