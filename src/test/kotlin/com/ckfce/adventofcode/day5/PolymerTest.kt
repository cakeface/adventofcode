package com.ckfce.adventofcode.day5

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class PolymerTest {

    @Test
    fun testUnitReaction() {
        val p = Polymer("")
        Assert.assertTrue(p.unitReaction('l', 'L'))
        Assert.assertTrue(p.unitReaction('L', 'l'))
        assertFalse(p.unitReaction('l', 'l'))
        assertFalse(p.unitReaction('L', 'L'))
        assertFalse(p.unitReaction('b', 'L'))
    }

    @Test
    fun testReactOnce() {
        val p = Polymer("")
        assertEquals("aaaaaaa", p.reactOnce("aaaaaaa"))
        assertEquals("aaaaaaa", p.reactOnce("aaaaaaabB"))
        assertEquals("aaaaaaaCc", p.reactOnce("aaaaaaaBbCc"))
    }

    @Test
    fun testSample() {
        val p = Polymer("dabAcCaCBAcCcaDA")
        assertEquals("dabCBAcaDA", p.react())
    }

    @Test
    fun solve1() {
        val p = Polymer(input()[0])
        println(p.react().length)
    }

    @Test
    fun testStrip() {
        val p = Polymer("")
        assertEquals("dbcCCBcCcD", p.strip("dabAcCaCBAcCcaDA", 'a'))
    }

    @Test
    fun testReactWithout() {
        val p = Polymer("dabAcCaCBAcCcaDA")
        assertEquals("dbCBcD", p.reactWithout('a'))
        assertEquals("daCAcaDA", p.reactWithout('b'))
        assertEquals("daDA", p.reactWithout('c'))
        assertEquals("abCBAc", p.reactWithout('d'))
    }

    @Test
    fun solve2() {
        val p = Polymer(input()[0])
        var smallest = Int.MAX_VALUE
        for (l in letters()) {
            val reactionSize = p.reactWithout(l).length
            if (reactionSize < smallest) {
                smallest = reactionSize
            }
        }
        println(smallest)
    }

    private fun letters(): List<Char> {
        return listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')
    }

    private fun input(): List<String> {
        val i = this.javaClass.getResource("input.txt").readText(Charsets.UTF_8)
        return i.split("\n").filter { it.isNotBlank() }
    }
}