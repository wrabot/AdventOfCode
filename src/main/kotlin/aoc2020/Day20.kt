package aoc2020

import tools.Day
import kotlin.math.sqrt

class Day20(test: Int? = null) : Day(2020, 20, test) {
    override fun solvePart1() = corners.map { it.key.id }.distinct().reduce { acc, i -> acc * i }

    override fun solvePart2(): Any {
        // creates block image
        val blockImage = mutableListOf<Block>()
        blockImage.add(corners.filter { it.value[1] != null && it.value[3] != null }.keys.first())
        (0 until size - 1).forEach { blockImage.add(links.getValue(blockImage[it])[3]!!) }
        for (i in 0 until size * size - size) {
            blockImage.add(links.getValue(blockImage[i])[1]!!)
        }

        val blockIndices = 0..9

        /*
        val debugImage = blockImage.chunked(size).joinToString("\n\n") { row ->
            blockIndices.joinToString("\n") { index ->
                row.joinToString(" ") { it.image[index] }
            }
        }
        println(debugImage)
        println("-----------------")
        */

        val image = blockImage.chunked(size).flatMap { row ->
            blockIndices.map { index ->
                row.joinToString("") { it.image[index].drop(1).dropLast(1) }
            }.drop(1).dropLast(1)
        }
        //println(image.joinToString("\n"))

        val imageWeight = image.sumOf { line -> line.count { it == '#' } }
        val pattern = listOf("                  #", "#    ##    ##    ###", " #  #  #  #  #  #")
        val patternWeight = pattern.sumOf { line -> line.count { it == '#' } }
        val patternWidth = pattern.maxOf { it.length }
        var count = 0
        image.allOrientations().forEach { i ->
            for (y in 0 until image.size - pattern.size) {
                for (x in 0 until image.size - patternWidth) {
                    if (pattern[0].match(i[y], x) && pattern[1].match(i[y + 1], x) && pattern[2].match(
                            i[y + 2],
                            x
                        )
                    ) count++
                }
            }
        }
        //count.log()

        return imageWeight - count * patternWeight
    }

    data class Block(
        val id: Long,
        val image: List<String>,
        val top: String,
        val bottom: String,
        val left: String,
        val right: String
    )

    private val chunks = lines.chunked(12)
    private val size = sqrt(chunks.size.toFloat()).toInt()
    private val blocks = chunks.flatMap { data ->
        val id = data[0].split(" ", ":")[1].toLong()
        data.drop(1).dropLast(1).allOrientations().map {
            val flipD = it.flipD()
            Block(id, it, it.first(), it.last(), flipD.first(), flipD.last())
        }
    }
    private val links = blocks.associateWith { block ->
        listOf(
            blocks.find { it.id != block.id && it.bottom == block.top },
            blocks.find { it.id != block.id && it.top == block.bottom },
            blocks.find { it.id != block.id && it.right == block.left },
            blocks.find { it.id != block.id && it.left == block.right }
        )
    }
    private val corners = links.filter { it.value.countNotNull() == 2 }

    private fun List<Block?>.countNotNull() = count { it != null }
    private fun List<String>.allOrientations() = listOf(
        this,
        flipD(),
        flipV(),
        flipD().flipV(),
        flipH(),
        flipD().flipH(),
        flipV().flipH(),
        flipD().flipV().flipH()
    )

    private fun String.match(s: String, start: Int) =
        zip(s.substring(start)).all { it.first == ' ' || it.first == it.second }

    private fun List<String>.flipD() = indices.map { index -> joinToString("") { it[index].toString() } }
    private fun List<String>.flipH() = map { it.reversed() }
    private fun List<String>.flipV() = reversed()
}
