package com.ckfce.adventofcode.day4

import org.junit.Assert
import org.junit.Test

class GuardLogTest {

    @Test
    fun testSortingLog() {
        val l = GuardLog(testData())
        val sorted = l.sortLog()
        Assert.assertEquals("[1518-11-01 00:00]", sorted[0].substring(0, 18))
        Assert.assertEquals("[1518-11-05 00:55]", sorted[sorted.size - 1].substring(0, 18))

    }

    @Test
    fun testParseDate() {
        val l = GuardLog(testData())
        val d = l.extractDate( "[1518-11-05 00:03] Guard #99 begins shift")

        Assert.assertEquals(1518, d.year)
        Assert.assertEquals(5, d.dayOfMonth)
        Assert.assertEquals(3, d.minute)
    }

    private fun testData(): List<String> {
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
}