package com.k00ntz.aoc2020

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.getFile
import com.k00ntz.aoc.utils.measureAndPrintTime
import java.util.*

@ExperimentalUnsignedTypes
class Day14 : Day<List<BitMask>, Long, Long> {
    override fun run() {
        val inputFile =
//            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
            parse(getFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<BitMask>): Long {
        val memory = mutableMapOf<Int, Long>()
        for (bitMask in input) {
            val bm = bitMask.mask
            for (w in bitMask.writes) {
                memory[w.index] = bm.mask(w.value)
            }
        }
        return memory.values.fold(0L) { a, b -> a + b }
    }

    override fun part2(input: List<BitMask>): Long {
        val memory = mutableMapOf<Long, Long>()
        for (bitMask in input) {
            val bm = bitMask.mask
            for (w in bitMask.writes) {
                bm.mask(w.index).forEach {
                    memory[it] = w.value
                }
            }
        }
        return memory.values.fold(0L) { a, b -> a + b }
    }

    internal fun parse(file: List<String>): List<BitMask> {
        val memoryWriteRegex = "mem\\[([0-9]+)] = ([0-9]+)".toRegex()
        val maskRegex = "mask = ([0-1X]+)".toRegex()
        val groupStrings = file.fold(mutableListOf()) { acc: MutableList<MutableList<String>>, s: String ->
            if (s.matches(maskRegex)) acc.add(mutableListOf(s))
            else acc.last().add(s)
            acc
        }
        return groupStrings.map {
            BitMask(maskRegex.matchEntire(it.first())!!.destructured.component1(),
                it.drop(1).map {
                    memoryWriteRegex.matchEntire(it)!!.destructured.let { (idx, value) ->
                        MemoryWrite(idx.toInt(), value.toLong())
                    }
                }
            )
        }
    }


}

@ExperimentalUnsignedTypes
internal fun String.mask(value: Int): List<Long> =
    this.toCharArray().zip(
        value.toULong().toString(2).padStart(36, '0').toCharArray()
    )
        .fold(listOf("")) { acc, (m, i) ->
            when (m) {
                'X' -> acc.flatMap { listOf(it + "0", it + "1") }
                '0' -> acc.map { it + i }
                '1' -> acc.map { it + "1" }
                else -> throw RuntimeException("bad mask value $m")
            }
        }.map { it.toLong(2) }
//    this.toCharArray().fold(listOf("")){
//            acc: List<String>, c: Char ->
//        if(c == 'X') acc.flatMap { listOf(it + "0", it + "1") }
//        else acc.map { it + c }
//    }.map{
//        val valueToLong = value.toULong()
//        val out = valueToLong.or(it.toULong(2)).toLong()
//        out
//    }


@ExperimentalUnsignedTypes
fun String.mask(value: Long): Long {
    val andMask = this.toCharArray().map { if (it == '0') 0 else 1 }.joinToString(separator = "").toULong(2)
    val orMask = this.toCharArray().map { if (it == '1') 1 else 0 }.joinToString(separator = "").toULong(2)
    val binaryNumberString = value.toULong()
    return binaryNumberString.and(andMask).or(orMask).toLong()
}

data class BitMask(val mask: String, val writes: List<MemoryWrite>)

data class MemoryWrite(val index: Int, val value: Long)

@ExperimentalUnsignedTypes
fun main() {
    println("Day 14")
    Day14().run()
}