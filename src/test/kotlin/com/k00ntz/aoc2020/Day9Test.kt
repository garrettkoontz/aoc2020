package com.k00ntz.aoc2020

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day9Test {

    val input = listOf(
        35L,
        20L,
        15L,
        25L,
        47L,
        40L,
        62L,
        55L,
        65L,
        95L,
        102L,
        117L,
        150L,
        182L,
        127L,
        219L,
        299L,
        277L,
        309L,
        576L
    )

    val day9 = Day9()

    @Test
    fun findInvalidNumber() {
        assertEquals(127L,day9.findInvalidNumber(input, 5))
    }

    @Test
    fun findContiguousSet() {
        assertEquals(input.subList(2, 6), day9.findContiguousSet(input, 5))
    }
}