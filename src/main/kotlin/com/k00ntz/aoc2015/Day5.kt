package com.k00ntz.aoc2015

import com.k00ntz.aoc.utils.*
import java.util.*

class Day5 : Day<List<String>, Int, Int> {

    override fun run() {
        val inputFile =
            parseFile("2015/${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { it }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    val vowels = setOf('a', 'e', 'i', 'o', 'u')

    fun isNice1(s: String): Boolean {
        if (!s.contains("ab|cd|pq|xy".toRegex())) {
            if (s.filter { vowels.contains(it) }.length > 2) {
                s.drop(1).fold(s.first()) { acc: Char, c: Char ->
                    if (acc == c)
                        return true
                    else c
                }
            }
        }
        return false
    }

    fun isNice2(s: String): Boolean {
        var hasPairs = false
        var hasRepeat = false
        for (i in (0 until s.length - 2)) {
            for (j in (i + 2 until s.length - 1)) {
                if (s[i] == s[j] && s[i + 1] == s[j + 1]) hasPairs = true
            }
            if(s[i] == s[i + 2]) hasRepeat = true
        }
        return hasPairs && hasRepeat
    }

    override fun part1(input: List<String>): Int =
        input.filter { isNice1(it) }.size

    override fun part2(input: List<String>): Int =
        input.filter { isNice2(it) }.size

}

fun main() {
    println("Day 5")
    Day5().run()
}