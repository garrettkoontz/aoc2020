package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.Day
import com.k00ntz.aoc2020.utils.measureAndPrintTime
import com.k00ntz.aoc2020.utils.parseFile
import java.util.*

class Day10 : Day<List<Int>, Int, Long> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
//            parse(getFileAsLineSequence("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<Int>): Int {
        val sortedInput = input.sorted()
        val diffs = sortedInput.zipWithNext().map { (a, b) -> b - a }.groupBy { it }
        val (oneDiff, threeDiff) = when (sortedInput.minOfOrNull { it }) {
            1 -> Pair(1, 0)
            2 -> Pair(0, 0)
            3 -> Pair(0, 1)
            else -> throw RuntimeException("initial diff too large!")
        }
        return (diffs[1]?.size?.plus(oneDiff) ?: 0) * (diffs[3]?.size?.plus(1 + threeDiff) ?: 0)
    }

    override fun part2(input: List<Int>): Long {
        val sortedInput = input.toSortedSet()

        val windows = sortedInput.windowed(3)


        val startAdapter = sortedInput.maxByOrNull { it }!! + 3
        return countValidCombos(sortedInput, startAdapter, 1L)
    }

    fun countValidCombos(inp: SortedSet<Int>, previousAdapter: Int, currentTotal: Long): Long {
        if (previousAdapter == 0) return 1L
        val nextValids =
            (1..3).map { diff ->
                (previousAdapter - diff).let {
                    if (it < 0) 0 else it
                }
            }.filter {
                it == 0 || inp.contains(it)
            }
        if (nextValids.isEmpty()) return 0L
        else return currentTotal * nextValids.map { countValidCombos(inp, it) }.sum()
    }
}

fun main() {
    println("Day 10")
    Day10().run()
}