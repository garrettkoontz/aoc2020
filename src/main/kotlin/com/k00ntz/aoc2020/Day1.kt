package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.*

class Day1 : Day<List<Int>,Int,Int> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
//            parseFileIndexed(this.javaClass.simpleName.toLowerCase() + ".txt") {i, s ->  Pair(i,s) }
//            parseLine(this.javaClass.simpleName.toLowerCase() + ".txt") { Integer.parseInt(it) }
        measureAndPrintTime { println(part1(inputFile)) }
        measureAndPrintTime { println(part2(inputFile)) }
    }

    override fun part1(input: List<Int>): Int {
        val targetValue = 2020
        val sorted = input.sorted()
        val numberPairs = walkForTarget(sorted, targetValue)
        return numberPairs.first * numberPairs.second
    }

    private fun walkForTarget(sorted: List<Int>, targetValue: Int): Pair<Int,Int> {
        var i = 0
        var j = sorted.size - 1
        while(i != j){
            val sum = sorted[i] + sorted[j]
            if(sum == targetValue) return Pair(sorted[i], sorted[j])
            if(sum < targetValue) ++i
            if(sum > targetValue) --j
        }
        return Pair(0,0)
    }

    override fun part2(input: List<Int>): Int {
        val targetValue = 2020
        var sorted = input.sorted()
        while(sorted.isNotEmpty()){
            val third = sorted.last()
            sorted = sorted.dropLast(1)
            val pair: Pair<Int,Int> = walkForTarget(sorted, targetValue - third)
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