package com.ckfce.adventofcode.day6

import kotlin.math.absoluteValue

class Grid(val coordStrings: List<String>) {

    val NO_CLOSEST = Area(Coord(-1, -1), -1)

    fun stringToCoord(s: String): Coord {
        val hs = s.split(',')
        return Coord(hs[0].trim().toInt(), hs[1].trim().toInt())
    }

    fun parsedCoords(): List<Coord> {
        return coordStrings.asSequence()
            .map { stringToCoord(it) }
            .toList()
    }

    fun findInfinites(bounds: Box, coords: List<Coord>): List<Coord> {
        val infinites = mutableSetOf<Coord>()
        for (y in bounds.topY - 1 .. bounds.topY + 1) {
            for (x in bounds.leftX - 1 .. bounds.rightX + 1) {
                val c = findClosest(Coord(x, y), coords)
                infinites.add(c.coord)
            }
        }
        return infinites.toList()
    }

    fun boundingBox(coords: List<Coord>): Box {
        val minX = coords.asSequence().map { it.x }.min() ?: 0
        val minY = coords.asSequence().map { it.y }.min() ?: 0
        val maxX = coords.asSequence().map { it.x }.max() ?: 0
        val maxY = coords.asSequence().map { it.y }.max() ?: 0

        return Box(leftX = minX, bottomY = minY, rightX =  maxX, topY = maxY)
    }

    fun distance(from: Coord, to: Coord): Int {
        val dx = (to.x - from.x).absoluteValue
        val dy = (to.y - from.y).absoluteValue
        return dx + dy
    }

    fun vectorToAllCoords(from: Coord, coords: List<Coord>): Set<Vector> {
        return coords.asSequence()
            .map { Vector(from, it, distance(from, it)) }
            .toSet()
    }

    fun allLocations(box: Box): List<Coord> {
        val ls = mutableListOf<Coord>()
        for (y in box.bottomY .. box.topY) {
            for (x in box.leftX..box.rightX) {
                ls.add(Coord(x, y))
            }
        }
        return ls.toList()
    }

    /**
     * find locations with a sum distance to all coords
     */
    fun safeLocations(safeDistance: Int): Set<Coord> {
        val coords = parsedCoords()
        val box = boundingBox(coords)

        return allLocations(box)
            .asSequence()
            .map { vectorToAllCoords(it, coords) }
            .map { LocationSum(it.first().from, it.map { it.distance }.sum()) }
            .filter { it.sum < safeDistance }
            .map { it.coord }
            .toSet()
    }

    fun findClosest(from: Coord, coords: List<Coord>): Area {
        val distanceMapped = vectorToAllCoords(from, coords)
            .asSequence()
            .groupBy { it.distance }

        val minDistance = distanceMapped.keys.min() ?: -1

        val closeVs = distanceMapped[minDistance] ?: listOf()
        if (closeVs.size != 1) {
            return NO_CLOSEST
        }
        val v = closeVs[0]
        return Area(v.to, v.distance)
    }


    fun printLoc(b: Box, x: Int, y: Int, a: Area, coords: List<Coord>) {
        when {
            coords.contains(Coord(x, y)) -> print("X")
            a == NO_CLOSEST -> print(".")
            x == b.leftX || x == b.rightX -> print("|")
            y == b.topY || y == b.bottomY -> print("=")
            else -> print(" ")
        }

    }
    fun calculateAreas(): List<Area> {
        val coords = parsedCoords()
        val box = boundingBox(coords)
        val infinites = findInfinites(box, coords)

        val g = mutableListOf<Area>()
        for (y in box.bottomY .. box.topY) {
            for (x in box.leftX .. box.rightX) {
                val c = findClosest(Coord(x, y), coords)
                //println("Closest to x: $x, y: $y is $c")
                printLoc(box, x, y, c, coords)
                g.add(c)
            }
            println()
        }

        return g.asSequence()
            .filter { it != NO_CLOSEST }
            .groupBy { it.coord }.asSequence()
            .map { Area(it.key, it.value.size) }
            .filter { !infinites.contains(it.coord) }
            .toList()
    }

    fun maxArea(): Area {
        return calculateAreas().asSequence()
            .maxBy { it.size } ?: NO_CLOSEST
    }
}

data class Coord(val x: Int, val y: Int)

data class Area(val coord: Coord, val size: Int)

data class Box(val leftX: Int, val topY: Int, val rightX: Int, val bottomY: Int)

data class Vector(val from: Coord, val to: Coord, val distance: Int)

data class LocationSum(val coord: Coord, val sum: Int)