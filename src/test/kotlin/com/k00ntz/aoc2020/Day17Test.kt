package com.k00ntz.aoc2020

import com.k00ntz.aoc.utils.Point3
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day17Test {

    val inputStr = """.#.
..#
###"""

    val input = inputStr.split("\n").map { it.toCharArray() }
        .flatMapIndexed { y, chars ->
            chars.mapIndexed { x, c ->
                if (c == '#') Point3(x, y, 1) else null
            }.filterNotNull()
        }.toSet()

    val day17 = Day17()

    @Test
    fun part1() {
        assertEquals(112, day17.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(848, day17.part2(input))
    }
}