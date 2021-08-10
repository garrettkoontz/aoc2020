package com.k00ntz.aoc2020

import com.k00ntz.aoc.utils.*
import java.util.*

class Day9 : Day<List<Long>, Long, Long> {


    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") {
                it.toLong()
            }
//            parse(getFileAsLineSequence("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<Long>): Long =
        findInvalidNumber(input)

    fun findInvalidNumber(lst: List<Long>, preambleLength: Int = 25): Long =
        lst.windowed(preambleLength + 1).mapNotNull {
            val target = it.last()
            val values = twoSum(it.dropLast(1).sorted(), target)
            if(values == Pair(0L,0L)) target else null
        }.first()

    override fun part2(input: List<Long>): Long =
        findContiguousSet(input).let { list -> (list.minByOrNull { it } ?: 0L) + (list.maxByOrNull { it } ?: 0L) }

    fun findContiguousSet(input: List<Long>, preambleLength: Int = 25): List<Long> {
        val target = findInvalidNumber(input, preambleLength)
        var (i,j) = Pair(0,0)
        var listSum = input.subList(i, j).sum()
        while(listSum != target){
            if(listSum > target)
                i++
            else j++
            listSum = input.subList(i, j).sum()
        }
        return input.subList(i, j)
    }

}

fun main() {
    println("Day 9")
    Day9().run()
}