package com.ckfce.adventofcode.day8

import org.junit.Test
import assertk.assert
import assertk.assertions.isEqualTo
import com.ckfce.adventofcode.AdventTest

class TreeTest: AdventTest() {
    @Test
    fun testParseNodes() {
        val t = Tree(sample())
        val n = t.parse(sample())

        assert(n).isEqualTo(
            Node(
                listOf(
                    Node(
                        listOf(),
                        listOf(10, 11, 12)),
                    Node(
                        listOf(
                            Node(
                                listOf(),
                                listOf(99))),
                        listOf(2)
                    )),
                listOf(1, 1, 2)
            )
        )
    }

    @Test
    fun testChecksum() {
        val t = Tree(sample())
        val s = t.checksum()
        assert(s).isEqualTo(138)
    }

    @Test
    fun solve1() {
        val t = Tree(input()[0])
        println(t.checksum())
    }

    @Test
    fun testIndexedSum() {
        val t = Tree(sample())
        val i = t.indexedSum()
        assert(i).isEqualTo(66)
    }

    @Test
    fun solve2() {
        val t = Tree(input()[0])
        println(t.indexedSum())
    }

    fun sample(): String {
        return "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"
    }
}