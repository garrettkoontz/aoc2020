package com.k00ntz.aoc2020.utils

import kotlin.math.abs

data class Point3(val x: Int, val y: Int, val z: Int) {

    val label: String = "x: $x, y: $y, z: $z"

    companion object {
        private val r = "<x=(-?[0-9]+), y=(-?[0-9]+), z=(-?[0-9]+)>".toRegex()
        fun fromString(s: String): Point3 =
            r.matchEntire(s)?.destructured?.let { (x, y, z) ->
                Point3(x.toInt(), y.toInt(), z.toInt())
            } ?: throw RuntimeException("unable to parse $s to Point3")

    }

    operator fun plus(other: Point3): Point3 =
        Point3(this.x + other.x, this.y + other.y, this.z + other.z)

    fun energy(): Int =
        abs(x) + abs(y) + abs(z)

    fun neighbors(distance: Int = 1): List<Point3> =
        ((distance * -1)..distance).flatMap { x ->
            ((distance * -1)..distance).flatMap { y ->
                ((distance * -1)..distance).map { z ->
                    Point3(x, y, z) + this
                }
            }
        }.filter { it == this }

}
