package com.k00ntz.aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day24Test {

    val inputStr = """sesenwnenenewseeswwswswwnenewsewsw
neeenesenwnwwswnenewnwwsewnenwseswesw
seswneswswsenwwnwse
nwnwneseeswswnenewneswwnewseswneseene
swweswneswnenwsewnwneneseenw
eesenwseswswnenwswnwnwsewwnwsene
sewnenenenesenwsewnenwwwse
wenwwweseeeweswwwnwwe
wsweesenenewnwwnwsenewsenwwsesesenwne
neeswseenwwswnwswswnw
nenwswwsewswnenenewsenwsenwnesesenew
enewnwewneswsewnwswenweswnenwsenwsw
sweneswneswneneenwnewenewwneswswnese
swwesenesewenwneswnwwneseswwne
enesenwswwswneneswsenwnewswseenwsese
wnwnesenesenenwwnenwsewesewsesesew
nenewswnwewswnenesenwnesewesw
eneswnwswnwsenenwnwnwwseeswneewsenese
neswnwewnwnwseenwseesewsenwsweewe
wseweeenwnesenwwwswnew""".split("\n")

    val input = inputStr.map { Directions.parse(it) }

    val day24 = Day24()

    @Test
    fun part1() {
        assertEquals(10, day24.part1(input))
    }

    @Test
    fun part2() {
        val day1 = day24.evolve(input.map { it.getPoint() }.groupBy { it }.mapValues { it.value.size }.filter {
            it.value % 2 == 1
        }.keys)
        assertEquals(15, day1.size)
        val day2 = day24.evolve(day1)
        assertEquals(12, day2.size)
        assertEquals(2208, day24.part2(input))
    }
}