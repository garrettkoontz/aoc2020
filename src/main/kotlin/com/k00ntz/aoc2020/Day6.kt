package com.k00ntz.aoc2020

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.getFile
import com.k00ntz.aoc.utils.groupSeparatedByEmpty
import com.k00ntz.aoc.utils.measureAndPrintTime
import java.util.*

class Day6 : Day<List<List<String>>, Int, Int> {
    override fun run() {
        val inputFile =
//            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
            groupSeparatedByEmpty(getFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<List<String>>): Int =
        input.map { it.flatMap { it.toList().filter { !it.isWhitespace() } }.toSet() }.sumOf { it.size }

    override fun part2(input: List<List<String>>): Int =
        input.sumOf { group ->
            group.map { it.toCharArray().toSet() }.reduce { acc: Set<Char>, set: Set<Char> ->
                acc.intersect(set)
            }.size
        }
}

fun main() {
    println("Day 6")
    Day6().run()
}