package com.k00ntz.aoc2020

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseFile
import java.util.*

class Day10 : Day<List<Int>, Int, Long> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { Integer.parseInt(it) }
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
        val sortedInput = listOf(0, *input.toTypedArray(), input.maxByOrNull { it }!!).sorted()
        val countMap = mutableMapOf(0 to 1L)
        sortedInput.dropWhile { countMap.containsKey(it) }.forEach {
            countMap[it] = (1..3).sumOf { sub -> countMap.getOrDefault(it - sub, 0) }
        }
        return countMap[sortedInput.last()]!!
    }

}

fun main() {
    println("Day 10")
    Day10().run()
}