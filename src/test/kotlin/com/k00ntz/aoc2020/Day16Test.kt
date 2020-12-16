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

    val inputStr2 = """class: 0-1 or 4-19
row: 0-5 or 8-19
seat: 0-13 or 16-19

your ticket:
11,12,13

nearby tickets:
3,9,18
15,1,5
5,14,9"""

    val input2 = day16.parse(inputStr2.split("\n"))

    @Test
    fun part1() {
        assertEquals(71, day16.part1(input))
    }

    @Test
    fun part2(){
        day16.part2(input2)
    }
}