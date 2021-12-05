import org.junit.Test

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
        val oxygen = lines.day3Part2('0', '1')
        val co2 = lines.day3Part2('1', '0')
        (oxygen * co2).log()
    }

    private fun List<String>.day3Part2(mostZeroBit: Char, leastOrEqualZeroBit: Char): Int {
        val length = this[0].length
        var current = this
        for (position in 0 until length) {
            if (current.size == 1) break
            val threshold = current.size / 2
            val bit = if (current.count { it[position] == '0' } > threshold) mostZeroBit else leastOrEqualZeroBit
            current = current.filter { it[position] == bit }
        }
        return current.first().toInt(2)
    }

    @Test
    fun day4() = test(1, 2) { lines ->
        val numbers = lines[0].split(",").map { it.toInt() }

        val boards = lines.drop(1).chunked(6).map { board ->
            Day4Board(board.drop(1).map { row ->
                row.chunked(3).map { Day4Board.Cell(it.trim().toInt()) }
            })
        }

        day4part1(numbers, boards)
        day4part2(numbers, boards)
    }

    private fun day4part1(numbers: List<Int>, boards: List<Day4Board>) {
        numbers.forEach { number ->
            boards.forEach { board ->
                board.draw(number)
                if (board.isComplete) {
                    val score = board.score() * number
                    score.log()
                    return
                }
            }
        }
    }

    private fun day4part2(numbers: List<Int>, boards: List<Day4Board>) {
        numbers.forEach { number ->
            boards.forEach { board ->
                board.draw(number)
                if (board.isComplete && boards.all { it.isComplete }) {
                    val score = board.score() * number
                    score.log()
                    return
                }
            }
        }
    }

    data class Day4Board(val rows: List<List<Cell>>) {
        private val size = 5
        private val cells: List<Cell> = rows.flatten()
        private val columns: List<List<Cell>> = (0 until size).map { column -> rows.map { it[column] } }
        var isComplete = false
            private set

        data class Cell(val value: Int, var marked: Boolean = false)

        fun draw(number: Int) {
            if (isComplete) return
            cells.forEach {
                if (it.value == number)
                    it.marked = true
            }
            isComplete = rows.any { it.isComplete() } || columns.any { it.isComplete() }
        }

        fun score() = cells.sumBy { if (it.marked) 0 else it.value }

        private fun List<Cell>.isComplete() = all { it.marked }
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

}
