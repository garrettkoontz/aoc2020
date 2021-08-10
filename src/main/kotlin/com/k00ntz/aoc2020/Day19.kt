package com.k00ntz.aoc2020

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.getFile
import com.k00ntz.aoc.utils.measureAndPrintTime
import java.util.*

class Day19 : Day<List<String>, Int, Int> {
    override fun run() {
        val inputFile =
//            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
            getFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt")
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    fun parseFull(l: List<String>): Pair<List<Rule>, List<Message>> {
        val splitPoint = l.indexOf("")
        val rules = listOf(resolveRule(parseRule(l.subList(0, splitPoint)), 0))
        val messages = parseMessages(l.subList(splitPoint + 1, l.size))
        return Pair(rules, messages)
    }

    fun parseMessages(l: List<String>): List<Message> =
        l.map { Message(it) }

    class RuleBuilder(
        val r1: List<Int>? = null,
        val r2: List<Int>? = null,
        var rule: List<String>? = null
    )

    fun parseRule(l: List<String>, ruleSize: Int = l.size): Array<RuleBuilder?> {
        val ruleNumberMap = arrayOfNulls<RuleBuilder>(ruleSize)
        val baseRegex = "([0-9]+): \"([ab])\"".toRegex()
        val doubleRuleRegex = "([0-9]+): ([0-9+ ?])+ \\| ([0-9+ ?])+".toRegex()
        val singleRuleRegex = "([0-9]+): ([0-9+ ?])+".toRegex()
        for (i in (l.indices)) {
            val li = l[i]
            when {
                baseRegex.matches(li) -> {
                    baseRegex.matchEntire(li)!!.destructured.let { (i, a) ->
                        ruleNumberMap[i.toInt()] = RuleBuilder(rule = listOf(a))
                    }
                }
                doubleRuleRegex.matches(li) -> {
                    val liSplit = li.replace(":", "").split(" ")
                    val j = liSplit[0].toInt()
                    val f = liSplit.subList(1, liSplit.indexOf("|"))
                    val b = liSplit.subList(liSplit.indexOf("|") + 1, liSplit.size)
                    ruleNumberMap[j] =
                        RuleBuilder(f.map { it.toInt() }, b.map { it.toInt() })
                }
                singleRuleRegex.matches(li) -> {
                    val liSplit = li.replace(":", "").split(" ")
                    ruleNumberMap[liSplit[0].toInt()] = RuleBuilder(liSplit.drop(1).map { it.toInt() })
                }
            }
        }
        return ruleNumberMap
    }

    fun resolveRule(ruleBuilders: Array<RuleBuilder?>, ruleIdx: Int): Rule {
        val rb = ruleBuilders[ruleIdx] ?: throw RuntimeException("no rule builder for rule $ruleIdx")
        return if (rb.rule != null) Rule(rb.rule!!)
        else {
            val r1 = rb.r1!!
            val r1Resolved = r1.fold(Rule(listOf(""))) { acc, i ->
                Rule(acc + resolveRule(ruleBuilders, i))
            }

            val r2 = rb.r2
            val r2Resolved: Rule? = r2?.fold(Rule(listOf(""))) { acc, i ->
                Rule(acc + resolveRule(ruleBuilders, i))
            }
            Rule(r1Resolved.matchRules.plus(r2Resolved?.matchRules ?: emptyList())).also {
                ruleBuilders[ruleIdx] = RuleBuilder(rule = it.matchRules)
            }
        }
    }

    override fun part1(input: List<String>): Int {
        val inp = parseFull(input)
        return inp.second.map { it.s }.toSet().intersect(inp.first.first().matchRules.toSet()).size
    }

    override fun part2(input: List<String>): Int {
        val matchSet = setOf(
            "bbabbbbaabaabba",
            "babbbbaabbbbbabbbbbbaabaaabaaa",
            "aaabbbbbbaaaabaababaabababbabaaabbababababaaa",
            "bbbbbbbaaaabbbbaaabbabaaa",
            "bbbababbbbaaaaaaaabbababaaababaabab",
            "ababaaaaaabaaab",
            "ababaaaaabbbaba",
            "baabbaaaabbaaaababbaababb",
            "abbbbabbbbaaaababbbbbbaaaababb",
            "aaaaabbaabaaaaababaa",
            "aaaabbaabbaaaaaaabbbabbbaaabbaabaaa",
            "aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba"
        )
        val splitPoint = input.indexOf("")
        val builders = parseRule(input.subList(0, splitPoint), maxOf(splitPoint, 43))
        val rule31 = resolveRule(builders, 31).matchRules.toSet()
        val rule42 = resolveRule(builders, 42).matchRules.toSet()
        val messages = parseMessages(input.subList(splitPoint + 1, input.size))
        val otherRules = """
            0: 8 11
            8: 42 | 42 8
            11: 42 31 | 42 11 31
        """.trimIndent()
        val windowSize = rule42.map { it.length }.distinct().first()
        val matchWindows = messages.map { it.s.chunked(windowSize) }
        val matches = matchWindows.filter { ls: List<String> ->
            if (rule42.contains(ls.first())) {
                val m31 = ls.dropLastWhile { rule31.contains(it) }
                val m42 = ls.dropWhile { rule42.contains(it) }
                if (m42.size >= m31.size || m31.size == ls.size || m42.size == ls.size) false
                else ls.size == m31.size + m42.size
            } else false
        }
        return matches.size
    }


}

data class Rule(val matchRules: List<String>) {
    operator fun plus(otherRule: Rule): List<String> =
        matchRules.flatMap { m ->
            otherRule.matchRules.map { o ->
                m + o
            }
        }
}

data class Message(val s: String)

fun main() {
    println("Day 19")
    Day19().run()
}