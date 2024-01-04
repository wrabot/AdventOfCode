import tools.log
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTime

fun main() {
    val duration = measureTime {
        runCatching { checkAll() }.onFailure { it.printStackTrace() }
    }
    log("total: $duration")
}

fun checkAll() {
    aoc2015.checkAll()
    aoc2019.checkAll()
    aoc2020.checkAll()
    aoc2021.checkAll()
    aoc2022.checkAll()
    aoc2023.checkAll()
}
