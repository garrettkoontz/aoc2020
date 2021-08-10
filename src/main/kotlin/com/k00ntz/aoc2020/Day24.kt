package com.k00ntz.aoc2020

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseFile
import java.util.*

class Day24 : Day<List<Directions>, Int, Int> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") {
                Directions.parse(it)
            }
//            parse(getFileAsLineSequence("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<Directions>): Int {
        val pointsList = input.map { it.getPoint() }
        val pointCounts = pointsList.groupBy { it }.mapValues { it.value.size }
        return pointCounts.entries.fold(0) { acc: Int, (_, cnt): Map.Entry<HexPoint, Int> ->
            if (cnt % 2 == 1) acc + 1 else acc
        }
    }

    override fun part2(input: List<Directions>): Int {
        val start = input.map { it.getPoint() }.groupBy { it }.mapValues { it.value.size }.filter {
            it.value % 2 == 1
        }.keys
        val evolved = (0 until 100).fold(start) { acc: Set<HexPoint>, _: Int ->
            evolve(acc)
        }
        return evolved.size
    }

    fun evolve(l: Set<HexPoint>): Set<HexPoint> {
        val blacks: List<Pair<Double, Int>> = l.flatMap { hp ->
            val numberOfPopulatedNeighbors = hp.getNeighbors().toSet().intersect(l).size
            if (numberOfPopulatedNeighbors == 0 || numberOfPopulatedNeighbors > 2) {
                listOf()
            } else {
                listOf(hp)
            }
        }
        val whites: List<Pair<Double, Int>> = l.flatMap { it.getNeighbors().toSet().minus(l) }
            .groupBy { it }
            .filter { it.value.size == 2 }
            .map { it.key }
        return blacks.plus(whites).toSet()
    }

}

typealias HexPoint = Pair<Double, Int>

fun HexPoint.getNeighbors(): List<HexPoint> =
    HexDirection.values().map { this + it.movePoint }

enum class HexDirection(val movePoint: HexPoint) {
    E(HexPoint(1.0, 0)),
    SE(HexPoint(.5, -1)),
    NE(HexPoint(.5, 1)),
    W(HexPoint(-1.0, 0)),
    SW(HexPoint(-.5, -1)),
    NW(HexPoint(-.5, 1))
}

operator fun HexPoint.plus(other: HexPoint): HexPoint =
    Pair(this.first + other.first, this.second + other.second)


data class Directions(val lst: List<HexDirection>) {
    companion object {
        fun parse(s: String): Directions {
            val sUpper = s.uppercase(Locale.getDefault())
            var i = 0
            val lst = mutableListOf<HexDirection>()
            while (i < sUpper.length) {
                val dir = sUpper[i].let {
                    if (it == 'S' || it == 'N') {
                        i += 2
                        "$it${sUpper[i - 1]}"
                    } else {
                        i++
                        "$it"
                    }
                }
                lst.add(HexDirection.valueOf(dir))
            }
            return Directions(lst)
        }
    }

    fun getPoint(): HexPoint =
        lst.fold(HexPoint(0.0, 0)) { acc: HexPoint, hexDirection: HexDirection ->
            acc + hexDirection.movePoint
        }
}

fun main() {
    println("Day 24")
    Day24().run()
}