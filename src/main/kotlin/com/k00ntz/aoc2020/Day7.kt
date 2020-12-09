package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.Day
import com.k00ntz.aoc2020.utils.measureAndPrintTime
import com.k00ntz.aoc2020.utils.parseFile

class Day7 : Day<Map<String, Map<String, Int>>, Int, Int> {

    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") {
                BagRule.parse(it)
            }.associate { Pair(it.bagColor, it.containerMap) }
//            parse(getFileAsLineSequence("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    private val myBag = "shiny gold"

    override fun part1(input: Map<String, Map<String, Int>>): Int {
        val containersList = mutableListOf<String>()
        var searchList = setOf(myBag)
        while (searchList.isNotEmpty()) {
            val newSearchList = searchList
                .flatMap { s ->
                    input.filter {
                        it.value.containsKey(s) && !containersList.contains(it.key)
                    }.keys
                }.toSet()
            containersList.addAll(newSearchList)
            searchList = newSearchList
        }
        return containersList.size
    }

    override fun part2(input: Map<String, Map<String, Int>>): Int = countBags(myBag, input) - 1

    private fun countBags(bag: String, mp: Map<String, Map<String, Int>>): Int {
        val startRules = mp[bag]
        return if (startRules.isNullOrEmpty()) {
            1
        } else {
            startRules.entries.fold(1) { acc: Int, entry: Map.Entry<String, Int> ->
                val subBagsCount = countBags(entry.key, mp)
                acc + entry.value * subBagsCount
            }
        }
    }

}

data class BagRule(
    val bagColor: String,
    val containerMap: Map<String, Int>
) {
    companion object {
        fun parse(str: String): BagRule {
            val (key, rest) = keyPairRegex.matchEntire(str)!!.destructured.let { (k, v) ->
                Pair(k, v)
            }
            val containerMap = containerRegex.findAll(rest).toList().associate {
                it.destructured.let { (n: String, b: String) -> Pair(b, n.toInt()) }
            }
            return BagRule(key, containerMap)
        }

        private val keyPairRegex = "([a-z ]+) bags contain (.*)".toRegex()
        private val containerRegex = "([0-9]) ([a-z ]+) bag[s,. ]{1,3}+".toRegex()
    }
}

fun main() {
    println("Day 7")
    Day7().run()
}