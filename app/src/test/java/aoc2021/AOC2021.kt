package aoc2021

import BaseTest
import Block
import Board
import Point
import log
import org.junit.Test
import kotlin.math.absoluteValue

class AOC2021 : BaseTest("AOC2021") {
    @Test
    fun day1() = test(1, 2) { lines ->
        val depths = lines.map { it.toInt() }
        //part1
        depths.zipWithNext().count { it.first < it.second }.log()
        //part2
        depths.windowed(3).map { it.sum() }.zipWithNext().count { it.first < it.second }.log()
    }

    @Test
    fun day2() = test(1, 2) { lines ->
        val orders = lines.map {
            val (action, value) = it.split(" ")
            action to value.toInt()
        }

        var position = 0
        var depth = 0

        //part1
        orders.forEach {
            when (it.first) {
                "forward" -> position += it.second
                "up" -> depth -= it.second
                "down" -> depth += it.second
            }
        }
        (position * depth).log()

        //part2
        position = 0
        depth = 0
        var aim = 0
        orders.forEach {
            when (it.first) {
                "forward" -> {
                    position += it.second
                    depth += it.second * aim
                }
                "up" -> aim -= it.second
                "down" -> aim += it.second
            }
        }
        (position * depth).log()
    }

    @Test
    fun day3() = test(1, 2) { lines ->
        //part1
        val length = lines[0].length
        val threshold = lines.size / 2
        val gammaBits = (0 until length).map { position ->
            if (lines.count { it[position] == '1' } > threshold) '1' else '0'
        }.joinToString("")
        val epsilonBits = gammaBits.map { if (it == '1') '0' else '1' }.joinToString("")
        (gammaBits.toInt(2) * epsilonBits.toInt(2)).log()

        //part2
        fun rating(mostZeroBit: Char, leastOrEqualZeroBit: Char): Int {
            var current = lines
            for (position in 0 until length) {
                if (current.size == 1) break
                val currentThreshold = current.size / 2
                val bit = if (current.count { it[position] == '0' } > currentThreshold) mostZeroBit else leastOrEqualZeroBit
                current = current.filter { it[position] == bit }
            }
            return current.first().toInt(2)
        }

        val oxygen = rating('0', '1')
        val co2 = rating('1', '0')
        (oxygen * co2).log()
    }

    @Test
    fun day4() = test(1, 2) { lines ->
        val numbers = lines[0].split(",").map { it.toInt() }

        data class Cell(val value: Int, var marked: Boolean = false)

        val boards = lines.drop(1).chunked(6).map { board ->
            val rows = board.drop(1).map { row -> row.chunked(3).map { Cell(it.trim().toInt()) } }
            val columns = (0 until 5).map { column -> rows.map { it[column] } }
            Board(5, 5, rows.flatten()) to (rows + columns)
        }.toMap()

        val completeBoards = mutableSetOf<Board<Cell>>()
        numbers.forEach { number ->
            boards.forEach { board ->
                board.key.cells.forEach { if (it.value == number) it.marked = true }
                if (board.value.any { line -> line.all { it.marked } }) {
                    val score = board.key.cells.sumOf { if (it.marked) 0 else it.value } * number
                    if (completeBoards.isEmpty()) score.log() // part 1
                    if (completeBoards.size == boards.size - 1 && board.key !in completeBoards) score.log() // part 2
                    completeBoards.add(board.key)
                }
            }
        }
    }

    @Test
    fun day5() = test(1, 2) { lines ->
        val segments = lines.map { line ->
            val (start, end) = line.split(" -> ").map { point ->
                val (x, y) = point.split(",").map { it.toInt() }
                Point(x, y)
            }
            start to end
        }

        val board = mutableMapOf<Point, Int>()
        fun MutableMap<Point, Int>.add(point: Point) = put(point, getOrDefault(point, 0) + 1)
        fun range(start: Int, end: Int) = if (start < end) start..end else end..start

        // part1
        segments.forEach { segment ->
            if (segment.first.x == segment.second.x) {
                range(segment.first.y, segment.second.y).forEach { board.add(Point(segment.first.x, it)) }
            } else if (segment.first.y == segment.second.y) {
                range(segment.first.x, segment.second.x).forEach { board.add(Point(it, segment.first.y)) }
            }
        }
        board.count { it.value > 1 }.log()

        // part1
        segments.forEach { segment ->
            val width = segment.second.x - segment.first.x
            val height = segment.second.y - segment.first.y
            if (width == height || width == -height) {
                if (width > 0 && height > 0) {
                    (0..width).forEach { board.add(Point(segment.first.x + it, segment.first.y + it)) }
                } else if (width > 0 && height < 0) {
                    (0..width).forEach { board.add(Point(segment.first.x + it, segment.first.y - it)) }
                } else if (width < 0 && height > 0) {
                    (0..-width).forEach { board.add(Point(segment.first.x - it, segment.first.y + it)) }
                } else if (width < 0 && height < 0) {
                    (0..-width).forEach { board.add(Point(segment.first.x - it, segment.first.y - it)) }
                }
            }
        }
        board.count { it.value > 1 }.log()
    }

    @Test
    fun day6() = test(1, 2) { lines ->
        var generation = LongArray(9) { 0 }
        lines[0].split(",").map { generation[it.toInt()]++ }
        repeat(256) {
            generation = LongArray(9) { timer ->
                when (timer) {
                    8 -> generation[0]
                    6 -> generation[0] + generation[7]
                    else -> generation[timer + 1]
                }
            }
            if (it == 79) generation.sum().log() // part1
        }
        generation.sum().log() // part2
    }

    @Test
    fun day7() = test(1, 2) { lines ->
        val positions = lines[0].split(",").map { it.toInt() }.groupingBy { it }.eachCount()
        val min = positions.minOf { it.key }
        val max = positions.maxOf { it.key }

        // part1
        (min..max).minOf { target ->
            positions.entries.sumOf {
                (it.key - target).absoluteValue * it.value
            }
        }.log()

        // part2
        (min..max).minOf { target ->
            positions.entries.sumOf {
                val n = (it.key - target).absoluteValue
                n * (n + 1) / 2 * it.value
            }
        }.log()
    }

    @Test
    fun day8() = test(1, 2) { lines ->
        val entries = lines.map { line ->
            val (input, output) = line.split(" | ")
            input.split(" ").map { it.toList().sorted() } to
                    output.split(" ").map { it.toList().sorted() }
        }

        // part 1
        entries.sumOf { entry -> entry.second.count { it.size in listOf(2, 3, 4, 7) } }.log()

        //part2
        entries.sumOf { entry ->
            val digits = mutableMapOf<Int, List<Char>>()
            digits[1] = entry.first.first { it.size == 2 }
            digits[7] = entry.first.first { it.size == 3 }
            digits[4] = entry.first.first { it.size == 4 }
            digits[8] = entry.first.first { it.size == 7 }
            digits[9] = entry.first.first { it.size == 6 && it.containsAll(digits[4]!!) }
            digits[0] = entry.first.first { it.size == 6 && it != digits[9] && it.containsAll(digits[1]!!) }
            digits[6] = entry.first.first { it.size == 6 && it != digits[9] && it != digits[0] }
            digits[5] = entry.first.first { it.size == 5 && digits[6]!!.containsAll(it) }
            digits[3] = entry.first.first { it.size == 5 && it != digits[5] && digits[9]!!.containsAll(it) }
            digits[2] = entry.first.first { it.size == 5 && it != digits[5] && it != digits[3] }
            val dictionary = digits.map { it.value to it.key }.toMap()
            entry.second.map { dictionary[it] }.joinToString("").toInt()
        }.log()

    }

    @Test
    fun day9() = test(1, 2) { lines ->
        val board = Board(lines[0].length, lines.size, lines.flatMap { line -> line.map { it.toString().toInt() } })

        val lowPoints = board.points.filter { point ->
            val height = board[point]
            board.neighbors4(point).all { board[it] > height }
        }

        // part1
        lowPoints.sumOf { board[it] + 1 }.log()

        // part2
        lowPoints.map { lowPoint -> board.zone4(lowPoint) { board[it] != 9 }.count() }
            .sortedDescending().take(3).reduce(Int::times).log()
    }


    @Test
    fun day10() = test(1, 2) { lines ->
        val simplifiedChunks = lines.map { line ->
            var chunk = line
            while (true) {
                val length = chunk.length
                chunk = chunk.replace("()", "")
                    .replace("[]", "")
                    .replace("{}", "")
                    .replace("<>", "")
                if (length == chunk.length) break
            }
            chunk.toList()
        }

        //part1
        simplifiedChunks.sumOf { chunk ->
            when (chunk.find { it in listOf(')', ']', '}', '>') }) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> 0  // not corrupted
            }.toInt()
        }.log()

        //part2
        val scores = simplifiedChunks.mapNotNull { chunk ->
            runCatching {
                chunk.reversed().fold(0) { acc, c ->
                    acc * 5 + when (c) {
                        '(' -> 1
                        '[' -> 2
                        '{' -> 3
                        '<' -> 4
                        else -> throw Error("Corrupted")
                    }
                }
            }.getOrNull()
        }.sorted()
        scores[scores.size / 2].log()
    }

    @Test
    fun day11() = test(1, 2) { lines ->
        data class Cell(var level: Int) {
            var flash = false
            override fun toString() = level.toString()
        }

        val board = Board(lines[0].length, lines.size, lines.flatMap { line -> line.map { Cell(it.toString().toInt()) } })

        var flashes = 0
        fun step() {
            board.cells.forEach { it.level++ }
            do {
                var flash = false
                board.points.forEach { point ->
                    val cell = board[point]
                    if (!cell.flash && cell.level > 9) {
                        cell.flash = true
                        flash = true
                        flashes++
                        board.neighbors8(point).forEach { board[it].level++ }
                    }
                }
            } while (flash)
            board.cells.forEach {
                if (it.flash) {
                    it.flash = false
                    it.level = 0
                }
            }
        }

        // part1
        repeat(100) { step() }
        flashes.log()

        // part2
        var step = 100
        while (board.cells.any { it.level != 0 }) {
            step()
            step++
        }
        step.log()
    }

    @Test
    fun day12() = test(1, 2, 3, 4) { lines ->
        val links = lines.flatMap { it.split("-").let { (a, b) -> listOf(a to b, b to a) } }
            .filter { it.second != "start" }

        fun countPaths(path: List<String>, acceptTwoSmallInPath: Boolean): Int =
            links.sumOf {
                when {
                    it.first != path.last() -> 0
                    it.second == "end" -> 1
                    it.second[0].isUpperCase() || it.second !in path -> countPaths(path + it.second, acceptTwoSmallInPath)
                    acceptTwoSmallInPath -> countPaths(path + it.second, false)
                    else -> 0
                }
            }

        countPaths(listOf("start"), false).log()
        countPaths(listOf("start"), true).log()
    }

    @Test
    fun day13() = test(1, 2) { lines ->
        val dots = lines.takeWhile { it != "" }.map { line -> line.split(",").map { it.toInt() } }
        val folds = lines.takeLastWhile { it != "" }.map {
            it.split("=").let { (a, b) -> a.removePrefix("fold along ") to b.toInt() }
        }

        fun List<Pair<String, Int>>.foldDots() = fold(dots) { acc, f ->
            val index = if (f.first == "x") 0 else 1
            val value = f.second
            acc.mapNotNull { dot ->
                when (dot[index].compareTo(value)) {
                    -1 -> dot[index]
                    1 -> 2 * value - dot[index]
                    else -> null
                }?.let { if (index == 0) listOf(it, dot[1]) else listOf(dot[0], it) }
            }.distinct()
        }

        // part 1
        folds.take(1).foldDots().count().log()

        // part 2
        val code = folds.foldDots()
        val width = code.maxOf { it[0] } + 1
        val height = code.maxOf { it[1] } + 1
        List(width * height) {
            if (listOf(it % width, it / width) in code) '#' else ' '
        }.log(width)
    }

    @Test
    fun day14() = test(1, 2) { lines ->
        val template = lines[0]
        val rules = lines.drop(2).map {
            it.split(" -> ").let { (pattern, insert) -> pattern.zipWithNext().first() to insert[0] }
        }.toMap()

        var generation = template.zipWithNext().groupingBy { it }.eachCount().mapValues { it.value.toLong() }

        fun <T> List<Pair<T, Long>>.cumulate() = groupBy { it.first }.mapValues { entry -> entry.value.sumOf { it.second } }

        fun difference() = (generation.map { it.key.first to it.value } + (template.last() to 1L))
            .cumulate().values.sorted().let { it.last() - it.first() }

        repeat(40) { count ->
            generation = generation.flatMap {
                val insert = rules[it.key]
                if (insert == null) listOf(it.key to it.value) else listOf((it.key.first to insert) to it.value, (insert to it.key.second) to it.value)
            }.cumulate()
            if (count == 9) difference().log() // part 1
        }
        difference().log() // part 2
    }

    @Test
    fun day15() = test(1, 2) { lines ->
        data class Cell(val risk: Int, var minRisk: Int = Int.MAX_VALUE) {
            override fun toString() = risk.toString()
        }

        fun minRisk(cave: Board<Cell>): Int {
            val start = cave.points.first()
            val end = cave.points.last()
            cave[start].minRisk = 0

            val todo = mutableListOf(start)
            while (todo.isNotEmpty()) {
                val point = todo.removeAt(0)
                val minRisk = cave[point].minRisk
                cave.neighbors4(point).forEach {
                    val neighbor = cave[it]
                    val newRisk = neighbor.risk + minRisk
                    if (newRisk < neighbor.minRisk) {
                        neighbor.minRisk = newRisk
                        if (it !in todo) todo.add(it)
                    }
                }
                todo.sortBy { cave[it].minRisk }
            }
            return cave[end].minRisk
        }

        minRisk(Board(lines[0].length, lines.size, lines.flatMap { line -> line.map { Cell(it.toString().toInt()) } })).log()

        minRisk(
            Board(
                lines[0].length * 5,
                lines.size * 5,
                (0..4).flatMap { row ->
                    lines.flatMap { line ->
                        (0..4).flatMap { column ->
                            line.map { Cell((it.toString().toInt() + column + row - 1) % 9 + 1) }
                        }
                    }
                })
        ).log()
    }

    @Test
    fun day16() = test(3) { lines ->
        open class Packet
        data class Literal(val version: Int, val value: Long) : Packet()
        data class Operator(val version: Int, val type: Int, val packets: List<Packet>) : Packet()
        data class ParseResult(val packet: Packet, val offset: Int)

        fun parsePacket(input: String): ParseResult {
            val version = input.substring(0, 3).toInt(2)
            val type = input.substring(3, 6).toInt(2)
            val packetPayload = input.substring(6)
            var offset = 6
            return when (type) {
                4 -> {
                    val blocks = packetPayload.chunked(5)
                    val count = blocks.indexOfFirst { it.startsWith('0') } + 1
                    val value = blocks.take(count).joinToString("") { it.drop(1) }.toLong(2)
                    ParseResult(Literal(version, value), offset + count * 5)
                }
                else -> {
                    val packets = mutableListOf<Packet>()
                    if (packetPayload[0] == '0') {
                        val length = packetPayload.substring(1, 16).toInt(2)
                        offset += 16
                        var operatorPayload = packetPayload.substring(16, 16 + length)
                        offset += length
                        while (operatorPayload.isNotEmpty()) {
                            val result = parsePacket(operatorPayload)
                            packets.add(result.packet)
                            operatorPayload = operatorPayload.substring(result.offset)
                        }
                    } else {
                        val count = packetPayload.substring(1, 12).toInt(2)
                        offset += 12
                        var operatorPayload = packetPayload.substring(12)
                        repeat(count) {
                            val result = parsePacket(operatorPayload)
                            packets.add(result.packet)
                            operatorPayload = operatorPayload.substring(result.offset)
                            offset += result.offset
                        }
                    }
                    ParseResult(Operator(version, type, packets), offset)
                }
            }
        }

        fun Packet.sumVersions(): Int = when (this) {
            is Literal -> version
            is Operator -> version + packets.sumOf { it.sumVersions() }
            else -> 0
        }

        fun Packet.evaluate(): Long = when (this) {
            is Literal -> value
            is Operator -> when (type) {
                0 -> packets.sumOf { it.evaluate() }
                1 -> packets.fold(1L) { acc, value -> acc * value.evaluate() }
                2 -> packets.minOf { it.evaluate() }
                3 -> packets.maxOf { it.evaluate() }
                5 -> if (packets[0].evaluate() > packets[1].evaluate()) 1 else 0
                6 -> if (packets[0].evaluate() < packets[1].evaluate()) 1 else 0
                7 -> if (packets[0].evaluate() == packets[1].evaluate()) 1 else 0
                else -> throw Error("invalid operator type")
            }
            else -> throw Error("invalid packet type")
        }

        lines.map {
            val bits = it.log().map { digit ->
                digit.toString().toInt(16).toString(2).padStart(4, '0')
            }.joinToString("")
            val packet = parsePacket(bits).packet
            packet.sumVersions().log()
            packet.evaluate().log()
        }
    }

    @Test
    fun day17() {
        fun maxY(vxInit: Int, vyInit: Int, xTarget: IntRange, yTarget: IntRange): Int? {
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

        fun findBestMaxY(xTarget: IntRange, yTarget: IntRange): Int {
            var bestMaxY = 0
            for (vy in 0..xTarget.last) {
                for (vx in 0..xTarget.last) {
                    bestMaxY = maxY(vx, vy, xTarget, yTarget)?.coerceAtLeast(bestMaxY) ?: bestMaxY
                }
            }
            return bestMaxY
        }

        fun countVelocities(xTarget: IntRange, yTarget: IntRange): Int {
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

        //findBestMaxY(20..30, -10..-5).log()
        log("part 1: " + findBestMaxY(102..157, -146..-90))
        //countVelocities(20..30, -10..-5).log()
        log("part 2: " + countVelocities(102..157, -146..-90))
    }

    @Test
    fun day18() = test(1, 2) { Day18.solve(it) }

    @Test
    fun day19() = test(1, 2) { lines ->
        val scanners = lines.fold(emptyList<List<Point>>()) { scanners, line ->
            when {
                line.isEmpty() -> scanners
                line.startsWith("---") -> scanners.plusElement(mutableListOf())
                else -> scanners.dropLast(1).plusElement(
                    scanners.last() + line.split(",").let { (x, y, z) -> Point(x.toInt(), y.toInt(), z.toInt()) }
                )
            }
        }

        // 48 but really 24
        fun List<Point>.rotate() =
            (1..3).runningFold(this) { points, _ -> points.map(Point::rotateX) }
                .flatMap { (1..3).runningFold(it) { points, _ -> points.map(Point::rotateY) } }
                .flatMap { (1..2).runningFold(it) { points, _ -> points.map(Point::rotateZ) } }
                //.apply { count().log() }
                .distinct()
        //.apply { count().log() }


        val rotateScanners = scanners.drop(1).map { it.rotate() }.toMutableList()

        fun findScanner(currentScanner: Set<Point>, start: Int): Triple<Int, Point, List<Point>>? {
            for (index in start until rotateScanners.size) {
                rotateScanners[index].forEach { beacons ->
                    currentScanner.drop(11).forEach { refBeacon ->
                        beacons.drop(11).forEach { beacon ->
                            val position = refBeacon - beacon
                            if (beacons.count { it + position in currentScanner } >= 12) {
                                return Triple(index, position, beacons.map { it + position })
                            }
                        }
                    }
                }
            }
            return null
        }

        log("part 1: ")
        val done = mutableListOf(Point(0, 0, 0) to scanners[0])
        val toTry = mutableListOf(scanners[0])
        while (toTry.isNotEmpty()) {
            val current = toTry.removeAt(0).toSet()
            var start = 0
            while (true) {
                val result = findScanner(current, start) ?: break
                rotateScanners.removeAt(result.first)
                start = result.first
                done.add(result.second to result.third)
                toTry.add(result.third)
            }
        }
        done.flatMap { it.second }.distinct().count().log()

        log("part 2: ")
        val positions = done.map { it.first }
        positions.maxOf { a ->
            positions.maxOf {
                (it.x - a.x).absoluteValue + (it.y - a.y).absoluteValue + (it.z - a.z).absoluteValue
            }
        }.log()
    }

    @Test
    fun day20() = test(1, 2) { lines ->
        fun createImage(rows: List<String>, outside: Char): Board<Char> {
            val width = rows[0].length + 2
            val separator = outside.toString().repeat(width).toList()
            return Board(width, rows.size + 2, separator + rows.flatMap { "$outside$it$outside".toList() } + separator)
        }

        fun Board<Char>.displayResult() = cells.count { it == '#' }.log()

        fun Board<Char>.enhance(algo: String): Board<Char> {
            val outside = cells[0]
            val rows = points.map { point ->
                (-1..1).flatMap { dy -> (-1..1).map { dx -> getOrNull(point.x + dx, point.y + dy) ?: outside } }
                    .joinToString("") { if (it == '#') "1" else "0" }
                    .toInt(2)
                    .let { algo[it] }
            }.chunked(width) { it.joinToString("") }
            return createImage(rows, if ((algo[0] == '#') xor (outside == '#')) '#' else '.')
        }

        val algo = lines[0]
        var image = createImage(lines.drop(2), '.')

        log("part 1: ")
        repeat(2) { image = image.enhance(algo) }
        image.displayResult()

        log("part 2: ")
        repeat(48) { image = image.enhance(algo) }
        image.displayResult()
    }

    @Test
    fun day21() = test(1) { _ ->
        log("part 1: ")
        data class Player(var position: Int, var score: Int = 0) {
            fun move(dice: Int) {
                position = (position + dice - 1) % 10 + 1
                score += position
            }
        }

        //val player1 = Player(4)
        //val player2 = Player(8)
        val player1 = Player(10)
        val player2 = Player(9)

        var round = 1
        while (true) {
            player1.move(round * 9 - 3)
            if (player1.score >= 1000) {
                (player2.score * round * 3).log()
                break
            }
            round++

            player2.move(round * 9 - 3)
            if (player2.score >= 1000) {
                (player1.score * round * 3).log()
                break
            }
            round++
        }


        log("part 2: ")
        data class PlayerState(val position: Int, val score: Int = 0)
        data class GameState(val players: List<PlayerState>)

        //var universes = mapOf(GameState(listOf(PlayerState(4), PlayerState(8))) to 1L)
        var universes = mapOf(GameState(listOf(PlayerState(10), PlayerState(9))) to 1L)
        val diceSums = (1..3).flatMap { dice1 -> (1..3).flatMap { dice2 -> (1..3).map { dice3 -> dice1 + dice2 + dice3 } } }
        val playerWins = Array(2) { 0L }
        var currentPlayerIndex = 0
        while (universes.isNotEmpty()) {
            val next = mutableMapOf<GameState, Long>()
            universes.forEach { universe ->
                val currentPlayer = universe.key.players[currentPlayerIndex]
                diceSums.map {
                    val position = (currentPlayer.position + it - 1) % 10 + 1
                    val score = currentPlayer.score + position
                    when {
                        score >= 21 -> playerWins[currentPlayerIndex] += universe.value
                        else -> {
                            val gameState = GameState(universe.key.players.mapIndexed { index, playerState ->
                                if (index == currentPlayerIndex) PlayerState(position, score) else playerState
                            })
                            next[gameState] = next.getOrDefault(gameState, 0L) + universe.value
                        }
                    }
                }
            }
            universes = next
            currentPlayerIndex = (currentPlayerIndex + 1) % 2
        }
        playerWins.maxOrNull().log()
    }


    @Test
    fun day22() = test(1, 2) { lines ->
        data class Step(val mode: Boolean, val cuboid: Block)

        val procedure = lines.map { line ->
            "(on|off) x=(-?\\d*)\\.\\.(-?\\d*),y=(-?\\d*)\\.\\.(-?\\d*),z=(-?\\d*)\\.\\.(-?\\d*)".toRegex()
                .matchEntire(line)?.groupValues.orEmpty().drop(1).let {
                    Step(
                        it[0] == "on",
                        Block(
                            Point(it[1].toInt(), it[3].toInt(), it[5].toInt()),
                            Point(it[2].toInt(), it[4].toInt(), it[6].toInt())
                        )
                    )
                }
        }

        log("part 1: ")
        val reactorSize = 50
        val reactorFullSize = reactorSize * 2 + 1
        val reactor = Array(reactorFullSize * reactorFullSize * reactorFullSize) { false }
        val reactorBlock = Block(Point(-reactorSize, -reactorSize, -reactorSize), Point(reactorSize, reactorSize, reactorSize))
        procedure.forEach {
            val block = it.cuboid.intersect(reactorBlock)
            if (block != null)
                for (x in block.start.x..block.end.x)
                    for (y in block.start.y..block.end.y)
                        for (z in block.start.z..block.end.z)
                            reactor[((z + reactorSize) * reactorFullSize + (y + reactorSize)) * reactorFullSize + (x + reactorSize)] = it.mode
        }
        reactor.count { it }.log()

        log("part 2: ")
        fun List<Block>.sum(): Long =
            if (isEmpty()) 0 else first().size() + drop(1).sum() - drop(1).mapNotNull { it.intersect(first()) }.sum()

        var count = 0L
        val exclusions = mutableListOf<Block>()
        procedure.reversed().forEach { step ->
            if (step.mode) {
                count += step.cuboid.size() - exclusions.mapNotNull { it.intersect(step.cuboid) }.sum()
            }
            exclusions.add(step.cuboid)
        }
        count.log()
    }

    @Test
    fun day23() = test(1, 2) { lines ->
        log("part1: ")
        log("part2: ")
    }

    @Test
    fun day24() = test(1, 2) { lines ->
        log("part1: ")
        log("part2: ")
    }

    @Test
    fun day25() = test(1, 2) { lines ->
        log("part 1: ")
        log("part 2: ")
    }
}
