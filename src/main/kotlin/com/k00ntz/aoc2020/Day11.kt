package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.*

class Day11 : Day<List<List<Char>>, Int, Int> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") {
                it.toCharArray().toList()
            }
//            parse(getFileAsLineSequence("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<List<Char>>): Int {
        var inp = input
        var out = runModel(inp)
        while (inp != out) {
            inp = out
            out = runModel(inp)
        }
        return inp.flatMap { it.toList() }.filter { it == '#' }.size
    }

    private fun runModel(input: List<List<Char>>): List<List<Char>> =
        input.mapIndexed { y, chars ->
            chars.mapIndexed { x, c ->
                val occupiedNeighbors = Point(x, y).validAroundNeighbors(input
                    .map { it.toCharArray() }
                ) { it == '#' }
                when (c) {
                    '.' -> '.'
                    'L' -> if (occupiedNeighbors.isEmpty()) '#' else 'L'
                    '#' -> if (occupiedNeighbors.size >= 4) 'L' else '#'
                    else -> throw RuntimeException("Invalid character: $c")
                }
            }
        }

    override fun part2(input: List<List<Char>>): Int {
        var inp = input
        var out = runModel2(inp)
        while (inp != out) {
            println(inp.joinToString(separator = "\n"))
            println()
            inp = out
            out = runModel2(inp)
        }
        return inp.flatMap { it.toList() }.filter { it == '#' }.size
    }

    private fun runModel2(input: List<List<Char>>): List<List<Char>> {
        val anglePoints = setOf(
            Point(0, 1), Point(0, -1), Point(1, 0), Point(-1, 0),
            Point(1, 1), Point(1, -1), Point(-1, 1), Point(-1, -1)
        )
        val maxScale = maxOf(input.size, input.first().size)
        return input.mapIndexed { y, chars ->
            chars.mapIndexed { x, c ->
                val point = Point(x, y)
                val occupiedNeighbors = anglePoints.mapNotNull { pt ->
                    (1..maxScale).map { point + pt * it }
                        .firstOrNull {
                            it.y() >= 0 && it.y() < input.size
                                    && it.x() >= 0 && it.x() < input[it.y()].size
                                    && input[y][x] == '#'
                        }
                }
                    .toSet()
                if (y == 1 && x == 0)
                    println("stop")
                when (c) {
                    '.' -> '.'
                    'L' -> if (occupiedNeighbors.isEmpty()) '#' else 'L'
                    '#' -> if (occupiedNeighbors.size >= 5) 'L' else '#'
                    else -> throw RuntimeException("Invalid character: $c")
                }
            }
        }
    }

}

fun main() {
    println("Day 11")
    Day11().run()
}