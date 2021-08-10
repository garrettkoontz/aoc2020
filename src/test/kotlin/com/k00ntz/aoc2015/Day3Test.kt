package com.k00ntz.aoc2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day3Test {
    private val day3 = Day3()

    @Test
    fun part1Test() {
        assertEquals(2, day3.part1(listOf(Move.RIGHT)))
        assertEquals(4, day3.part1(listOf(Move.UP, Move.RIGHT, Move.DOWN, Move.LEFT)))
        assertEquals(
            2,
            day3.part1(
                listOf(
                    Move.UP,
                    Move.DOWN,
                    Move.UP,
                    Move.DOWN,
                    Move.UP,
                    Move.DOWN,
                    Move.UP,
                    Move.DOWN,
                    Move.UP,
                    Move.DOWN
                )
            )
        )
    }

    @Test
    fun part2Test() {
        assertEquals(3, day3.part2(listOf(Move.UP, Move.DOWN)))
        assertEquals(3, day3.part2(listOf(Move.UP, Move.RIGHT, Move.DOWN, Move.LEFT)))
        assertEquals(
            11,
            day3.part2(
                listOf(
                    Move.UP,
                    Move.DOWN,
                    Move.UP,
                    Move.DOWN,
                    Move.UP,
                    Move.DOWN,
                    Move.UP,
                    Move.DOWN,
                    Move.UP,
                    Move.DOWN
                )
            )
        )
    }
}