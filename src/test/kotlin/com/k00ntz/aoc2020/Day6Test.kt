package com.k00ntz.aoc2020

import com.k00ntz.aoc.utils.groupSeparatedByEmpty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day6Test {

    val day6 = Day6()

    val inputStr = """
        abc

        a
        b
        c

        ab
        ac

        a
        a
        a
        a

        b
    """.trimIndent()

    val input = groupSeparatedByEmpty(inputStr.split("\n"))

    @Test
    fun part1() {
        assertEquals(11, day6.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(6, day6.part2(input))
    }
}