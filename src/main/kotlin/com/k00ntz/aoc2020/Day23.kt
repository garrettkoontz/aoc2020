package com.k00ntz.aoc2020

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.MapRing
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseLine
import java.util.*

class Day23 : Day<List<Int>, String, Long> {
    override fun run() {
        val inputFile =
//            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
//            parse(getFileAsLineSequence("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
            parseLine("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { it.toCharArray().map { "$it".toInt() } }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<Int>): String {
        val out = doNumberOfRingMoves(100, input)
        return ringToString(out.toList(1))
    }

    override fun part2(input: List<Int>): Long {
        val i = input.plus((10..1_000_000))
        val out = doNumberOfRingMoves(10_000_000, i)
        val oneNode = out[1]!!
        return oneNode.toLong() * out[oneNode]!!.toLong()
    }

    fun ringToString(i: List<Int>): String =
        i.subList(i.indexOf(1) + 1, i.size)
            .plus(i.subList(0, i.indexOf(1)))
            .joinToString("")

    fun doNumberOfMoves(n: Int, i: List<Int>, currentIdx: Int = 0): Pair<List<Int>, Int> =
        (0 until n).fold(Pair(i, currentIdx)) { acc, _ ->
            doMove(acc.first, acc.second)
        }

    fun doNumberOfRingMoves(n: Int, i: List<Int>, currentValue: Int = i.first()): MapRing<Int> {
        val ring = MapRing(i)
        val max = i.maxOrNull()!!
        (0 until n).fold(currentValue) { acc, _ ->
            doMoveRing(ring, acc, max)
        }
        return ring
    }

    fun doMove(i: List<Int>, currentIdx: Int): Pair<List<Int>, Int> {
        val size = i.size
        val current = i[currentIdx]
        val abc = (1..3).map { (currentIdx + it) % size }
        val removed = abc.map { i[it] }
        val out = i.filterNot { removed.contains(it) }
        val nextVal = (1..9).dropWhile { removed.plus(0).contains(((current - it) + 10) % 10) }.first()
            .let { ((current - it) + 10) % 10 }
        val placementIdx = out.indexOf(nextVal) + 1
        val lst = (if (placementIdx > 0) out.subList(0, placementIdx) else emptyList())
            .plus(removed)
            .plus(
                if (placementIdx < out.size)
                    out.subList(placementIdx, out.size)
                else emptyList()
            )
        return Pair(lst, (lst.indexOf(current) + 1) % lst.size)
    }

    fun doMoveRing(r: MapRing<Int>, currentNode: Int, maxValue: Int): Int {
        val removed = r.removeAfter(currentNode)
        val placementNode = nextValue(currentNode, removed, maxValue)
        r.insertAfter(placementNode, removed)
        return r[currentNode]!!
    }

    private fun nextValue(current: Int, removed: List<Int>, max: Int): Int {
        var c = current - 1
        while (c in removed || c < 1) {
            if (c < 1) c = max else c--
        }
        return c
    }
}


fun main() {
    println("Day 23")
    Day23().run()
}