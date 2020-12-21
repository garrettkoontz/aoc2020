package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.Day
import com.k00ntz.aoc2020.utils.getFile
import com.k00ntz.aoc2020.utils.measureAndPrintTime
import kotlin.math.sqrt

class Day20 : Day<List<Image>, Long, Int> {
    override fun run() {
        val inputFile =
//            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
            parse(getFile("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    internal fun parse(file: List<String>): List<Image> {
        val parseTile = "Tile ([0-9]+):".toRegex()
        val idxs = file.flatMapIndexed { index: Int, s: String ->
            if (s.contains("Tile")) listOf(index) else listOf()
        }
        return idxs.mapIndexed { idx, i ->
            val j = idxs.getOrNull(idx + 1) ?: file.size + 1
            val id = parseTile.matchEntire(file[i])!!.destructured.component1().toInt()
            val image = file.subList(i + 1, j - 1).map { it.toCharArray() }
            Image(id, image)
        }
    }

    override fun part1(input: List<Image>): Long {
        val matchingImages = matchImages(input)
        val cornerImages = getCorners(matchingImages)
        return cornerImages.keys.fold(1L) { acc: Long, image: Image -> acc * image.id }
    }

    private fun getCorners(matchingImages: Map<Int, Set<Image>>): Map<Image, Int> {
        val imgToMatchingIntCount =
            matchingImages.flatMap { it.value }.fold(mapOf()) { acc: Map<Image, Int>, image: Image ->
                acc.plus(image to acc.getOrDefault(image, 0) + 1)
            }
        return imgToMatchingIntCount.filter { it.value == 4 }
    }

    private fun matchImages(input: List<Image>): Map<Int, Set<Image>> {
        val intToImageMap = input.flatMap {
            listOf(
                Pair(it, it.topInt),
                Pair(it, it.bottomInt),
                Pair(it, it.leftInt),
                Pair(it, it.rightInt),
                Pair(it, it.topIntReverse),
                Pair(it, it.bottomIntReverse),
                Pair(it, it.leftIntReverse),
                Pair(it, it.rightIntReverse)
            )
        }.groupBy({ it.second }, { it.first })

        return intToImageMap
            .filter { it.value.size != 1 }.mapValues { it.value.toSet() }
    }

    val dragonStr =
        "                  # \n" +
                "#    ##    ##    ###\n" +
                " #  #  #  #  #  #   "

    override fun part2(input: List<Image>): Int {
        val organizedImage = organizeImages(input)
        var dragonCount = countDragons(organizedImage)
        val changes = listOf(
            lazy { organizedImage.rotate90() },
            lazy { organizedImage.rotate90().rotate90() },
            lazy { organizedImage.rotate90().rotate90().rotate90() },
            lazy { organizedImage.flipLeftToRight() },
            lazy { organizedImage.flipLeftToRight().rotate90() },
            lazy { organizedImage.flipLeftToRight().rotate90().rotate90() },
            lazy { organizedImage.flipLeftToRight().rotate90().rotate90().rotate90() },
            lazy { organizedImage.flipTopToBottom() },
            lazy { organizedImage.flipTopToBottom().rotate90() },
            lazy { organizedImage.flipTopToBottom().rotate90().rotate90() },
            lazy { organizedImage.flipTopToBottom().rotate90().rotate90() },
            lazy { organizedImage.flipTopToBottom().flipLeftToRight().rotate90() },
            lazy { organizedImage.flipTopToBottom().flipLeftToRight().rotate90().rotate90() },
            lazy { organizedImage.flipTopToBottom().flipLeftToRight().rotate90().rotate90().rotate90() },
            lazy { organizedImage.flipTopToBottom().flipLeftToRight() },
        ).iterator()
        while (dragonCount == 0) {
            dragonCount = countDragons(changes.next().value)
        }
        val dragonSize = dragonStr.toCharArray().filter { it == '#' }.size
        val roughSeas = organizedImage.img.flatMap { it.toList() }.filter { it == '#' }.size
        return roughSeas - (dragonSize * dragonCount)
    }

    private fun Iterable<Char>.toInt() =
        this.map { if (it == '#') 1 else 0 }.joinToString("").toInt(2)

    private fun CharArray.toInt(): Int =
        this.map { if (it == '#') 1 else 0 }.joinToString("").toInt(2)

    private fun countDragons(organizedImage: Image): Int {
        val seaMonster: List<String> = dragonStr.split("\n")
        val seaMonsterWidth = seaMonster[0].length
        val seaMonsterHeight = seaMonster.size
        val seaMonsterMask = seaMonster.map {
            it.toCharArray().toInt()
        }
        var dragonCount = 0
        val img = organizedImage.img
        for (i in (0 until img.size - seaMonsterHeight)) {
            for (j in (0 until img.first().size - seaMonsterWidth)) {
                val nums = img.subList(i, i + seaMonsterHeight).map { it.slice((j until j + seaMonsterWidth)).toInt() }
                val hasMonster = seaMonsterMask.zip(nums).fold(true) { acc: Boolean, (x, y) ->
                    acc && (x.and(y) == x)
                }
                if (hasMonster) dragonCount++
            }
        }

        return dragonCount
    }

    private fun organizeImages(input: List<Image>): Image {
        val imgToMatchingIntCount: Map<Int, Set<Image>> = matchImages(input)
        val corners = getCorners(imgToMatchingIntCount)
        val neighbors = mutableMapOf<Image, MutableSet<Image>>()
        imgToMatchingIntCount.values.forEach { imageMatch ->
            imageMatch.forEach { image ->
                neighbors.putIfAbsent(image, mutableSetOf())
                neighbors[image]!!.addAll(imageMatch.minusElement(image))
            }
        }
        val size = sqrt(input.size.toDouble()).toInt()
        val img: Array<Array<Image?>> = (0 until size).map { arrayOfNulls<Image>(size) }.toTypedArray()
        val topLeft = corners.entries.first().key
        val tlNeighbors = neighbors[topLeft]
        val rightNeighbor = tlNeighbors!!.first()
        val bottomNeighbor = tlNeighbors.drop(1).first()
        val lrMatchNum = listOf(
            topLeft.topInt,
            topLeft.bottomInt,
            topLeft.leftInt,
            topLeft.rightInt
        ).first { rightNeighbor.intMap.containsKey(it) }

        var topLeftOriented = topLeft.putOnRight(lrMatchNum)!!
        var toLeft = rightNeighbor.putOnLeft(topLeftOriented.rightInt)
        var toDown = bottomNeighbor.putOnTop(topLeftOriented.bottomInt)
        if (toDown == null) {
            topLeftOriented = topLeftOriented.flipTopToBottom()
            toLeft = rightNeighbor.putOnLeft(topLeftOriented.rightInt)
            toDown = bottomNeighbor.putOnTop(topLeftOriented.bottomInt)
        }

        img[0][0] = topLeftOriented
        img[0][1] = toLeft
        img[1][0] = toDown

        fun Image.getNeighborForInt(i: Int): Image =
            neighbors[this]!!.first { it.intMap.containsKey(i) }

        for (i in (2 until size)) {
            img[0][i] = img[0][i - 1].let {
                val n = it!!.getNeighborForInt(it.rightInt)
                n.putOnLeft(it.rightInt)
            }
        }

        for (j in (1 until size)) {
            for (i in (0 until size)) {
                img[j][i] = img[j - 1][i].let {
                    val n = it!!.getNeighborForInt(it.bottomInt)
                    n.putOnTop(it.bottomInt)
                }
            }
        }

        val strs = img.map { it.map { it!!.toImageStringList() } }
        return Image(0, strs.flatMap {
            val ml = it.first().toMutableList()
            for (i in (1 until it.size)) {
                for (j in ml.indices) {
                    ml[j] += it[i][j]
                }
            }
            ml.map { it.toCharArray() }
        })
    }
}


class Image(val id: Int, val img: List<CharArray>) {
    val topInt by lazy { img.first().map { if (it == '#') 1 else 0 }.joinToString(separator = "").toInt(2) }
    val bottomInt by lazy { img.last().map { if (it == '#') 1 else 0 }.joinToString(separator = "").toInt(2) }
    val topIntReverse by lazy {
        img.first().map { if (it == '#') 1 else 0 }.asReversed().joinToString(separator = "").toInt(2)
    }
    val bottomIntReverse by lazy {
        img.last().map { if (it == '#') 1 else 0 }.asReversed().joinToString(separator = "").toInt(2)
    }
    val leftInt by lazy { img.map { if (it.first() == '#') 1 else 0 }.joinToString(separator = "").toInt(2) }
    val rightInt by lazy { img.map { if (it.last() == '#') 1 else 0 }.joinToString(separator = "").toInt(2) }
    val leftIntReverse by lazy {
        img.map { if (it.first() == '#') 1 else 0 }.asReversed().joinToString(separator = "").toInt(2)
    }
    val rightIntReverse by lazy {
        img.map { if (it.last() == '#') 1 else 0 }.asReversed().joinToString(separator = "").toInt(2)
    }


    val intMap by lazy {
        mapOf(
            topInt to "topInt",
            bottomInt to "bottomInt",
            topIntReverse to "topIntReverse",
            bottomIntReverse to "bottomIntReverse",
            leftInt to "leftInt",
            rightInt to "rightInt",
            leftIntReverse to "leftIntReverse",
            rightIntReverse to "rightIntReverse",
        )
    }

    fun rotate90(): Image {
        val newImg = img.map { it.copyOf() }.toMutableList()
        val n = img.size
        for (layer in 0 until n / 2) {
            val last = n - 1 - layer
            for (i in layer until last) {
                val offset = i - layer

                // left -> top
                newImg[layer][i] = img[last - offset][layer]

                // bottom -> left
                newImg[last - offset][layer] = img[last][last - offset]

                // right -> bottom
                newImg[last][last - offset] = img[i][last]

                // top -> right
                newImg[i][last] = img[layer][i] // right <- saved top
            }
        }
        return Image(id, newImg)
    }

//    fun rotate90(): Image {
//        val newImg = img.map { it.copyOf() }.toMutableList()
//        val size = img.size
//        for (i in img.indices) {
//            for (j in img[i].indices) {
//                newImg[j][size - i - 1] = img[i][j]
//            }
//        }
//        return Image(id, newImg)
//    }

    fun flipLeftToRight(): Image {
        val newImg = img.map {
            val newc = it.copyOf()
            newc.reverse()
            newc
        }
        return Image(id, newImg)
    }

    fun flipTopToBottom(): Image {
        return Image(id, img.reversed())
    }

    override fun toString(): String = "$id"

    fun toImageString(): String =
        img
            //.drop(1).dropLast(1)
            .joinToString("\n") {
                it
                    //.drop(1).dropLast(1)
                    .joinToString("")
            }

    fun toImageStringList(): List<String> = img.drop(1).dropLast(1).map {
        it.drop(1).dropLast(1).joinToString("")
    }

    fun putOnTop(i: Int): Image? =
        when (this.intMap[i]) {
            "topInt" -> this
            "topIntReverse" -> this.flipLeftToRight()
            "bottomInt" -> this.flipTopToBottom()
            "bottomIntReverse" -> this.flipTopToBottom().flipLeftToRight()
            "leftInt" -> this.rotate90().flipLeftToRight()
            "leftIntReverse" -> this.rotate90()
            "rightInt" -> this.rotate90().rotate90().rotate90()
            "rightIntReverse" -> this.rotate90().flipTopToBottom()
            else -> null
        }

    fun matchTopOrNull(top: Image): Image? =
        putOnTop(top.bottomInt)

    fun matchTop(top: Image): Image =
        matchTopOrNull(top) ?: throw RuntimeException("cannot match")

    fun matchRight(rightNeighbor: Image): Image =
        matchRightOrNull(rightNeighbor) ?: throw RuntimeException("cannot match")

    fun matchRightOrNull(rightNeighbor: Image): Image? =
        putOnRight(rightNeighbor.leftInt)

    fun putOnRight(i: Int): Image? =
        when (this.intMap[i]) {
            "topInt" -> rotate90()
            "bottomInt" -> flipTopToBottom().rotate90()
            "topIntReverse" -> flipLeftToRight().rotate90()
            "bottomIntReverse" -> flipTopToBottom().flipLeftToRight().rotate90()
            "leftInt" -> rotate90().flipLeftToRight()
            "rightInt" -> this
            "leftIntReverse" -> flipLeftToRight()
            "rightIntReverse" -> flipTopToBottom()
            else -> null
        }

    fun putOnLeft(i: Int): Image? =
        when (this.intMap[i]) {
            "topInt" -> rotate90().flipLeftToRight()
            "bottomInt" -> rotate90()
            "topIntReverse" -> rotate90().flipLeftToRight().flipTopToBottom()
            "bottomIntReverse" -> rotate90().flipTopToBottom()
            "leftInt" -> this
            "rightInt" -> flipLeftToRight()
            "leftIntReverse" -> flipTopToBottom()
            "rightIntReverse" -> flipLeftToRight().flipTopToBottom()
            else -> null
        }

    fun matchLeftOrNull(leftNeighbor: Image): Image? =
        putOnLeft(leftNeighbor.rightInt)

    fun matchLeft(leftNeighbor: Image): Image =
        matchLeftOrNull(leftNeighbor) ?: throw RuntimeException("cannot match")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Image

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

}

fun main() {
    println("Day 20")
    Day20().run()
}