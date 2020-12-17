package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.*

class Day17 : Day<Set<Point3>, Int, Int> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") { it.toCharArray() }
                .flatMapIndexed { y, chars ->
                    chars.mapIndexed { x, c ->
                        if (c == '#') Point3(x, y, 0) else null
                    }.filterNotNull()
                }.toSet()
//            parse(getFileAsLineSequence("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: Set<Point3>): Int {
        val sixIteration = (1..6).fold(input) { acc: Set<Point3>, i: Int ->
            acc.next()
        }
        return sixIteration.size
    }

    override fun part2(input: Set<Point3>): Int {
        val point4s = input.map { Point4.fromPoint3(it) }.toSet()
        val sixIteration = (1..6).fold(point4s) { acc: Set<Point4>, i: Int ->
            acc.next()
        }
        return sixIteration.size
    }

}

fun <T : Neighborly<T>> Set<T>.next(): Set<T> {
    val pointsAndNeighbors = this.associateBy({it}, {it.neighbors()})
    val neighborsAndNeighbors = pointsAndNeighbors.values.flatten().toSet().associateBy({it}, {it.neighbors()})
    val nextPoints = pointsAndNeighbors.entries.map { (point, neighbors) ->
                val validNeighbors = neighbors.intersect(this)
                    if ((2..3).contains(validNeighbors.size))
                        point
                    else null
            }.filterNotNull().toSet()
    val nextPointsNeighbors = neighborsAndNeighbors.map { (point, neighbors) ->
        val validNeighbors = neighbors.intersect(this)
        if (3 == validNeighbors.size)
            point
        else null
    }.filterNotNull()

    return nextPoints.plus(nextPointsNeighbors)
}

fun main() {
    println("Day 17")
    Day17().run()
}