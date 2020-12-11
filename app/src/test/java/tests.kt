fun resource(name: String) = name.javaClass.getResource(name)!!.readText()

fun test(name: String, count: Int, block: (List<String>) -> Any) {
    log("start test $name")
    for (index in 1..count) {
        log("start test $index")
        block(resource("/$name/input$index.txt").lines())
    }
}

fun log(message: Any?) = System.err.println("!!!$message")

fun <T> T.log() = apply { log(this) }
