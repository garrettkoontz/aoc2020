package com.k00ntz.aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day12Test {

    val day12 = Day12()

    val input = """F10
N3
F7
R90
F11""".split("\n").map(day12.parseFun)

    @Test
    fun part1() {
        assertEquals(25, day12.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(286, day12.part2(input))
    }
}