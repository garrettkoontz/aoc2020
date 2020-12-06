package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.Day
import com.k00ntz.aoc2020.utils.measureAndPrintTime
import com.k00ntz.aoc2020.utils.parseFile

class Day5 : Day<List<BinarySpacePartitionedSeat>, Int, Int> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") {
                BinarySpacePartitionedSeat(it)
            }
//            parse(getFileAsLineSequence("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<BinarySpacePartitionedSeat>): Int =
        input.maxByOrNull { it.id }!!.id

    override fun part2(input: List<BinarySpacePartitionedSeat>): Int {
        val seatArray = arrayOfNulls<BinarySpacePartitionedSeat>(128 * 8)
        input.forEach { seat: BinarySpacePartitionedSeat ->
            seatArray[seat.id] = seat
        }
        val dropNulls = seatArray.dropWhile { it == null }
        val dropSeats = dropNulls.dropWhile { it != null }
        return dropSeats.drop(1).first()!!.id - 1
    }

}

data class BinarySpacePartitionedSeat(
    val string: String,
    val row: Int,
    val column: Int,
    val id: Int = row * 8 + column
) {
    constructor(string: String) : this(
        string,
        binaryPartition(string, 127, 'F', 'B'),
        binaryPartition(string, 7, 'L', 'R')
    )
}

fun binaryPartition(str: String, size: Int, leftChar: Char, rightChar: Char): Int =
    str.fold(Pair(0, size)) { (x, y), c: Char ->
        when (c) {
            leftChar -> Pair(x, y - (y - x) / 2 - 1)
            rightChar -> Pair(x + (y - x) / 2 + 1, y)
            else -> Pair(x, y)
        }
    }.first


fun main() {
    println("Day 5")
    Day5().run()
}