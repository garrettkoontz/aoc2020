package com.k00ntz.aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day5Test {

    @Test
    fun part1() {
    }

    @Test
    fun paritionParseTest() {
        assertEquals(
            BinarySpacePartitionedSeat("FBFBBFFRLR", 44, 5, 357),
            BinarySpacePartitionedSeat("FBFBBFFRLR")
        )
        assertEquals(
            BinarySpacePartitionedSeat("BFFFBBFRRR", 70, 7, 567),
            BinarySpacePartitionedSeat("BFFFBBFRRR")
        )
        assertEquals(
            BinarySpacePartitionedSeat("FFFBBBFRRR", 14, 7, 119),
            BinarySpacePartitionedSeat("FFFBBBFRRR")
        )
        assertEquals(
            BinarySpacePartitionedSeat("BBFFBBFRLL", 102, 4, 820),
            BinarySpacePartitionedSeat("BBFFBBFRLL")
        )
    }
}