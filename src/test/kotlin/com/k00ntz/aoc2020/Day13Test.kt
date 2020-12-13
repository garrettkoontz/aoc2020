package com.k00ntz.aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day13Test {

    val input = BusTime(939, listOf(7, 13, null, null, 59, null, 31, 19))

    val day13 = Day13()

    @Test
    fun part1() {
        assertEquals(295, day13.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(1068781, day13.findTimeMap(input.busSchedule))
        assertEquals(3417, day13.findTimeMap(listOf(17, null, 13, 19)))
        assertEquals(754018, day13.findTimeMap(listOf(67, 7, 59, 61)))
        assertEquals(779210, day13.findTimeMap(listOf(67, null, 7, 59, 61)))
        assertEquals(1261476, day13.findTimeMap(listOf(67, 7, null, 59, 61)))
        assertEquals(1202161486L, day13.findTimeMap(listOf(1789, 37, 47, 1889)))
    }
}