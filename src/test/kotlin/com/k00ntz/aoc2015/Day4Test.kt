package com.k00ntz.aoc2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day4Test {
    private val day4 = Day4()

    @Test
    fun part1Test() {
        assertEquals(609043, day4.part1("abcdef"))
        assertEquals(1048970, day4.part1("pqrstuv"))
    }

    @Test
    fun part2Test() {

    }
}