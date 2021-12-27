import tools.log

fun Any.forEachInput(year: Int, day: Int, vararg inputs: Int, block: (List<String>) -> Unit) {
    inputs.forEach {
        log("load year $year day $day input $it")
        block(javaClass.classLoader!!.getResource("aoc$year/day$day/input$it.txt")!!.readText().lines())
    }
}

fun <T> repeat(count: Int, init: T, next: (T) -> T): T {
    var t = init
    repeat(count) { t = next(t) }
    return t
}
