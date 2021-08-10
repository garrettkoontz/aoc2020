package com.k00ntz.aoc2015

import com.k00ntz.aoc.utils.*
import java.util.*

enum class Move(val c: Char, val p: Point){
    UP('^', Point(0, 1)),
    DOWN('v', Point(0, -1)),
    RIGHT('>', Point(1, 0)),
    LEFT('<', Point(-1, 0))
}

val map = Move.values().associateBy { it.c }

class Day3 : Day<List<Move>, Int, Int> {

    override fun run() {
        val inputFile =
            parseLine("2015/${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") {
                it.map { map.getValue(it) }
            }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<Move>): Int {
        var pt = Point(0,0)
        val visited = mutableSetOf(pt)
        for (m in input){
            pt += m.p
            visited.add(pt)
        }
        return visited.size
    }

    override fun part2(input: List<Move>): Int {
        var pt1 = Point(0,0)
        var pt2 = Point(0,0)
        val visited = mutableSetOf(pt1, pt2)
        for (m in input.indices){
            if(m % 2 == 0){
                pt1 += input[m].p
                visited.add(pt1)
            } else {
                pt2 += input[m].p
                visited.add(pt2)
            }
        }
        return visited.size
    }



}

fun main() {
    println("Day 3")
    Day3().run()
}