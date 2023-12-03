package aoc2019

fun checkAll() {
    Day1().check(3452245, 5175499)
    Day2().check(3516593, 7749)
    //Day3(1).check(6,30)
    //Day3(2).check(159,610)
    //Day3(3).check(135,410)
    Day3().check(865, 35038)
    Day4().check(1790, 1206)
    Day5().check(15097178, 1558663)
    Day6().check(130681, 313)
    Day7().check(116680, 89603079)
    Day7(1).checkPart1(43210)
    Day7(2).checkPart1(54321)
    Day7(3).checkPart1(65210)
    Day7(4).checkPart2(139629729)
    Day7(5).checkPart2(18216)
    Day8().check(
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
    Day9(1).checkPart1(listOf(109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99))
    Day9(2).checkPart1(listOf(1219070632396864))
    Day9(3).checkPart1(listOf(1125899906842624))
    Day9().check(listOf(2775723069), 49115)
}
