package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.Day
import com.k00ntz.aoc2020.utils.measureAndPrintTime
import com.k00ntz.aoc2020.utils.parseFile

class Day8 : Day<List<Ops>, Int, Int> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") { s ->
                s.split(" ").let { Ops(it[0], it[1]) }
            }
//            parse(getFileAsLineSequence("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<Ops>): Int {
        val pcSet = mutableSetOf<Int>()
        val opRunner = OpRunner(input)
        var nextPc = 0
        while (!pcSet.contains(nextPc)) {
            pcSet.add(nextPc)
            nextPc = opRunner.next()
        }
        return opRunner.globalAcc
    }

    override fun part2(input: List<Ops>): Int {
        val replaceIdxs = input.indices.filter { setOf(OpCode.NOP, OpCode.JMP).contains(input[it].opCode) }
        replaceIdxs.forEach {
            val newInput = input.toMutableList()
            newInput[it] = newInput[it].swapOp()
            val pcSet = mutableSetOf<Int>()
            val opRunner = OpRunner(newInput)
            var nextPc = 0
            while (!pcSet.contains(nextPc)) {
                pcSet.add(nextPc)
                if (!opRunner.hasNext())
                    return opRunner.globalAcc
                nextPc = opRunner.next()
            }
        }
        return -1
    }

}

class OpRunner(private val ops: List<Ops>, var globalAcc: Int = 0, private var pc: Int = 0) : Iterator<Int> {

    private fun OpCode.execute(i: Int): Int =
        when (this) {
            OpCode.NOP -> ++pc
            OpCode.ACC -> {
                globalAcc += i
                ++pc
            }
            OpCode.JMP -> {
                pc += i
                pc
            }
        }

    override fun hasNext(): Boolean =
        (ops.indices).contains(pc)

    override fun next(): Int = ops[pc].let { op ->
        op.opCode.execute(op.value)
    }

}

data class Ops(val opCode: OpCode, val value: Int) {
    fun swapOp(): Ops =
        when (opCode) {
            OpCode.NOP -> Ops(OpCode.JMP, value)
            OpCode.JMP -> Ops(OpCode.NOP, value)
            else -> this
        }

    constructor(s1: String, s2: String) : this(OpCode.valueOf(s1.toUpperCase()), Integer.parseInt(s2))
}


enum class OpCode {
    ACC,
    JMP,
    NOP;
}

fun main() {
    println("Day 8")
    Day8().run()
}