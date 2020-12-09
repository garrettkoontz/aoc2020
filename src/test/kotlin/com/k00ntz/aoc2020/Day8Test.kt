package com.k00ntz.aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day8Test {

    val inputStr = """
        nop +0
        acc +1
        jmp +4
        acc +3
        jmp -3
        acc -99
        acc +1
        jmp -4
        acc +6
    """.trimIndent()

    val input = inputStr.split("\n").map {
        it.split(" ").let {
            Ops(it[0], it[1])
        }
    }

    val day8 = Day8()

    @Test
    fun part1() {
        assertEquals(5, day8.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(8, day8.part2(input))
    }
}