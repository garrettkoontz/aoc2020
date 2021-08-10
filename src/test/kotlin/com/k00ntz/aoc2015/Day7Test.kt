package com.k00ntz.aoc2015

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day7Test {
    private val day7 = Day7()

    @Test
    fun part1Test() {
        assertEquals(
            mapOf(
                "d" to 72,
                "e" to 507,
                "f" to 492,
                "g" to 114,
                "h" to 65412,
                "i" to 65079,
                "x" to 123,
                "y" to 456,
            ), day7.emulate(
                mapOf(
                    "x" to Day7.Wire("x", 123u),
                    "y" to Day7.Wire("y", 456u),
                    "d" to Day7.And("x", "y"),
                    "e" to Day7.Or("x", "y"),
                    "f" to Day7.Lshift("x", 2),
                    "g" to Day7.Rshift("y", 2),
                    "h" to Day7.Not("x"),
                    "i" to Day7.Not("y"),
                )
            ).mapValues { it.value.signal }
        )
    }

    @Test
    fun part2Test() {

    }
}