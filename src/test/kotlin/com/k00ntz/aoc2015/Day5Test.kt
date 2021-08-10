package com.k00ntz.aoc2015

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day5Test {
    private val day5 = Day5()

    @Test
    fun part1Test() {
        assertTrue(day5.isNice1("ugknbfddgicrmopn"))
        assertTrue(day5.isNice1("aaa"))
        assertFalse(day5.isNice1("jchzalrnumimnmhp"))
        assertFalse(day5.isNice1("haegwjzuvuyypxyu"))
        assertFalse(day5.isNice1("dvszwmarrgswjxmb"))
    }

    @Test
    fun part2Test() {
        assertTrue(day5.isNice2("qjhvhtzxzqqjkmpb"))
        assertTrue(day5.isNice2("xxyxx"))
        assertFalse(day5.isNice2("uurcxstgmygtbstg"))
        assertFalse(day5.isNice2("ieodomkazucvgmuy"))
    }
}