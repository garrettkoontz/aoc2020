package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.Day
import com.k00ntz.aoc2020.utils.getFile
import com.k00ntz.aoc2020.utils.measureAndPrintTime

class Day16 : Day<TicketInput, Int, Int> {
    override fun run() {
        val inputFile =
//            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") {}
            parse(getFile("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    fun parse(file: List<String>): TicketInput {
        val ruleRegex = "([A-z]+): ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)".toRegex()
        val myTicketLine = "your ticket:"
        val nearbyTicketLine = "nearby tickets:"
        val rules = mutableListOf<TicketRule>()
        val myTicket = mutableListOf<Int>()
        val nearbyTickets = mutableListOf<List<Int>>()
        var myTicketFlag = false
        var nearbyTicketsFlag = false
        for (s in file) {
            if (s.isEmpty()) continue
            if (s.matches(ruleRegex)) {
                ruleRegex.matchEntire(s)!!.destructured.let { (name: String, r1: String, r2: String, r3: String, r4: String) ->
                    rules.add(TicketRule(name, (r1.toInt()..r2.toInt()), (r3.toInt()..r4.toInt())))
                }
            }

            if (myTicketFlag && nearbyTicketsFlag)
                nearbyTickets.add(s.split(",").map { it.toInt() })
            if (s == nearbyTicketLine) nearbyTicketsFlag = true
            if (myTicketFlag && !nearbyTicketsFlag)
                myTicket.addAll(s.split(",").map { it.toInt() })
            if (s == myTicketLine) myTicketFlag = true

        }
        return TicketInput(rules, myTicket, nearbyTickets)
    }

    override fun part1(input: TicketInput): Int {
        val invalidTickets = input.nearbyTickets.mapNotNull { ticket ->
            ticket.firstOrNull { values -> input.rules.none { it.checkRange(values) } }
        }
        return invalidTickets.sum()
    }

    override fun part2(input: TicketInput): Int {
        val validTickets = input.nearbyTickets.filter { ticket ->
            ticket.all { values -> input.rules.any { it.checkRange(values) } }
        }
        validTickets.map { ticket -> ticket.withIndex().map { Pair(it.index, it.value) } }.groupBy { }
    }

}

data class TicketRule(val name: String, val firstRange: IntRange, val secondRange: IntRange) {
    fun checkRange(i: Int) = firstRange.contains(i) || secondRange.contains(i)
}

data class TicketInput(
    val rules: List<TicketRule>,
    val myTicket: List<Int>,
    val nearbyTickets: List<List<Int>>
)

fun main() {
    println("Day 16")
    Day16().run()
}