package com.k00ntz.aoc2020

import com.k00ntz.aoc.utils.*
import java.util.*

class Day1 : Day<List<Int>,Int,Int> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { Integer.parseInt(it) }
//            parseFileIndexed(this.javaClass.simpleName.toLowerCase() + ".txt") {i, s ->  Pair(i,s) }
//            parseLine(this.javaClass.simpleName.toLowerCase() + ".txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<Int>): Int {
        val targetValue = 2020
        val sorted = input.sorted()
        val numberPairs = twoSum(sorted, targetValue)
        return numberPairs.first * numberPairs.second
    }

    override fun part2(input: List<Int>): Int {
        val targetValue = 2020
        var sorted = input.sorted()
        while(sorted.isNotEmpty()){
            val third = sorted.last()
            sorted = sorted.dropLast(1)
            val pair: Pair<Int,Int> = twoSum(sorted, targetValue - third)
            if(pair.sum() != 0)
                return pair.first * pair.second * third
        }
        return -1
    }

}

fun main() {
    println("Day 1")
    Day1().run()
}


