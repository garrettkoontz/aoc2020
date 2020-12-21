package com.k00ntz.aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day21Test {

    val inputStr = """mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
trh fvjkl sbzzf mxmxvkd (contains dairy)
sqjhc fvjkl (contains soy)
sqjhc mxmxvkd sbzzf (contains fish)"""

    val input = inputStr.split("\n").map { Food.parseFromString(it) }

    val day21 = Day21()

    @Test
    fun part1() {
        assertEquals(5, day21.part1(input))
    }

    @Test
    fun part2() {
        assertEquals("mxmxvkd,sqjhc,fvjkl", day21.part2(input))
    }
}