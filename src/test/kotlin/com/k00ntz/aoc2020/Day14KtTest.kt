package com.k00ntz.aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@ExperimentalUnsignedTypes
internal class Day14KtTest {

    val day14 = Day14()

    val inputStr = """mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
mem[8] = 11
mem[7] = 101
mem[8] = 0"""

    val input = day14.parse(inputStr.split("\n"))

    val inputStr2 = """mask = 000000000000000000000000000000X1001X
mem[42] = 100
mask = 00000000000000000000000000000000X0XX
mem[26] = 1"""

    val input2 = day14.parse(inputStr2.split("\n"))

    @Test
    fun part1() {
        assertEquals(165, day14.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(208, day14.part2(input2))
    }

    @Test
    fun mask() {
        assertEquals(73L, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X".mask(11L))
        assertEquals(101L, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X".mask(101L))
        assertEquals(64L, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X".mask(0L))
        assertEquals(setOf(26L, 27L, 58L, 59L), "000000000000000000000000000000X1001X".mask(42).toSet())
        assertEquals(
            setOf(16L, 17L, 18L, 19L, 24L, 25L, 26L, 27L),
            "00000000000000000000000000000000X0XX".mask(26).toSet()
        )
    }
}