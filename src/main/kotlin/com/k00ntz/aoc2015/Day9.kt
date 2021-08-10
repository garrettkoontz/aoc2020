package com.k00ntz.aoc2015

import com.k00ntz.aoc.utils.*
import java.util.*

class Day9 : Day<Pair<List<List<String>>, Map<String,Map<String,Int>>>, Int, Int> {

    data class Distance(val value: Int, val places: Pair<String, String>) {
        companion object {
            val regex = "([A-z]+) to ([A-z]+) = ([0-9]+)".toRegex()
            fun fromString(s: String): Distance =
                regex.matchEntire(s)!!.destructured.let { (a, b, d) ->
                    Distance(d.toInt(), Pair(a, b))
                }
        }
    }

    override fun run() {
        val inputFile =
            parseFile("2015/${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { Distance.fromString(it) }
        val graph = getGraph(inputFile)
        val paths = getPaths(graph)

        val input = Pair(paths, graph)
        measureAndPrintTime { print(part1(input)) }
        measureAndPrintTime { print(part2(input)) }
    }

    private fun getGraph(input: List<Distance>): Map<String,Map<String,Int>>{
        val graph: MutableMap<String, MutableMap<String, Int>> = mutableMapOf()
        input.forEach {
            val p1 = it.places.first
            val p2 = it.places.second
            val m1 = graph.getOrDefault(p1, mutableMapOf())
            m1[p2] = it.value
            graph[p1] = m1
            val m2 = graph.getOrDefault(p2, mutableMapOf())
            m2[p1] = it.value
            graph[p2] = m2
        }
        return graph
    }

    private fun getPaths(graph: Map<String,Map<String,Int>>): List<List<String>>{
        return graph.keys.flatMap{
            findAllPaths(listOf(it), graph)
        }
    }

    override fun part1(input: Pair<List<List<String>>, Map<String,Map<String,Int>>>): Int {
        val (paths, graph) = input
        val minpath = paths.minByOrNull{
            pathValue(it, graph)
        }!!
        return pathValue(minpath, graph)
    }

    override fun part2(input: Pair<List<List<String>>, Map<String,Map<String,Int>>>): Int {
        val (paths, graph) = input
        val minpath = paths.maxByOrNull{
            pathValue(it, graph)
        }!!
        return pathValue(minpath, graph)
    }

    private fun findAllPaths(path: List<String>, graph: Map<String, Map<String, Int>>): List<List<String>> {
        val peers = graph[path.last()]
        val nexts = peers!!.keys.minus(path)
        return if (nexts.size == 1) {
            listOf(path.plus(nexts.first()))
        } else {
            nexts.flatMap {
                findAllPaths(path.plus(it), graph)
            }
        }
    }

    private fun pathValue(path: List<String>, graph: Map<String, Map<String, Int>>): Int =
        path.zipWithNext().sumOf { graph[it.first]!![it.second]!! }

}


fun main() {
    println("Day 9")
    Day9().run()
}
