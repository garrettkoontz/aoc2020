package com.k00ntz.aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day23Test {

    val day23 = Day23()

    @Test
    fun doMove() {
        assertEquals(Pair(listOf(3, 2, 8, 9, 1, 5, 4, 6, 7), 1), day23.doMove(listOf(3, 8, 9, 1, 2, 5, 4, 6, 7), 0))
        day23.doNumberOfMoves(10, listOf(3, 8, 9, 1, 2, 5, 4, 6, 7))
    }

    @Test
    fun doMoves() {
        assertEquals("92658374", day23.ringToString(day23.doNumberOfMoves(10, listOf(3, 8, 9, 1, 2, 5, 4, 6, 7)).first))
    }

    @Test
    fun part1() {
        assertEquals("67384529", day23.part1(listOf(3, 8, 9, 1, 2, 5, 4, 6, 7)))
    }

    @Test
    fun part2() {
        assertEquals(149245887792L, day23.part2(listOf(3, 8, 9, 1, 2, 5, 4, 6, 7)))
    }

    @Test
    fun doRingMove() {
        assertEquals(
            "92658374",
            day23.ringToString(day23.doNumberOfRingMoves(10, listOf(3, 8, 9, 1, 2, 5, 4, 6, 7)).toList(1))
        )
    }
}