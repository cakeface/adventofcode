package com.ckfce.adventofcode.day3

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class FabricTest {

    @Test
    fun testParseClaim() {

        val f = Fabric(100)
        val c = f.parseClaim("#123 @ 3,2: 5x4")
        assertEquals(123, c.number)
        assertEquals(3, c.leftOffset)
        assertEquals(2, c.topOffset)
        assertEquals(5, c.width)
        assertEquals(4, c.height)
    }

    @Test
    fun testMeasureClaim() {
        val  f = Fabric(100)

        f.measureClaim("#123 @ 3,2: 5x4")

        assertEquals(0, f.inches[2][2].size)
        assertEquals(1, f.inches[2][3].size)
        assertEquals(1, f.inches[5][7].size)
        assertEquals(0, f.inches[6][7].size)
    }

    @Test
    fun testCountOverlapping() {
        val f = Fabric(100)
        f.measureClaim("#1 @ 1,3: 4x4")
        f.measureClaim("#2 @ 3,1: 4x4")
        f.measureClaim("#3 @ 5,5: 2x2")

        assertEquals(4, f.countOverlappingInches())
    }

    @Test
    fun testCountInput() {
        val f = Fabric(1000)
        for (claimStr in lines()) {
            f.measureClaim(claimStr)
        }
        println(f.countOverlappingInches())
    }

    @Test
    fun testFindCleanClaims() {
        val f = Fabric(100)
        f.measureClaim("#1 @ 1,3: 4x4")
        f.measureClaim("#2 @ 3,1: 4x4")
        f.measureClaim("#3 @ 5,5: 2x2")

        assertEquals(3, f.findCleanClaims()[0].number)
    }

    @Test
    fun cleanClaimsFromInput() {
        val f = Fabric(1000)
        for (claimStr in lines()) {
            f.measureClaim(claimStr)
        }
        val cleanClaims = f.findCleanClaims()
        assertEquals(1, cleanClaims.size)
        println(cleanClaims[0].toString())
    }

    private fun lines(): List<String> {
        val i = this.javaClass.getResource("input.txt").readText(Charsets.UTF_8)
        return i.split("\n").filter { it.isNotBlank() }

    }
}