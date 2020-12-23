package com.k00ntz.aoc2020

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day22Test {

    val inputStr = """Player 1:
9
2
6
3
1

Player 2:
5
8
4
7
10"""

    val day22 = Day22()

    val input = day22.parse(inputStr.split("\n"))

    val infiniteInput = day22.parse(
        """Player 1:
43
19

Player 2:
2
29
14""".split("\n")
    )

    @Test
    fun part1() {
        assertEquals(306, day22.part1(input))
    }

    @Test
    fun part2Completes() {
        runBlocking {
            withTimeout(10000L) {
                day22.part2(infiniteInput)
            }
        }
    }

    @Test
    fun part2() {
        assertEquals(291, day22.part2(input))
    }
}