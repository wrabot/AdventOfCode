package aoc2018

import Day

class Day19(test: Int? = null) : Day(test) {
    override fun solvePart1() = wristDevice.run()

    override fun solvePart2(): Int {
        // display program more clearly
        //wristDevice.list()
        // reverse for a = 1
        /*
        a = 0
        b = 10551383
        c = 1
        do {
            e = 1
            do {
                if (c * e == b) a += c
                e++
            } while (e <= b)
            c++
        } while (c <= b)
         */
        // => sum of dividers of b
        // if (a == 0) b=983 if (a==1) b=10551383
        val b = 10551383
        return (1..b).sumOf { if (b % it == 0) it else 0 }
    }

    private val wristDevice = WristDevice(input)
}
