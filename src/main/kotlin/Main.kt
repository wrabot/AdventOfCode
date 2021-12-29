fun main() {
    runCatching { checkAll() }.onFailure { it.printStackTrace() }
}

fun checkAll() {
    aoc2015.all()
    aoc2019.all()
    aoc2020.checkAll()
    aoc2021.checkAll()
}
