package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.Day
import com.k00ntz.aoc2020.utils.measureAndPrintTime
import com.k00ntz.aoc2020.utils.parseFile
import java.util.*

class Day18 : Day<List<String>, Long, Long> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") { it }
//            parse(getFileAsLineSequence("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<String>): Long =
        input.map { evaluate(it.replace(" ", "")).first }.sum()

    override fun part2(input: List<String>): Long =
        input.map { eval2(it.replace("(", "( ").replace(")", " )")) }.sum()

    fun evaluate(str: String): Pair<Long, Int> {
        val stack = Stack<Pair<Operator?, Long?>>()
        var i = 0
        while (i < str.length) {
            val value = str[i]
            if (charToOps.containsKey(value)) {
                when (value) {
                    '(' -> {
                        val (vl, idx) = evaluate(str.substring(i + 1))
                        stack.push(Pair(null, vl))
                        if (stack.size > 1) {
                            val j = stack.pop().second ?: throw RuntimeException("not a number!")
                            val op = stack.pop().first ?: throw RuntimeException("not an operation!")
                            val n = stack.pop().second ?: throw RuntimeException("not a number!")
                            stack.push(Pair(null, op.apply(j, n)))
                        }
                        i += idx
                    }
                    ')' -> return Pair(stack.pop().second ?: throw RuntimeException("not a number!"), i + 1)
                    else -> stack.push(Pair(charToOps[value], null))
                }
            } else {
                val j = ("" + value).toLongOrNull() ?: throw RuntimeException("not an int! $value")
                if (stack.isEmpty())
                    stack.push(Pair(null, j))
                else {
                    val op = stack.pop().first ?: throw RuntimeException("not an operation!")
                    val n = stack.pop().second ?: throw RuntimeException("not a number!")
                    stack.push(Pair(null, op.apply(j, n)))
                }
            }
            i++
        }
        return Pair(stack.first().second!!, i)
    }

    fun eval2(str: String): Long {
        val expr = parseString(str)
        return expr.evaluate()
    }


}

class ExpressionTree(val nodes: List<ExpressionNode>) {
    fun size(): Int =
        nodes.map { it.size() }.sum()

    fun evaluate(): Long {
        val toTopLevelNodes = nodes.map {
            if (it is Plus || it is Times || it is ExpressionNumber) it
            else ExpressionNumber((it as SubExpression).evaluate())
        }

        val rPlus = toTopLevelNodes.foldIndexed(mutableListOf<ExpressionNode>()) { index, acc, node ->
            if (node is Plus) {
                acc[acc.size - 1] =
                    ExpressionNumber((acc[acc.size - 1] as ExpressionNumber).l + (toTopLevelNodes[index + 1] as ExpressionNumber).l)
                acc
            } else {
                if (toTopLevelNodes.getOrNull(index - 1) !is Plus) acc.add(node)
                acc
            }
        }

        val resolveTimes = rPlus.fold(ExpressionNumber(1L)) { acc, node ->
            if (node is Times) {
                acc
            } else {
                ExpressionNumber(acc.l * (node as ExpressionNumber).l)
            }
        }

        return resolveTimes.l
    }
}

sealed class ExpressionNode {
    abstract fun size(): Int
}

class ExpressionNumber(val l: Long) : ExpressionNode() {
    override fun size(): Int = 1

    override fun toString(): String {
        return l.toString()
    }

    fun evaluate(): Long = l
}

object Plus : ExpressionNode() {
    override fun size(): Int = 1

    override fun toString(): String {
        return "+"
    }

    fun evaluate(expr1: ExpressionNode, expr2: ExpressionNode): Long {
        if (expr1 is ExpressionNumber && expr2 is ExpressionNumber) {
            return expr1.l + expr2.l
        } else if (expr1 is SubExpression && expr2 is ExpressionNumber) {
            return expr1.evaluate() + expr2.l
        } else if (expr2 is SubExpression && expr1 is ExpressionNumber) {
            return expr2.evaluate() + expr1.l
        } else {
            return (expr1 as SubExpression).evaluate() + (expr2 as SubExpression).evaluate()
        }
    }
}

object Times : ExpressionNode() {
    override fun size(): Int = 1
    override fun toString(): String {
        return "*"
    }

    fun evaluate(expr1: ExpressionNode, expr2: ExpressionNode): Long {
        if (expr1 is ExpressionNumber && expr2 is ExpressionNumber) {
            return expr1.l * expr2.l
        } else if (expr1 is SubExpression && expr2 is ExpressionNumber) {
            return expr1.evaluate() * expr2.l
        } else if (expr2 is SubExpression && expr1 is ExpressionNumber) {
            return expr2.evaluate() * expr1.l
        } else {
            return (expr1 as SubExpression).evaluate() * (expr2 as SubExpression).evaluate()
        }
    }
}

class SubExpression(val subTree: ExpressionTree) : ExpressionNode() {
    override fun size(): Int = subTree.size() + 2
    override fun toString(): String {
        return subTree.nodes.joinToString(prefix = "(", postfix = ")")
    }

    fun evaluate(): Long =
        subTree.evaluate()


}

fun parseString(str: String): ExpressionTree {
    val nodes = mutableListOf<ExpressionNode>()
    val lst = str.split(" ")
    var i = 0
    while (i < lst.size) {
        when (val l = lst[i]) {
            "(" -> {
                val parse = parseString(lst.subList(i + 1, lst.size).joinToString(" "))
                nodes.add(SubExpression(parse))
                i += parse.size() + 1
            }
            ")" -> return ExpressionTree(nodes)
            "+" -> nodes.add(Plus)
            "*" -> nodes.add(Times)
            else -> nodes.add(ExpressionNumber(l.toLong()))
        }
        i++
    }
    return ExpressionTree(nodes)
}

enum class Operator(val c: Char) {
    TIMES('*'), PLUS('+'), OPEN_PAREN('('), CLOSE_PAREN(')');

    fun apply(i: Long, n: Long) =
        when (this) {
            TIMES -> i * n
            PLUS -> i + n
            else -> throw RuntimeException("Not an applicable operation: $this")
        }
}

val charToOps = Operator.values().associateBy { it.c }

fun main() {
    println("Day 18")
    Day18().run()
}