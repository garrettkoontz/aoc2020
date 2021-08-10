package com.k00ntz.aoc2015

import com.k00ntz.aoc.utils.*
import java.security.MessageDigest
import java.util.*

class Day4 : Day<String, Int, Int> {

    private val md: MessageDigest = MessageDigest.getInstance("MD5")

    override fun run() {
        val inputFile =
            parseLine("2015/${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") {it}

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    private fun check1(hash: ByteArray) =
        hash.size > 2
                && hash[0].toInt() == 0
                && hash[1].toInt() == 0
                && (hash[2].toInt() ushr 4 ) == 0

    override fun part1(input: String): Int {
        var i = 0
        var d = md.digest((input + i).toByteArray())
        while(!check1(d)){
            i++
            d = md.digest((input + i).toByteArray())
        }
        return i
    }

    private fun check2(hash: ByteArray) =
        hash.size > 2
                && hash[0].toInt() == 0
                && hash[1].toInt() == 0
                && hash[2].toInt() == 0

    override fun part2(input: String): Int {
        var i = 0
        var d = md.digest((input + i).toByteArray())
        while(!check2(d)){
            i++
            d = md.digest((input + i).toByteArray())
        }
        return i
    }
}

fun main() {
    println("Day 4")
    Day4().run()
}