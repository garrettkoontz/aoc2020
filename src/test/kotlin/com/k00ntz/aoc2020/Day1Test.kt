package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.Day1
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day1Test {

    val day1 = Day1()

    val input = listOf(
        1721,
        979,
        366,
        299,
        675,
        1456
    )

    @Test
    fun part1() {
        assertEquals(514579,day1.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(241861950, day1.part2(input))
    }
}