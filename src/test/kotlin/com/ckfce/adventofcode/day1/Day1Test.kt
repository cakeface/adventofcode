package com.ckfce.adventofcode.day1

import org.junit.Assert
import org.junit.Test

class Day1Test {

    @Test
    fun testBasicCalibrations() {
        val c1 = Calibrator(listOf("+1"))
        Assert.assertEquals(1, c1.calibrate())
    }

    @Test
    fun testComplicated() {
        val c1 = Calibrator(listOf("+1", "-3", "-5", "+2"))
        Assert.assertEquals(-5, c1.calibrate())
    }

    @Test
    fun testEmpty() {
        val c = Calibrator(listOf("", "+1"))
        Assert.assertEquals(1, c.calibrate())
    }

    @Test
    fun testInputFile() {
        val c = Calibrator(lines())
        print(c.calibrate())
    }

    @Test
    fun testCycles() {
        Assert.assertEquals(0,
            Calibrator(listOf("+1", "-1")).cycle())

        Assert.assertEquals(10,
            Calibrator(listOf("+3", "+3", "+4", "-2", "-4")).cycle())

        //-6, +3, +8, +5, -6 first reaches 5 twice.
        Assert.assertEquals(5,
            Calibrator(listOf("-6", "+3", "+8", "+5", "-6")).cycle())

        //+7, +7, -2, -7, -4 first reaches 14 twice.
        Assert.assertEquals(14,
            Calibrator(listOf("+7", "+7", "-2", "-7", "-4")).cycle())
    }

    @Test
    fun testCyclesInput() {
        print(Calibrator(lines()).cycle())
    }

    private fun lines(): List<String> {
        val i = this.javaClass.getResource("input.txt").readText(Charsets.UTF_8)
        return i.split("\n")
    }
}