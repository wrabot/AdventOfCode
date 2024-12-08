#!/usr/bin/env kotlin

import java.io.File
import java.net.URL

val (year, day, session) = args

val input = URL("https://adventofcode.com/$year/day/$day/input").openConnection().apply {
    setRequestProperty("Cookie", "session=$session")
}.getInputStream().reader().readText().trim()

println(input)

File("src/test/resources/aoc$year/day$day/input.txt").writeText(input)
