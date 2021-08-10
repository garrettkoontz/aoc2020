package com.k00ntz.aoc2015

import com.k00ntz.aoc.utils.*
import java.util.*

enum class LightInstructionType {
    TOGGLE,
    OFF,
    ON
}

data class LightInstruction(val type: LightInstructionType, val startX: Int, val startY: Int, val endX: Int, val endY: Int) {
    companion object {
        val regex = "(turn on|turn off|toggle) ([0-9]+),([0-9]+) through ([0-9]+),([0-9]+)".toRegex()
        fun fromString(s: String): LightInstruction =
            regex.matchEntire(s)!!.destructured.let { (t: String, sx: String, sy: String, ex: String, ey: String) ->
                LightInstruction(
                    when (t) {
                        "toggle" -> LightInstructionType.TOGGLE
                        "turn off" -> LightInstructionType.OFF
                        "turn on" -> LightInstructionType.ON
                        else -> throw RuntimeException("Don't know $t")
                    },
                    sx.toInt(), sy.toInt(), ex.toInt(), ey.toInt()
                )
            }

    }

    fun operateOn1(grid: MutableList<MutableList<Boolean>>) {
        val points = (startX..endX).flatMap{ x -> (startY..endY).map { y ->  Pair(x, y)} }
        when(type){
            LightInstructionType.ON -> {
                points.forEach { (x,y) ->
                    grid[y][x] = true
                }
            }
            LightInstructionType.OFF -> {
                points.forEach { (x,y) ->
                    grid[y][x] = false
                }
            }
            LightInstructionType.TOGGLE -> {
                points.forEach { (x,y) ->
                    grid[y][x] = !grid[y][x]
                }
            }
        }
    }

    fun operateOn2(grid: MutableList<MutableList<Int>>) {
        val points = (startX..endX).flatMap{ x -> (startY..endY).map { y ->  Pair(x, y)} }
        when(type){
            LightInstructionType.ON -> {
                points.forEach { (x,y) ->
                    grid[y][x] +=1
                }
            }
            LightInstructionType.OFF -> {
                points.forEach { (x,y) ->
                    if(grid[y][x] > 0 ) grid[y][x] -=1
                }
            }
            LightInstructionType.TOGGLE -> {
                points.forEach { (x,y) ->
                    grid[y][x] +=2
                }
            }
        }
    }
}

class Day6 : Day<List<LightInstruction>, Int, Int> {
    override fun run() {
        val inputFile =
            parseFile("2015/${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") {
                LightInstruction.fromString(
                    it
                )
            }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<LightInstruction>): Int {
        val grid: MutableList<MutableList<Boolean>> = MutableList(1000) {
            MutableList(1000) { false }
        }
        input.forEach { it.operateOn1(grid) }
        return grid.sumOf { list -> list.filter { it }.size }
    }

    override fun part2(input: List<LightInstruction>): Int {
        val grid: MutableList<MutableList<Int>> = MutableList(1000) {
            MutableList(1000) { 0 }
        }
        input.forEach { it.operateOn2(grid) }
        return grid.sumOf { list -> list.sum() }
    }
}


fun main() {
    println("Day 6")
    Day6().run()
}
