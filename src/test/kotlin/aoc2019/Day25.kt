package aoc2019

import Day

class Day25(test: Int? = null) : Day(test) {
    override fun solvePart1(): String {
        // explore all rooms and retrieve all items
        var current = room(runtime.read()).explore()
        current = current.leave("north").explore()
        current = current.leave("north").explore()
        current = current.leave("east").explore()

        // go to security check point
        current = current.leave("west")
        current = current.leave("south")
        current = current.leave("south")
        current = current.leave("south")
        current = current.leave("south")
        current = current.leave("west")
        current.leave("south")

        val items = runtime.write("inv").lines().drop(1).items()
        for (i in 0..(1 shl items.size)) {
            items.forEachIndexed { index, item ->
                runtime.write(if (i shr index and 1 == 0) "drop $item" else "take $item")
            }
            val result = runtime.write("east")
            if (!result.contains("Alert!")) {
                return result.filter { it.isDigit() }
            }
        }
        return "not found"
    }

    private val forbiddenItems = listOf("molten lava", "infinite loop", "photons", "escape pod", "giant electromagnet")

    private val rooms = mutableMapOf<String, Room>()

    private fun Room.explore(): Room {
        var current = this
        while (true) {
            current = current.leave(current.nextDirection() ?: break)
        }
        return current
    }

    private fun Room.nextDirection() = directions.firstNotNullOfOrNull { if (it.value == null) it.key else null }

    private fun Room.leave(direction: String): Room {
        items.filterNot { it in forbiddenItems }.forEach { runtime.write("take $it") }
        return room(runtime.write(direction)).also { directions.getOrPut(direction) { it.name } }
    }

    private fun room(text: String) = text.lines().filter { it.isNotBlank() }.let { lines ->
        val name = lines[0].trim('=', ' ')
        rooms.getOrPut(name) {
            Room(
                name,
                lines[1],
                lines.dropWhile { it != "Doors here lead:" }.drop(1).items().sortedDescending().associateWith { null }
                    .toMutableMap(),
                lines.dropWhile { it != "Items here:" }.drop(1).items(),
            )
        }
    }

    private fun List<String>.items() = takeWhile { it.startsWith("- ") }.map { it.removePrefix("- ") }

    private data class Room(
        val name: String,
        val description: String,
        val directions: MutableMap<String, String?>,
        val items: List<String>
    )

    override fun solvePart2() = Unit

    private fun Day9.Runtime.write(s: String): String {
        s.forEach { execute(it.code.toLong()) }
        execute('\n'.code.toLong())
        return read()
    }

    private fun Day9.Runtime.read(): String {
        val s = StringBuilder()
        while (true) {
            val o = execute(null) ?: break
            s.append(if (o < 127) o.toInt().toChar() else o.toString())
        }
        return s.toString()
    }

    val code = lines.first().split(",").map { it.toLong() }
    val runtime = Day9.Runtime(code)
}


