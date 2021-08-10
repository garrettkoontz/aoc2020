package com.k00ntz.aoc2015

import com.k00ntz.aoc.utils.*
import java.util.*

class Day7 : Day<Map<String, Day7.Gate>, UShort, UShort> {

    sealed class Gate {
        abstract fun signal(wires: Map<String, Wire>): UShort
        abstract fun getChildren(): List<String>
        fun getSignalOf(w: String, mp: Map<String, Wire>): UShort =
            w.toIntOrNull()?.toUShort() ?: mp.getValue(w).signal

        override fun toString(): String {
            return getChildren().joinToString(prefix = "[", postfix = "]")
        }
    }

    class Noop(val id: String, val pointer: String) : Gate() {
        override fun signal(wires: Map<String, Wire>): UShort =
            getSignalOf(pointer, wires)

        override fun getChildren(): List<String> = listOf(pointer)

    }

    class Wire(val id: String, val signal: UShort) : Gate() {
        override fun signal(wires: Map<String, Wire>) = signal
        override fun getChildren() = emptyList<String>()
    }

    class And(val w1: String, val w2: String) : Gate() {
        override fun signal(wires: Map<String, Wire>): UShort =
            getSignalOf(w1, wires) and getSignalOf(w2, wires)

        override fun getChildren() = listOf(w1, w2).filter { it.toIntOrNull() == null }
    }

    class Or(val w1: String, val w2: String) : Gate() {
        override fun signal(wires: Map<String, Wire>): UShort =
            getSignalOf(w1, wires) or getSignalOf(w2, wires)

        override fun getChildren() = listOf(w1, w2).filter { it.toIntOrNull() == null }
    }

    class Lshift(val w1: String, val i: Int) : Gate() {
        override fun signal(wires: Map<String, Wire>): UShort =
            (getSignalOf(w1, wires).toInt() shl i).toUShort()

        override fun getChildren() = listOf(w1).filter { it.toIntOrNull() == null }
    }

    class Rshift(val w1: String, val i: Int) : Gate() {
        override fun signal(wires: Map<String, Wire>): UShort =
            (getSignalOf(w1, wires).toInt() ushr i).toUShort()

        override fun getChildren() = listOf(w1).filter { it.toIntOrNull() == null }
    }

    class Not(val w1: String) : Gate() {
        override fun signal(wires: Map<String, Wire>): UShort =
            getSignalOf(w1, wires).inv()

        override fun getChildren() = listOf(w1).filter { it.toIntOrNull() == null }
    }

    val number = "([0-9]+)"
    val letter = "([a-z]+)"
    val letterOrNumber = "([0-9a-z]+)"
    val wireRegex = "$number -> $letter".toRegex()
    val andRegex = "$letterOrNumber AND $letterOrNumber -> $letter".toRegex()
    val orRegex = "$letterOrNumber OR $letterOrNumber -> $letter".toRegex()
    val lsRegex = "$letterOrNumber LSHIFT $number -> $letter".toRegex()
    val rsRegex = "$letterOrNumber RSHIFT $number -> $letter".toRegex()
    val notRegex = "NOT $letterOrNumber -> $letter".toRegex()
    val noopRegex = "$letter -> $letter".toRegex()

    fun parseLine(s: String): Pair<String, Gate> {
        return when {
            wireRegex.matches(s) -> {
                wireRegex.matchEntire(s)!!.destructured.let { (n, l) ->
                    Pair(l, Wire(l, n.toUShort()))
                }
            }
            andRegex.matches(s) -> {
                andRegex.matchEntire(s)!!.destructured.let { (l1, l2, l3) ->
                    Pair(l3, And(l1, l2))
                }
            }
            orRegex.matches(s) -> {
                orRegex.matchEntire(s)!!.destructured.let { (l1, l2, l3) ->
                    Pair(l3, Or(l1, l2))
                }
            }
            lsRegex.matches(s) -> {
                lsRegex.matchEntire(s)!!.destructured.let { (l1, n, l2) ->
                    Pair(l2, Lshift(l1, n.toInt()))
                }
            }
            rsRegex.matches(s) -> {
                rsRegex.matchEntire(s)!!.destructured.let { (l1, n, l2) ->
                    Pair(l2, Rshift(l1, n.toInt()))
                }

            }
            notRegex.matches(s) -> {
                notRegex.matchEntire(s)!!.destructured.let { (l1, l2) ->
                    Pair(l2, Not(l1))
                }
            }
            noopRegex.matches(s) -> {
                noopRegex.matchEntire(s)!!.destructured.let { (p, l) ->
                    Pair(l, Noop(l, p))
                }
            }
            else -> {
                throw RuntimeException("Can't pares line $s")
            }
        }
    }

    override fun run() {
        val inputFile =
            parseFile("2015/${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { parseLine(it) }.associate { it }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: Map<String, Gate>): UShort {
        val wires = emulate(input)
        return wires["a"]?.signal ?: 0.toUShort()
    }

    fun emulate(input: Map<String, Gate>): Map<String, Wire> {
        val wires: MutableMap<String, Wire> = input.values.filterIsInstance<Wire>().associateBy { it.id }.toMutableMap()
        while (wires.size < input.keys.size) {
            val makeThese = input.filter { wires.keys.containsAll(it.value.getChildren()) }.minus(wires.keys)
            makeThese.forEach { (name, gate) ->
                wires[name] = Wire(name, gate.signal(wires))
            }
        }
        return wires
    }

    override fun part2(input: Map<String, Gate>): UShort {
        val wires = emulate(input)
        val a = wires["a"]?.signal ?: 0.toUShort()
        val newWires = input.toMutableMap().plus("b" to Wire("b", a))
        val newMap = emulate(newWires)
        return newMap["a"]?.signal ?: 0.toUShort()
    }
}


fun main() {
    println("Day 7")
    Day7().run()
}
