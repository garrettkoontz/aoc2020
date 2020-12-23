package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.Day
import com.k00ntz.aoc2020.utils.getFile
import com.k00ntz.aoc2020.utils.measureAndPrintTime

class Day22 : Day<Pair<List<Int>, List<Int>>, Long, Long> {
    override fun run() {
        val inputFile =
//            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
            parse(getFile("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    fun parse(s: List<String>): Pair<List<Int>, List<Int>> {
        val breakidx = s.indexOf("")
        val p1Hand = ArrayDeque(s.subList(1, breakidx).map { it.toInt() })
        val p2Hand = ArrayDeque(s.subList(breakidx + 2, s.size).map { it.toInt() })
        return Pair(p1Hand, p2Hand)
    }

    override fun part1(input: Pair<List<Int>, List<Int>>): Long = SpaceCardGame(input).play()

    override fun part2(input: Pair<List<Int>, List<Int>>): Long = SpaceCardGame(input).playRecursively().first

}

data class SpaceCardGame(val p1Hand: ArrayDeque<Int>, val p2Hand: ArrayDeque<Int>) {
    constructor(lists: Pair<List<Int>, List<Int>>) : this(ArrayDeque(lists.first), ArrayDeque(lists.second))

    fun play(): Long {
        while (p1Hand.isNotEmpty() && p2Hand.isNotEmpty()) {
            val p1 = p1Hand.removeFirst()
            val p2 = p2Hand.removeFirst()
            if (p1 > p2) {
                p1Hand.addLast(p1)
                p1Hand.addLast(p2)
            } else {
                p2Hand.addLast(p2)
                p2Hand.addLast(p1)
            }
        }
        return score(p1Hand, p2Hand)
    }

    fun playRecursively(
        p1h: ArrayDeque<Int> = p1Hand,
        p2h: ArrayDeque<Int> = p2Hand,
        gameNumber: Int = 1
    ): Pair<Long, Boolean> {
        var round = 1
        val previousMatches = mutableSetOf(Pair(p1h.toList(), p2h.toList()))
        while (p1h.isNotEmpty() && p2h.isNotEmpty()) {
            val p1 = p1h.removeFirst()
            val p2 = p2h.removeFirst()
            when {
                p1 > p1h.size || p2 > p2h.size -> {
                    if (p1 > p2) {
//                        println("player 1 wins round $round of game $gameNumber")
                        p1h.addLast(p1)
                        p1h.addLast(p2)
                    } else {
//                        println("player 2 wins round $round of game $gameNumber")
                        p2h.addLast(p2)
                        p2h.addLast(p1)
                    }
                }
                else -> {
//                    println("Playing a sub-game to determine the winner...")
                    if (playRecursively(
                            ArrayDeque(p1h.toList().take(p1)),
                            ArrayDeque(p2h.toList().take(p2)),
                            gameNumber + 1
                        ).second
                    ) {
//                        println("player 1 wins round $round of game $gameNumber")
                        p1h.addLast(p1)
                        p1h.addLast(p2)
                    } else {
//                        println("player 2 wins round $round of game $gameNumber")
                        p2h.addLast(p2)
                        p2h.addLast(p1)
                    }
                }
            }
            if (previousMatches.contains(Pair(p1h.toList(), p2h.toList()))) {
                return Pair(score(p1h, ArrayDeque()), true)
            } else {
                previousMatches.add(Pair(p1h.toList(), p2h.toList()))
            }
            round++
        }
        return Pair(score(p1h, p2h), p1h.isNotEmpty())
    }

    fun score(p1Hand: ArrayDeque<Int>, p2Hand: ArrayDeque<Int>): Long {
        val hand = if (p1Hand.isEmpty()) p2Hand else p1Hand
        return hand.toList().reversed().foldIndexed(0L) { index: Int, acc: Long, i: Int ->
            acc + i * (index.toLong() + 1L)
        }
    }
}

fun main() {
    println("Day 22")
    Day22().run()
}