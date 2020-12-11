package com.k00ntz.aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day11Test {

    val day11 = Day11()

    val input = """L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL""".split("\n").map { it.toCharArray().toList() }

    @Test
    fun part1() {
        assertEquals(37, day11.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(26, day11.part2(input))
    }
}