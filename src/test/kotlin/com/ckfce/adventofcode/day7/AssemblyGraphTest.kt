package com.ckfce.adventofcode.day7

import assertk.assert
import assertk.assertions.containsAll
import assertk.assertions.isEqualTo
import com.ckfce.adventofcode.AdventTest
import org.junit.Test

class AssemblyGraphTest : AdventTest() {

    @Test
    fun testParseToNode() {
        val ag = AssemblyGraph(listOf())
        val s = ag.parseToNode("Step C must be finished before step A can begin.")
        assert(s).isEqualTo("A")
    }

    @Test
    fun testParse() {
        val ag = AssemblyGraph(sample())
        val g = ag.parseGraph(sample())
        println(g)
        assert(g.nodes).containsAll(
            Node("A"),
            Node("B"),
            Node("C"),
            Node("D"),
            Node("E"),
            Node("F"))
    }

    @Test
    fun testFindRoot() {
        val ag = AssemblyGraph(sample())
        val n = ag.findRoots(ag.graph)
        assert(n).isEqualTo(setOf(Node("C")))
    }

    @Test
    fun testCorrectOrder() {
        val ag = AssemblyGraph(sample())
        val o = ag.correctOrder()
        printOrder(o)
    }

    @Test
    fun solve1() {
        val ag = AssemblyGraph(input())
        val o = ag.correctOrder()
        printOrder(o)
    }

    @Test
    fun testWorkSteps() {
        val ag = AssemblyGraph(sample())
        val time = ag.workSteps(numWorkers = 2, fixedDuration = 0)
        assert(time).isEqualTo(15)
    }

    @Test
    fun solve2() {
        val ag = AssemblyGraph(input())
        val time = ag.workSteps(numWorkers = 5, fixedDuration = 60)
        println(time)
    }

    fun printOrder(nodes: List<Node>) {
        nodes.forEach{ print(it.name) }
        println()
    }

    fun sample(): List<String> {
        return listOf(
            "Step C must be finished before step A can begin.",
            "Step C must be finished before step F can begin.",
            "Step A must be finished before step B can begin.",
            "Step A must be finished before step D can begin.",
            "Step B must be finished before step E can begin.",
            "Step D must be finished before step E can begin.",
            "Step F must be finished before step E can begin.")
    }
}