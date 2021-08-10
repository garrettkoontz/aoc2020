package com.k00ntz.aoc2020

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseFile
import java.util.*

data class PasswordLine(val firstNum: Int, val secondNum: Int, val letter: Char, val password: String) {
    companion object {
        val parseRegex = "([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)".toRegex()

        fun newPasswordLine(s: String) =
            parseRegex.matchEntire(s)!!.destructured.let { (min, max, l, p) ->
                PasswordLine(min.toInt(), max.toInt(), l.first(), p)
            }
    }

    fun rangeValidate(): Boolean {
        val charMap = password.fold(mutableMapOf<Char,Int>()){
            acc, c ->
            if(acc.containsKey(c)) acc[c] = acc[c]?.plus(1)!!
            else acc[c] = 1
            acc
        }
        return (firstNum..secondNum).contains(charMap[letter])
    }

    fun positionValidate(): Boolean =
        password[firstNum-1] == letter && password[secondNum-1] != letter
                || password[firstNum-1] != letter && password[secondNum-1] == letter

}

class Day2 : Day<List<PasswordLine>, Int, Int> {

    override fun run() {
        val inputFile =
            parseFile(this.javaClass.simpleName.lowercase(Locale.getDefault()) + ".txt")
            { s: String ->
                PasswordLine.newPasswordLine(s)
            }
        
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<PasswordLine>): Int =
        input.filter { it.rangeValidate() }.size

    override fun part2(input: List<PasswordLine>): Int =
        input.filter { it.positionValidate() }.size

}

fun main() {
    println("Day 2")
    Day2().run()
}