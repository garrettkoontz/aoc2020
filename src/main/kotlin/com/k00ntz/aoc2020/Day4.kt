package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.Day
import com.k00ntz.aoc2020.utils.getFile
import com.k00ntz.aoc2020.utils.measureAndPrintTime

class Day4 : Day<List<PassportLine>, Int, Int> {
    override fun run() {

        val inputFile = parse(getFile("${this.javaClass.simpleName.toLowerCase()}.txt"))
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    fun parse(input: List<String>) =
        input.fold(mutableListOf("")) { acc: MutableList<String>, s: String? ->
            if (s.isNullOrBlank()) {
                acc.add("")
            } else {
                acc[acc.size - 1] = acc[acc.size - 1] + " $s"
            }
            acc
        }
            .map { s ->
                PassportLine(s.trim().split(" ").associate {
                    val pr = it.split(":")
                    Pair(pr[0], pr[1])
                })
            }

    override fun part1(input: List<PassportLine>): Int {
        return input.filter { it.isValidNPCreds() }.size
    }

    override fun part2(input: List<PassportLine>): Int {
        return input.filter { it.isValidNPCCredsAndData() }.size
    }
}

data class PassportLine(
    val byr: Int?, //(Birth Year)
    val iyr: Int?, //(Issue Year)
    val eyr: Int?,//(Expiration Year)
    val hgt: String?, //(Height)
    val hcl: String?, //(Hair Color)
    val ecl: String?,//(Eye Color)
    val pid: String?,//(Passport ID)
    val cid: Int?//(Country ID)
) {
    constructor(pairs: Map<String, String>) : this(
        pairs["byr"]?.toInt(),
        pairs["iyr"]?.toInt(),
        pairs["eyr"]?.toInt(),
        pairs["hgt"],
        pairs["hcl"],
        pairs["ecl"],
        pairs["pid"],
        pairs["cid"]?.toInt(),
    )

    fun isValidNPCreds() =
        byr != null &&
                iyr != null &&
                eyr != null &&
                hgt != null &&
                hcl != null &&
                ecl != null &&
                pid != null

    fun isValidNPCCredsAndData(): Boolean {
        val hgtRegex = "([0-9]+)(cm|in)".toRegex()
        val hclRegex = "#[0-f]{6}".toRegex()
        val eclSet = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
        /*byr (Birth Year) - four digits; at least 1920 and at most 2002.
        iyr (Issue Year) - four digits; at least 2010 and at most 2020.
        eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
        hgt (Height) - a number followed by either cm or in:
        If cm, the number must be at least 150 and at most 193.
        If in, the number must be at least 59 and at most 76.
        hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
        ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
        pid (Passport ID) - a nine-digit number, including leading zeroes.
        cid (Country ID) - ignored, missing or not.*/
        val byrCond = byr != null && (1920..2002).contains(byr)
        val iyrCond = iyr != null && (2010..2020).contains(iyr)
        val eyrCond = eyr != null && (2020..2030).contains(eyr)
        val hgtCond = hgt != null && hgtRegex.matchEntire(hgt)?.let {
            it.destructured.let { (ht, unit) ->
                when (unit) {
                    "cm" -> (150..193).contains(ht.toInt())
                    "in" -> (59..76).contains(ht.toInt())
                    else -> false
                }
            }
        } == true
        val hclCond = hcl != null && hcl.matches(hclRegex)
        val eclCond = ecl != null && eclSet.contains(ecl)
        val pidCond = pid != null && pid.length == 9
        return byrCond && iyrCond && eyrCond && hgtCond && hclCond && eclCond && pidCond
    }

}

fun main() {
    println("Day 4")
    Day4().run()
}