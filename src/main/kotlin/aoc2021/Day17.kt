package aoc2021

import tools.log

object Day17 {
    fun solve() {
        //findBestMaxY(20..30, -10..-5).log()
        log("part 1: " + findBestMaxY(102..157, -146..-90))
        //countVelocities(20..30, -10..-5).log()
        log("part 2: " + countVelocities(102..157, -146..-90))
    }

    private fun maxY(vxInit: Int, vyInit: Int, xTarget: IntRange, yTarget: IntRange): Int? {
        var vx = vxInit
        var vy = vyInit
        var x = 0
        var y = 0
        var maxY = 0
        while (x <= xTarget.last && y >= yTarget.first) {
            x += vx
            y += vy
            if (y > maxY) maxY = y
            if (vx > 0) vx-- else if (vx < 0) vx++
            vy--
            if (x in xTarget && y in yTarget) return maxY
        }
        return null
    }

    private fun findBestMaxY(xTarget: IntRange, yTarget: IntRange): Int {
        var bestMaxY = 0
        for (vy in 0..xTarget.last) {
            for (vx in 0..xTarget.last) {
                bestMaxY = maxY(vx, vy, xTarget, yTarget)?.coerceAtLeast(bestMaxY) ?: bestMaxY
            }
        }
        return bestMaxY
    }

    private fun countVelocities(xTarget: IntRange, yTarget: IntRange): Int {
        var velocities = 0
        for (vy in yTarget.first..xTarget.last) {
            for (vx in 0..xTarget.last) {
                if (maxY(vx, vy, xTarget, yTarget) != null) {
                    velocities++
                }
            }
        }
        return velocities
    }
}
