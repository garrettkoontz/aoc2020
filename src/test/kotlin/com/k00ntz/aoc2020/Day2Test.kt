package com.k00ntz.aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day2Test {

    val input = listOf(
        "1-3 a: abcde",
        "1-3 b: cdefg",
        "2-9 c: ccccccccc"
    ).map { PasswordLine.newPasswordLine(it) }

    val day2 = Day2()

    @Test
    fun part1() {
        assertEquals(2, day2.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(1, day2.part2(input))
    }
}