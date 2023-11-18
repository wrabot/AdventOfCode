fun main() {
    runCatching { checkAll() }.onFailure { it.printStackTrace() }
}

fun checkAll() {
    //aoc2015.checkAll()
    //aoc2019.checkAll()
    //aoc2020.checkAll()
    //aoc2021.checkAll()
    //aoc2022.checkAll()
    aoc2023.checkAll()
}
