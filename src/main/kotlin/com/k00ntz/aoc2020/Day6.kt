package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.Day
import com.k00ntz.aoc2020.utils.getFile
import com.k00ntz.aoc2020.utils.groupSeparatedByEmpty
import com.k00ntz.aoc2020.utils.measureAndPrintTime

class Day6 : Day<List<List<String>>, Int, Int> {
    override fun run() {
        val inputFile =
//            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
            groupSeparatedByEmpty(getFile("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<List<String>>): Int =
        input.map { it.flatMap { it.toList().filter { !it.isWhitespace() } }.toSet() }
            .map { it.size }.sum()

    override fun part2(input: List<List<String>>): Int =
        input.map { group ->
            group.map { it.toCharArray().toSet() }.reduce { acc: Set<Char>, set: Set<Char> ->
                acc.intersect(set)
            }.size
        }.sum()
}

fun main() {
    println("Day 6")
    Day6().run()
}