package com.k00ntz.aoc2015

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day6Test {
    private val day6 = Day6()

    @Test
    fun part1Test() {

    }

    @Test
    fun part2Test() {
        assertEquals(1, day6.part2(listOf(LightInstruction(LightInstructionType.ON, 0, 0, 0, 0))))
        assertEquals(2000000, day6.part2(listOf(LightInstruction(LightInstructionType.TOGGLE, 0, 0, 999, 999))))
    }
}