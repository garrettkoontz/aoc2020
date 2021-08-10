package com.k00ntz.aoc2015

import com.k00ntz.aoc.utils.*
import java.util.*

class Day8 : Day<List<String>, Int, Int> {
    override fun run() {
        val inputFile =
            parseFile("2015/${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { it }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<String>): Int {
        val l1 = input.sumOf { it.length }
        val l2 = input.sumOf { it.resolvedLength() }
        return l1 - l2
    }

    override fun part2(input: List<String>): Int {
        val l1 = input.sumOf { it.length }
        val l2 = input.sumOf { it.paddedLength() }
        return l2 - l1
    }
}


fun String.resolvedLength(): Int {
    val newStr = this.substring(1, this.length - 1)
        .replace("\\\"", "\"")
        .replace("\\\\", "\\")
        .replace("\\\\x[a-f0-9][a-f0-9]".toRegex(), "'")
    return newStr.length
}

fun String.paddedLength(): Int {
    val newStr = this.replace("\\", "\\\\")
        .replace("\"", "\\\"")
    return newStr.length + 2
}


fun main() {
    println("Day 8")
    Day8().run()
}
