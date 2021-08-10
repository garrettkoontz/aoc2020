package com.k00ntz.aoc2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day1Test {
    private val day1 = Day1()

    private val inputs = listOf(
        "(())",
        "()()",
        "(((",
        "(()(()(",
        "))(((((",
        "())",
        "))(",
        ")))",
        ")())())"
    )

    @Test
    fun part1Test(){
        assertEquals(listOf(0, 0, 3, 3, 3, -1, -1, -3, -3), inputs.map { day1.part1(it) })
    }

    @Test
    fun part2Test(){
        assertEquals(listOf(1, 5), listOf(")", "()())").map { day1.part2(it) })
    }
}