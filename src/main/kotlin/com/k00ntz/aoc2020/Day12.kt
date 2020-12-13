package com.k00ntz.aoc2020

import com.k00ntz.aoc2020.utils.*

class Day12 : Day<List<DirectionInput>, Int, Int> {
    private val parseRegex = "([E-W])([0-9]+)".toRegex()

    val parseFun = { s: String ->
        parseRegex.matchEntire(s)!!.destructured.let { (dir, amt) ->
            DirectionInput(Direction.valueOf(dir), Integer.parseInt(amt))
        }
    }

    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.toLowerCase()}.txt", parseFun)
//            parse(getFileAsLineSequence("${this.javaClass.simpleName.toLowerCase()}.txt"))
//            parseFileIndexed("${this.javaClass.simpleName.toLowerCase()}.txt") {i, s ->  Pair(i,s) }
//            parseLine("${this.javaClass.simpleName.toLowerCase()}.txt") { Integer.parseInt(it) }
        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<DirectionInput>): Int {
        val startPoint = Point(0, 0)
        val startDir = Direction.E
        val endPoint = input.fold(
            Pair(
                startPoint,
                startDir
            )
        ) { (position, direction): Pair<Point, Direction>, movement: DirectionInput ->
            when (movement.direction) {
                Direction.N,
                Direction.S,
                Direction.E,
                Direction.W -> Pair(position + movement.direction.movePoint!! * movement.value, direction)
                Direction.L -> Pair(
                    position,
                    Direction.getDirectionFromAngle((direction.angle!! - movement.value + 360) % 360)
                        ?: throw RuntimeException("Bad angle : $movement")
                )
                Direction.R -> Pair(
                    position,
                    Direction.getDirectionFromAngle((direction.angle!! + movement.value + 360) % 360)
                        ?: throw RuntimeException("Bad angle : $movement")
                )
                Direction.F -> Pair(position + direction.movePoint!! * movement.value, direction)
            }
        }
        return startPoint.manhattanDistanceTo(endPoint.first)
    }

    override fun part2(input: List<DirectionInput>): Int {
        val startPoint = Point(0, 0)
        val startWaypoint = Point(10, 1)
        val endPoint = input.fold(
            Pair(
                startPoint,
                startWaypoint
            )
        ) { (position, waypoint): Pair<Point, Point>, movement: DirectionInput ->
            when (movement.direction) {
                Direction.N, Direction.S,
                Direction.E, Direction.W -> Pair(position, waypoint + movement.direction.movePoint!! * movement.value)
                Direction.L, Direction.R -> Pair(position, movement.turnWaypoint(waypoint))
                Direction.F -> Pair(position + waypoint * movement.value, waypoint)
            }
        }
        return startPoint.manhattanDistanceTo(endPoint.first)
    }

}

data class DirectionInput(val direction: Direction, val value: Int) {
    fun turnWaypoint(waypoint: Point): Point =
        when (this) {
            DirectionInput(Direction.L, 90),
            DirectionInput(Direction.R, 270) -> {
                Point(waypoint.y() * -1, waypoint.x())
            }
            DirectionInput(Direction.R, 90),
            DirectionInput(Direction.L, 270) -> {
                Point(waypoint.y(), waypoint.x() * -1)
            }
            DirectionInput(Direction.L, 180),
            DirectionInput(Direction.R, 180) -> {
                waypoint * -1
            }
            else -> throw RuntimeException("Bad waypoint turn: $this")
        }
}

enum class Direction(val angle: Int? = null, val movePoint: Point? = null) {
    N(0, Point(0, 1)),
    E(90, Point(1, 0)),
    S(180, Point(0, -1)),
    W(270, Point(-1, 0)),
    L,
    R,
    F;

    companion object {
        private val angleDirections = values().filter { it.angle != null }.associateBy { it.angle!! }
        fun getDirectionFromAngle(a: Int): Direction? =
            angleDirections[a]
    }
}

fun main() {
    println("Day 12")
    Day12().run()
}