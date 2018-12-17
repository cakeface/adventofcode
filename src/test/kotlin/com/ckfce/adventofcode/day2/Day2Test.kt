package com.ckfce.adventofcode.day2

import com.ckfce.adventofcode.day1.Calibrator
import org.junit.Assert
import org.junit.Test

class Day2Test {

    @Test
    fun testSampleChecksum() {
        val s = listOf(
            "abcdef",
            "bababc",
            "abbcde",
            "abcccd",
            "aabcdd",
            "abcdee",
            "ababab"
        )
        Assert.assertEquals(12, Scanner(s).checksum())
    }

    @Test
    fun testChecksumInput() {
        print(Scanner(lines()).checksum())
    }

    @Test
    fun testFindMatched() {
        val s = listOf(
            "abcde",
            "fghij",
            "klmno",
            "pqrst",
            "fguij",
            "axcye",
            "wvxyz"
        )
        Assert.assertEquals("fgij", Scanner(s).findMatched())
    }

    @Test
    fun testFindMatchedInput() {
        print(Scanner(lines()).findMatched())
    }

    @Test
    fun testCompare() {
        val r1 = Scanner(listOf()).compare("fghij", "fguij")
        Assert.assertTrue(r1.matched)
        Assert.assertEquals("fgij", r1.matches)
        val r2 = Scanner(listOf()).compare("fghik", "fguij")
        Assert.assertFalse(r2.matched)
    }

    private fun lines(): List<String> {
        val i = this.javaClass.getResource("input.txt").readText(Charsets.UTF_8)
        return i.split("\n").filter { it.isNotBlank() }

    }
}