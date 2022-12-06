package aoc2022

fun checkAll() {
    Day1(1).check(24000, 45000)
    Day1().check(66487, 197301)
    Day2(1).check(15, 12)
    Day2().check(14375, 10274)
    Day3(1).check(157, 70)
    Day3().check(7811, 2639)
    Day4(1).check(2, 4)
    Day4().check(483, 874)
    Day5(1).check("CMZ", "MCD")
    Day5().check("TPGVQPFDH", "DMRDFRHHH")
    Day6(1).check(listOf(7, 5, 6, 10, 11), listOf(19, 23, 23, 29, 26))
    Day6().check(listOf(1640), listOf(3613))
}
