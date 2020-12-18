package com.k00ntz.aoc2020

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ExpressionTest {

    val day18 = Day18()

    @Test
    fun evaluate() {
        assertEquals(71, day18.evaluate("1 + 2 * 3 + 4 * 5 + 6".replace(" ", "")).first)
        assertEquals(51, day18.evaluate("1 + (2 * 3) + (4 * (5 + 6))".replace(" ", "")).first)
        assertEquals(26, day18.evaluate("2 * 3 + (4 * 5)".replace(" ", "")).first)
        assertEquals(437, day18.evaluate("5 + (8 * 3 + 9 + 3 * 4 * 3)".replace(" ", "")).first)
        assertEquals(12240, day18.evaluate("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))".replace(" ", "")).first)
        assertEquals(13632, day18.evaluate("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2".replace(" ", "")).first)
    }

    @Test
    fun eval2(){
        assertEquals(231, day18.eval2("1 + 2 * 3 + 4 * 5 + 6".replace("(", "( ").replace(")", " )")))
        assertEquals(51, day18.eval2("1 + (2 * 3) + (4 * (5 + 6))".replace("(", "( ").replace(")", " )")))
        assertEquals(46, day18.eval2("2 * 3 + (4 * 5)".replace("(", "( ").replace(")", " )")))
        assertEquals(1445, day18.eval2("5 + (8 * 3 + 9 + 3 * 4 * 3)".replace("(", "( ").replace(")", " )")))
        assertEquals(669060, day18.eval2("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))".replace("(", "( ").replace(")", " )")))
        assertEquals(23340, day18.eval2("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2".replace("(", "( ").replace(")", " )")))
    }
}