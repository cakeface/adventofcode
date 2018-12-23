package com.ckfce.adventofcode.day4

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class GuardLogTest {

    @Test
    fun testSortingLog() {
        val l = GuardLog(sampleData())
        val sorted = l.sortLog()
        assertEquals("[1518-11-01 00:00]", sorted[0].substring(0, 18))
        assertEquals("[1518-11-05 00:55]", sorted[sorted.size - 1].substring(0, 18))

    }

    @Test
    fun testParseDate() {
        val l = GuardLog(sampleData())
        val d = l.extractDate( "[1518-11-05 00:03] Guard #99 begins shift")

        assertEquals(1518, d.year)
        assertEquals(5, d.dayOfMonth)
        assertEquals(3, d.minute)
    }

    @Test
    fun testExtractGuard() {
        val l = GuardLog(sampleData())
        assertEquals(88, l.extractGuard("[1518-11-01 00:00] Guard #88 begins shift"))
    }

    @Test
    fun testParse() {
        val l = GuardLog(sampleData())
        l.parse()
        assertEquals(1, l.days[0].date.dayOfMonth)
        assertEquals(5, l.days[0].startMinute)

    }

    @Test
    fun testFindSleepy() {
        val l = GuardLog(sampleData())
        l.parse()
        val guardId = l.findSleepiest()
        assertEquals(10, guardId)
    }

    @Test
    fun testYawnyMinute() {
        val l = GuardLog(sampleData())
        l.parse()
        assertEquals(24, l.yawnyMinute(10))
    }

    @Test
    fun solve1() {
        val l = GuardLog(input())
        l.parse()
        val g = l.findSleepiest()
        val m = l.yawnyMinute(g)
        println(g * m)
    }

    @Test
    fun testFrequentMinute() {
        val l = GuardLog(sampleData())
        l.parse()
        val gm = l.frequentMinute()
        assertEquals(99, gm.guardId)
        assertEquals(45, gm.minute)
    }

    @Test
    fun solve2() {
        val l = GuardLog(input())
        l.parse()
        val gm = l.frequentMinute()
        println(gm.guardId * gm.minute)
    }

    private fun sampleData(): List<String> {
        return listOf(
            "[1518-11-04 00:36] falls asleep",
            "[1518-11-04 00:46] wakes up",
            "[1518-11-05 00:03] Guard #99 begins shift",
            "[1518-11-05 00:45] falls asleep",
            "[1518-11-05 00:55] wakes up",
            "[1518-11-03 00:05] Guard #10 begins shift",
            "[1518-11-02 00:40] falls asleep",
            "[1518-11-02 00:50] wakes up",
            "[1518-11-01 00:30] falls asleep",
            "[1518-11-01 00:55] wakes up",
            "[1518-11-01 23:58] Guard #99 begins shift",
            "[1518-11-03 00:24] falls asleep",
            "[1518-11-03 00:29] wakes up",
            "[1518-11-04 00:02] Guard #99 begins shift",
            "[1518-11-01 00:00] Guard #10 begins shift",
            "[1518-11-01 00:05] falls asleep",
            "[1518-11-01 00:25] wakes up"
        )
    }

    private fun input(): List<String> {
        val i = this.javaClass.getResource("input.txt").readText(Charsets.UTF_8)
        return i.split("\n").filter { it.isNotBlank() }
    }
}