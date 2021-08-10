package com.k00ntz.aoc2015

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseLine
import java.util.*

class Day1 : Day<String, Int, Int> {
    override fun run() {
        val inputFile =
            parseLine("2015/${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { it }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: String): Int =
        input.fold(0){ acc: Int, c: Char ->
            when (c) {
                '(' -> acc+1
                ')' -> acc-1
                else -> acc
            }
        }

    override fun part2(input: String): Int {
        var i = 0
        for (j in input.indices){
            if(i == -1) return j
            when (input[j]) {
                '(' -> i++
                ')' -> i--
            }
        }
        return if (i == -1) input.length else -1
    }


}

fun main() {
    println("Day 1")
    Day1().run()
}