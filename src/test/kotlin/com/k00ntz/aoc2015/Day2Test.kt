package com.k00ntz.aoc2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day2Test {
    private val day2 = Day2()

    @Test
    fun part1Test(){
        assertEquals(58, day2.part1(listOf(Triple(2,3,4))))
        assertEquals(43, day2.part1(listOf(Triple(1,1,10))))
    }

    @Test
    fun part2Test(){
        assertEquals(34, day2.part2(listOf(Triple(2,3,4))))
        assertEquals(14, day2.part2(listOf(Triple(1,1,10))))
    }
}