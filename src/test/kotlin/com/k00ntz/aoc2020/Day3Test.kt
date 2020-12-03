package com.k00ntz.aoc2020

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day3Test {

    val day3 = Day3()

    val inputStr = "..##.......\n" +
            "#...#...#..\n" +
            ".#....#..#.\n" +
            "..#.#...#.#\n" +
            ".#...##..#.\n" +
            "..#.##.....\n" +
            ".#.#.#....#\n" +
            ".#........#\n" +
            "#.##...#...\n" +
            "#...##....#\n" +
            ".#..#...#.#"

    val input = inputStr.split("\n").map { it.toCharArray() }

    @Test
    fun part1() {
        assertEquals(7, day3.part1(input))
    }

    @Test
    fun part2(){
        assertEquals(336, day3.part2(input))
    }
}