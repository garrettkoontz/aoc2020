package com.k00ntz.aoc2015

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseFile
import java.util.*
import kotlin.math.max

class Day2 : Day<List<Triple<Long, Long, Long>>, Long, Long> {
    override fun run() {
        val regex = "([0-9]+)x([0-9]+)x([0-9]+)".toRegex()
        val inputFile =
            parseFile("2015/${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { s: String ->
                regex.matchEntire(s)!!.destructured.let { (l, w, h) ->
                    Triple(l.toLong(), w.toLong(), h.toLong())
                }
            }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    private fun surfaceArea(l: Long, w: Long, h: Long) = listOf(l * w, w * h, h * l).let { list ->
        (list.sum() * 2) + (list.minByOrNull { it } ?: 0)
    }

    private fun ribbon(l: Long, w: Long, h: Long): Long =
        2 * (l + w + h - max(l, max(w, h))) + l * w * h

    override fun part1(input: List<Triple<Long, Long, Long>>): Long =
        input.fold(0L) { acc: Long, (l, w, h): Triple<Long, Long, Long> ->
            acc + surfaceArea(l, w, h)
        }

    override fun part2(input: List<Triple<Long, Long, Long>>): Long =
        input.fold(0L) { acc: Long, (l, w, h): Triple<Long, Long, Long> ->
            acc + ribbon(l, w, h)
        }

}

fun main() {
    println("Day 2")
    Day2().run()
}