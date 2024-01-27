package aoc2019

import kotlin.test.Test

class Year2019 {
    @Test
    fun day1() = Day1().check(3452245, 5175499)

    @Test
    fun day2() = Day2().check(3516593, 7749)

    @Test
    fun day3() {
        //Day3(1).check(6,30)
        //Day3(2).check(159,610)
        //Day3(3).check(135,410)
        Day3().check(865, 35038)
    }

    @Test
    fun day4() = Day4().check(1790, 1206)

    @Test
    fun day5() = Day5().check(15097178, 1558663)

    @Test
    fun day6() = Day6().check(130681, 313)

    @Test
    fun day7() {
        //Day7(1).checkPart1(43210)
        //Day7(2).checkPart1(54321)
        //Day7(3).checkPart1(65210)
        //Day7(4).checkPart2(139629729)
        //Day7(5).checkPart2(18216)
        Day7().check(116680, 89603079)
    }

    @Test
    fun day8() = Day8().check(
        1584,
        """

        #  #  ##   ##  ####  ##  
        # #  #  # #  # #    #  # 
        ##   #    #    ###  #    
        # #  #    # ## #    #    
        # #  #  # #  # #    #  # 
        #  #  ##   ### ####  ##  
        """.trimIndent()
    )

    @Test
    fun day9() {
        //Day9(1).checkPart1(listOf(109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99))
        //Day9(2).checkPart1(listOf(1219070632396864))
        //Day9(3).checkPart1(listOf(1125899906842624))
        Day9().check(listOf(2775723069), 49115)
    }

    @Test
    fun day10() {
        //Day10(0).checkPart1(8)
        //Day10(1).checkPart1(33)
        //Day10(2).checkPart1(35)
        //Day10(3).checkPart1(41)
        //Day10(4).check(210, 802)
        Day10().check(230, 1205)
    }

    @Test
    fun day11() = Day11().check(
        1930,
        """
        
        XXX  XXXX X  X X  X XXXX  XX  XXXX X  X   
        X  X X    X X  X  X X    X  X    X X  X   
        X  X XXX  XX   XXXX XXX  X      X  X  X   
        XXX  X    X X  X  X X    X     X   X  X   
        X    X    X X  X  X X    X  X X    X  X   
        X    X    X  X X  X XXXX  XX  XXXX  XX    
        """.trimIndent()
    )

    @Test
    fun day12() {
        //Day12(1).check(179, 2772)
        //Day12(2).check(1940, 4686774924)
        Day12().check(8044, 362375881472136)
    }

    @Test
    fun day13() = Day13().check(255, 12338)

    @Test
    fun day14() {
        //Day14(1).checkPart1(31)
        //Day14(2).checkPart1(165)
        //Day14(3).check(13312, 82892753)
        //Day14(4).check(180697, 5586022)
        //Day14(5).check(2210736, 460664)
        Day14().check(892207, 1935265)
    }

    @Test
    fun day15() = Day15().check(298, 346)

    @Test
    fun day16() {
        Day16(1).checkPart1("24176176")
        Day16(2).checkPart1("73745418")
        Day16(3).checkPart1("52432133")
        Day16().checkPart1("44098263")
        Day16(4).checkPart2("84462026")
        Day16(5).checkPart2("78725270")
        Day16(6).checkPart2("53553731")
        Day16().check("44098263", "12482168")
    }
}
