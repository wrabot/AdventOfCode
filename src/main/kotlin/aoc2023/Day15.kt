package aoc2023

import Day

class Day15(test: Int? = null) : Day(test) {
    override fun solvePart1() = input.split(',').sumOf { it.hash() }
    override fun solvePart2(): Int {
        val boxes = Array(256) { mutableListOf<Pair<String, Int>>() }
        input.split(',').forEach { step ->
            val split = step.split('-', '=')
            val label = split[0]
            val box = boxes[label.hash()]
            val boxIndex = box.indexOfFirst { it.first == label }
            if (split[1].isNotEmpty()) {
                val lens = label to split[1].toInt()
                if (boxIndex >= 0) box[boxIndex] = lens else box.add(lens)
            } else {
                if (boxIndex >= 0) box.removeAt(boxIndex)
            }
        }
        return boxes.mapIndexed { boxIndex, box ->
            (boxIndex + 1) * box.mapIndexed { lensIndex, lens ->
                (lensIndex + 1) * lens.second
            }.sum()
        }.sum()
    }

    private fun String.hash() = fold(0) { acc, c -> (acc + c.code) * 17 % 256 }
}
