package com.k00ntz.aoc2020

import com.k00ntz.aoc.utils.*
import java.util.*

class Day11 : Day<List<List<Char>>, Int, Int> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") {
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
            inp = out
            out = runModel2(inp)
        }
        return inp.flatMap { it.toList() }.filter { it == '#' }.size
    }

    private fun runModel2(input: List<List<Char>>): List<List<Char>> =
        input.mapIndexed { y, chars ->
            chars.mapIndexed { x, c ->
                val point = Point(x, y)
                val neighbors = point.getAngleNeighbors(input)
                val occupiedNeighbors = neighbors.filter { input[it.y()][it.x()] == '#' }
                when (c) {
                    '.' -> '.'
                    'L' -> if (occupiedNeighbors.isEmpty()) '#' else 'L'
                    '#' -> if (occupiedNeighbors.size >= 5) 'L' else '#'
                    else -> throw RuntimeException("Invalid character: $c")
                }
            }
        }
}

fun main() {
    println("Day 11")
    Day11().run()
}