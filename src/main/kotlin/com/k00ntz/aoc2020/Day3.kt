package com.k00ntz.aoc2020

import com.k00ntz.aoc.utils.*
import java.util.*

class Day3 : Day<List<CharArray>, Long, Long> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { line ->
                line.toCharArray()
                }
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    val treeChar = '#'

    override fun part1(input: List<CharArray>): Long {
        return checkSlope(Point(3,1), input)
    }

    fun checkSlope(slope: Point, input: List<CharArray>): Long{
        val width = input.first().size
        var pt = Point(0,0)
        var treesCount = 0
        while (pt.y() < input.size){
            if(input[pt.y()][pt.x()] == treeChar) treesCount++
            pt = Point((pt.x() + slope.x()) % width, pt.y() + slope.y())
        }
        return treesCount.toLong()
    }

    override fun part2(input: List<CharArray>): Long {
        return listOf(Point(1,1), Point(3,1), Point(5,1), Point(7,1), Point(1,2))
            .map { checkSlope(it, input) }.fold(1L) { acc, i -> acc * i }
    }

}

fun main() {
    println("Day 3")
    Day3().run()
}