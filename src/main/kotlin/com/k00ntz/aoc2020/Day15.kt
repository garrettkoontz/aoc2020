package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.Day
import com.k00ntz.aoc2020.utils.measureAndPrintTime
import com.k00ntz.aoc2020.utils.parseLine

class Day15 : Day<List<Int>, Int, Int> {
    override fun run() {
        val inputFile =
//            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
//            parse(getFile("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { it.split(",").map { it.toInt() } }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<Int>): Int {
        val mg = MemoryGame(input)
        val list = mg.take(2020)
        return list.last()
    }

    override fun part2(input: List<Int>): Int {
        val mg = MemoryGame(input)
        val list = mg.take(30_000_000)
        return list.last()
    }

}

class MemoryGame(private val startList: List<Int>) : Iterable<Int> {
    override fun iterator(): Iterator<Int> =
        object : Iterator<Int> {
            private val list: MutableList<Int> = mutableListOf()

            private val indexMap: MutableMap<Int, Int> =
                startList.dropLast(1).mapIndexed { index: Int, i: Int -> Pair(i, index) }.associate { it }
                    .toMutableMap()

            private var i = 0

            override fun hasNext(): Boolean = true

            override fun next(): Int =
                (if (startList.size > i) startList[i]
                else {
                    val recent = list.last()
                    if (indexMap.containsKey(recent)) {
                        (i - indexMap[recent]!! - 1).also { indexMap[recent] = i -1 }
                    } else {
                        indexMap[recent] = i - 1
                        0
                    }
                }).also {
                    list.add(it)
                    i++
                }
        }
}

fun main() {
    println("Day 15")
    Day15().run()
}