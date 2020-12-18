package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.Day
import com.k00ntz.aoc2020.utils.measureAndPrintTime
import com.k00ntz.aoc2020.utils.parseFile

class Day18 : Day<List<Expression>, Long, Long> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") { Expression(it) }
//            parse(getFileAsLineSequence("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

}

enum class Operator(val c: Char) {
    TIMES('*'), PLUS('+'), OPEN_PAREN('('), CLOSE_PAREN(')');
}

val charToOps = Operator.values().associateBy { it.c }

data class Expression(private val str: String) {
    fun evaluate(): Long {
        val stack = ArrayDeque<Pair<Operator?, Int?>>()
        str.toCharArray().forEach { value ->
            if (value == ' ') //do nothing
            else {
                if (charToOps.containsKey(value)) {
                    if (value == ')')
                    // evaluate
                    else stack.addFirst(Pair(charToOps[value], null))
                } else {
                    if (stack.isEmpty())
                        stack.addFirst(Pair(null, Integer.parseInt("" + value)))
                    else
                    //evaluate
                }
            }
        }
    }
}

fun main() {
    println("Day 18")
    Day18().run()
}