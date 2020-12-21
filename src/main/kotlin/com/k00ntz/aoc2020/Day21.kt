package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.Day
import com.k00ntz.aoc2020.utils.measureAndPrintTime
import com.k00ntz.aoc2020.utils.parseFile

class Day21 : Day<List<Food>, Int, String> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") {
                Food.parseFromString(it)
            }
//            parse(getFileAsLineSequence("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<Food>): Int {
        val possibleAllergens = mapAllergens(input)
        val possibleAllergenIngredients = possibleAllergens.values.flatten().toSet()
        val allIngredients = input.flatMap { it.ingredients }
        val nonAllergens = allIngredients.filter { !possibleAllergenIngredients.contains(it) }
        return nonAllergens.size
    }

    private fun mapAllergens(input: List<Food>): Map<String, Set<String>> {
        val possibleAllergens = mutableMapOf<String, Set<String>>()
        input.forEach { f ->
            val aToI = f.allergens.map { Pair(it, f.ingredients.toSet()) }
            aToI.forEach { (a, i) ->
                if (possibleAllergens.containsKey(a)) {
                    possibleAllergens[a] = possibleAllergens[a]!!.intersect(i)
                } else {
                    possibleAllergens[a] = i
                }
            }
        }
        return possibleAllergens
    }

    override fun part2(input: List<Food>): String {
        var allergensMap = mapAllergens(input)
        val knownAllergens = allergensMap.filter { it.value.size == 1 }.mapValues { it.value.first() }.toMutableMap()
        while (knownAllergens.size < allergensMap.size) {
            val knownSet = knownAllergens.values.toSet()
            allergensMap = allergensMap.mapValues { it.value.filterNot { knownSet.contains(it) }.toSet() }
            knownAllergens.putAll(allergensMap.filter { it.value.size == 1 }.mapValues { it.value.first() })
        }
        return knownAllergens.toSortedMap().values.joinToString(",")
    }

}

data class Food(val ingredients: List<String>, val allergens: List<String>) {
    companion object {
        private val parseRegex = "(.*) [(]contains (.*)[)]".toRegex()
        fun parseFromString(s: String): Food =
            parseRegex.matchEntire(s)!!.destructured.let { (i, a) ->
                val ingredients = i.split(" ")
                val alergens = a.split(",").map { it.trim() }
                Food(ingredients, alergens)
            }
    }
}

fun main() {
    println("Day 21")
    Day21().run()
}