package com.k00ntz.aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day7Test {

    val day7 = Day7()

    val inputStr = """        light red bags contain 1 bright white bag, 2 muted yellow bags.
        dark orange bags contain 3 bright white bags, 4 muted yellow bags.
        bright white bags contain 1 shiny gold bag.
        muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
        shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
        dark olive bags contain 3 faded blue bags, 4 dotted black bags.
        vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
        faded blue bags contain no other bags.
        dotted black bags contain no other bags.""".trimIndent()

    val input = inputStr.split("\n").map { BagRule.parse(it) }
        .associate { Pair(it.bagColor, it.containerMap) }

    val inputStr2 = """
        shiny gold bags contain 2 dark red bags.
        dark red bags contain 2 dark orange bags.
        dark orange bags contain 2 dark yellow bags.
        dark yellow bags contain 2 dark green bags.
        dark green bags contain 2 dark blue bags.
        dark blue bags contain 2 dark violet bags.
        dark violet bags contain no other bags.
    """.trimIndent()

    val input2 = inputStr2.split("\n").map { BagRule.parse(it) }
        .associate { Pair(it.bagColor, it.containerMap) }

    @Test
    fun part1() {
        assertEquals(4, day7.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(32, day7.part2(input))
        assertEquals(126, day7.part2(input2))
    }
}