package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.*

class Day17 : Day<Set<Point3>, Int, Int> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") { it.toCharArray() }
                .flatMapIndexed { y, chars ->
                    chars.mapIndexed { x, c ->
                        if (c == '#') Point3(x, y, 1) else null
                    }.filterNotNull()
                }.toSet()
//            parse(getFileAsLineSequence("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: Set<Point3>): Int {
    }

}

fun Set<Point3>.next(): Set<Point3> {
    val maxX = this.maxByOrNull { it.x }!!.x + 1
    val minX = this.minByOrNull { it.x }!!.x - 1
    val minY = this.minByOrNull { it.y }!!.y + 1
    val maxY = this.maxByOrNull { it.y }!!.y - 1
    val minZ = this.minByOrNull { it.z }!!.z + 1
    val maxZ = this.maxByOrNull { it.z }!!.z - 1
    return (minX..maxX).flatMap { x ->
        (minY..maxY).flatMap { y ->
            (minZ..maxZ).pmap { z ->
                val pt = Point3(x, y, z)
                val validNeighbors = pt.neighbors().intersect(this)
                if (this.contains(pt)) {
                    if ((2..3).contains(validNeighbors.size))
                        pt
                    else null
                } else {
                    if (validNeighbors.size == 3) pt else null
                }
            }.filterNotNull()
        }
    }.toSet()
}

fun main() {
    println("Day 17")
    Day17().run()
}