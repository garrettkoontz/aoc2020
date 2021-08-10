package com.k00ntz.aoc2020

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseLine
import java.util.*

class Day15 : Day<List<Int>, Int, Int> {
    override fun run() {
        val inputFile =
//            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
//            parse(getFile("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
            parseLine("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { it.split(",").map { it.toInt() } }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<Int>): Int {
        val mg = MemoryGame(input, 2020).iterator()
        var i = input.size
        while (++i < 2020) mg.next()
        return mg.next()
    }

    override fun part2(input: List<Int>): Int {
        val mg = MemoryGame(input, 300_000_000).iterator()
        var i = input.size
        while (++i < 30_000_000) mg.next()
        return mg.next()
    }

}

class MemoryGame(private val startList: List<Int>, val total: Int) : Iterable<Int> {
    override fun iterator(): Iterator<Int> =
        object : Iterator<Int> {
            private var last = startList.last()

            private val indexMap: Array<Int?> =
                startList.foldIndexed(arrayOfNulls(total)) { index: Int, acc: Array<Int?>, i: Int ->
                    acc[i] = index
                    acc
                }

            private var i = startList.size - 1

            override fun hasNext(): Boolean = true

            override fun next(): Int {
                val j = indexMap[last]
                indexMap[last] = i
                last = if (j != null) i - j else 0
                i++
                return last
            }
        }
}

fun main() {
    println("Day 15")
    Day15().run()
}