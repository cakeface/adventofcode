package com.ckfce.adventofcode.day6

import com.ckfce.adventofcode.AdventTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GridTest : AdventTest() {

    @Test
    fun testDistance() {
        val g = Grid(listOf())
        assertEquals(2, g.distance(Coord(1,1), Coord(2,2)))
        assertEquals(2, g.distance(Coord(2, 2), Coord(1,1)))
    }

    @Test
    fun testBoundAndInfinites() {
        val g = Grid(listOf())
        val b = g.boundingBox(sampleCoords())
        assertEquals(1, b.leftX)
        assertEquals(1, b.bottomY)
        assertEquals(8, b.rightX)
        assertEquals(9, b.topY)
        val i = g.findInfinites(b, sampleCoords())

        assertEquals(4, i.size)
        assertTrue(i.contains(Coord(1,1)))
    }

    @Test
    fun testBoundAndInfinitesInput() {
        val g = Grid(input())
        val b = g.boundingBox(g.parsedCoords())
        assertEquals(45, b.leftX)
        assertEquals(44, b.bottomY)
        assertEquals(354, b.rightX)
        assertEquals(356, b.topY)
        val i = g.findInfinites(b, g.parsedCoords())

        assertEquals(4, i.size)
        println(i)
        assertTrue(i.contains(Coord(1,1)))
    }

    @Test
    fun testParse() {
        val g = Grid(sample())
        assertEquals(sampleCoords(), g.parsedCoords())
    }

    @Test
    fun testFindClosest() {
        val g = Grid(sample())
        val c = g.findClosest(Coord(3,2), sampleCoords())
        assertEquals(Area(Coord(3, 4), 2), c)
        val c2 = g.findClosest(Coord(3,6), sampleCoords())
        assertEquals(g.NO_CLOSEST, c2)
    }

    @Test
    fun testCalculateAreas() {
        val g = Grid(sample())
        val aa = g.calculateAreas()
        println(aa)
        assertEquals(2, aa.size)
    }

    @Test
    fun testSample() {
        val g = Grid(sample())

        assertEquals(17, g.maxArea().size)
    }

    @Test
    fun solve1() {
        val g = Grid(input())
        val max = g.maxArea()

        println(max.size)
    }

    @Test
    fun testSafeLocations() {
        val g = Grid(sample())
        val ls = g.safeLocations(32)
        assertEquals(16, ls.size)
    }

    @Test
    fun solve2() {
        val g = Grid(input())
        val safeLocations = g.safeLocations(10000)
        println(safeLocations.size)
    }

    private fun sampleCoords(): List<Coord> {
        return listOf(
            Coord(1, 1),
            Coord(1, 6),
            Coord(8, 3),
            Coord(3, 4),
            Coord(5, 5),
            Coord(8, 9))
    }

    private fun sample(): List<String> {
        return listOf(
            "1, 1",
                "1, 6" ,
                "8, 3" ,
                "3, 4" ,
                "5, 5" ,
                "8, 9")
    }
}