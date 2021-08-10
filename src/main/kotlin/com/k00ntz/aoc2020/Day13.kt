package com.k00ntz.aoc2020

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.getFile
import com.k00ntz.aoc.utils.lcm
import com.k00ntz.aoc.utils.measureAndPrintTime
import java.util.*

class Day13 : Day<BusTime, Long, Long> {
    override fun run() {
        val inputFile =
            getFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt").let {
                BusTime(it.first().toInt(), it.last().split(",").map {
                    it.toLongOrNull()
                })
            }
//            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") {
//                Integer.parseInt(it) }
//            parse(getFileAsLineSequence("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: BusTime): Long {
        val earliestDeparture = input.earliestDeparture
        val bestBus = input.busSchedule.filterNotNull().minByOrNull {
            it - earliestDeparture % it
        } ?: throw RuntimeException("No buses")
        return bestBus * (bestBus - earliestDeparture % bestBus)
    }

    override fun part2(input: BusTime): Long =
        findTimeMap(input.busSchedule)

    fun findTimeMap(busTimes: List<Long?>): Long {
        val busTimeMap = TreeMap(busTimes.mapIndexedNotNull { i: Int, b: Long? ->
            b?.let { Pair(b, i) }
        }.associate { it })
        var i = 0L
        var lcm = 1L
        for ((k, v) in busTimeMap.descendingMap()) {
            while ((i + v) % k != 0L) i += lcm
            lcm = lcm(lcm, k)
        }
        return i
    }

}

data class BusTime(val earliestDeparture: Int, val busSchedule: List<Long?>)

fun main() {
    println("Day 13")
    Day13().run()
}
