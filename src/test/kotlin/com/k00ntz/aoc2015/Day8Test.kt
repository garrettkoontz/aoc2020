package com.k00ntz.aoc2015

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day8Test {
    private val day8 = Day8()

    val data = listOf(
        """""""",
        """"abc"""",
        """"aaa\"aaa"""",
        """"\x27""""
    )

    @Test
    fun part1Test() {
        assertEquals(listOf(0, 3, 7, 1), data.map { it.resolvedLength() })
        assertEquals(12, day8.part1(data))
    }

    @Test
    fun part2Test() {
        assertEquals(listOf(6, 9, 16, 11), data.map { it.paddedLength() })
        assertEquals(19, day8.part2(data))
    }
}