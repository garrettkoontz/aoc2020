package com.k00ntz.aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day16Test {

    val day16 = Day16()

    val inputStr = """class: 1-3 or 5-7
row: 6-11 or 33-44
seat: 13-40 or 45-50

your ticket:
7,1,14

nearby tickets:
7,3,47
40,4,50
55,2,20
38,6,12"""

    val input = day16.parse(inputStr.split("\n"))

    @Test
    fun part1() {
        assertEquals(71, day16.part1(input))
    }
}