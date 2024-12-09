package aoc2024

import Day

class Day9(test: Int? = null) : Day(test) {
    override fun solvePart1(): Long {
        val map = (input + '0').map { it - '0' }
        val disk = mutableListOf<Long>()
        var id = 0L
        map.chunked(2).forEach {
            repeat(it[0]) { disk.add(id) }
            repeat(it[1]) { disk.add(-1) }
            id++;
        }
        var start = 0
        var end = disk.lastIndex
        while (start < end) when {
            disk[start] != -1L -> start++
            disk[end] == -1L -> end--
            else -> disk[start++] = disk[end--]
        }
        return disk.take(end + 1).reduceIndexed { index, acc, i -> acc + index * i }
    }

    override fun solvePart2(): Long {
        var address = 0
        val files = mutableListOf<File>()
        val spaces = mutableListOf<Space>()
        (input + '0').map { it - '0' }.chunked(2).forEachIndexed { id, (fileLength, spaceLength) ->
            files.add(File(id, address, fileLength))
            address += fileLength
            spaces.add(Space(address, spaceLength))
            address += spaceLength
        }
        for (file in files.reversed()) {
            val space = spaces.find { it.address < file.address && it.length >= file.length } ?: continue
            file.address = space.address
            space.address += file.length
            space.length -= file.length
        }
        return files.sumOf { it.id.toLong() * it.length * (2 * it.address + it.length - 1) / 2 }
    }

    private data class File(val id: Int, var address: Int, val length: Int)
    private data class Space(var address: Int, var length: Int)
}
