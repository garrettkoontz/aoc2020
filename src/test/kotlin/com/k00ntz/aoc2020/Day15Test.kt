package com.k00ntz.aoc2020

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day15Test {

    val day15 = Day15()

    @Test
    fun part1() {
        assertEquals(436, day15.part1(listOf(0,3,6)))
        assertEquals(1, day15.part1(listOf(1,3,2)))
        assertEquals(10, day15.part1(listOf(2,1,3)))
        assertEquals(27, day15.part1(listOf(1,2,3)))
        assertEquals(78, day15.part1(listOf(2,3,1)))
        assertEquals(438, day15.part1(listOf(3,2,1)))
        assertEquals(1836, day15.part1(listOf(3,1,2)))
    }
}