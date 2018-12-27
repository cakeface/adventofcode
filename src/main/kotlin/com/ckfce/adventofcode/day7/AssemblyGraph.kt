package com.ckfce.adventofcode.day7

import java.lang.IllegalStateException
import kotlin.math.max
import kotlin.math.min

class AssemblyGraph(requirements: List<String>) {
    val graph: Graph

    init {
        graph = parseGraph(requirements)
    }

    fun regExtract(r: Regex, line: String): String {
        return r.find(line)
            ?.groupValues
            ?.get(1) ?: ""
    }

    fun parseFromNode(line: String): String {
        return regExtract("Step ([A-Z]) must".toRegex(), line)
    }

    fun parseToNode(line: String): String {
        return regExtract("step ([A-Z]) can".toRegex(), line)
    }


    fun parseGraph(rs: List<String>): Graph {
        val nodes = mutableSetOf<Node>()
        val edges = mutableSetOf<Edge>()
        for (line in rs) {
            val t = Node(parseToNode(line))
            val f = Node(parseFromNode(line))
            nodes.add(t)
            nodes.add(f)
            edges.add(Edge(from = f, to = t))
        }
        return Graph(nodes.toSet(), edges.toSet())
    }

    fun findRoots(graph: Graph): Set<Node> {
        return graph.nodes
            .filter { test -> graph.edges.map { it.to }.all { it != test } }
            .toSet()
    }

    fun children(n: Node, g: Graph): Set<Node> {
        return g.edges
            .filter { it.from == n }
            .map { it.to }
            .toSet()
    }

    fun areParentsProcessed(n: Node, processed: List<Node>, g: Graph): Boolean {
        return g.edges
            .filter { it.to == n }
            .map { it.from }
            .all { processed.contains(it) }
    }

    fun findNext(processed: List<Node>, available: Set<Node>, g: Graph): List<Node> {
        return available
            .sortedBy { it.name }
            .filter { areParentsProcessed(it, processed, g) }
            .toList()
    }

    fun correctOrder(): List<Node> {
        val start = findRoots(graph)
        val processed = mutableListOf<Node>()
        val available = mutableSetOf<Node>()
        available.addAll(start)

        println("root: $start")
        while (processed.size < graph.nodes.size) {
            println("available: $available")
            val n = findNext(processed, available, graph).first()
            println("Chose $n")
            available.remove(n)
            processed.add(n)
            available.addAll(children(n, graph))
        }
        return processed.toList()
    }

    fun notDone(p: ProcessingState, g: Graph): Boolean {
        return p.processed.size != g.nodes.size
    }

    fun finishWork(p: ProcessingState, g: Graph) {
        p.workers.filter { it.isDone() }
            .forEach{
                p.processed.add(it.currentNode)
                p.available.addAll(children(it.currentNode, g))
            }
        p.workers.removeIf { it.isDone() }
    }

    fun readyNodes(numWorkers: Int, p: ProcessingState, g: Graph): List<Node> {
        val numNeeded = numWorkers - p.workers.size

        val workable = mutableSetOf<Node>()
        val next = findNext(p.processed, p.available, g)

        val numReady = min(numNeeded, next.size)
        for (i in 0 until numReady) {
            workable.add(next[i])
        }
        return workable.toList()
    }

    fun timeToProcess(n: Node, fixedDuration: Int): Int {
        val c = n.name.toCharArray().first()
        val cSeconds = c.toInt() - 65
        return cSeconds + fixedDuration
    }

    fun startWork(fixedDuration: Int, readyNodes: List<Node>, p: ProcessingState) {
        readyNodes.forEach {
            p.available.remove(it)
            p.workers.add(Worker(it, timeToProcess(it, fixedDuration)))
        }
    }

    fun workSteps(numWorkers: Int, fixedDuration: Int): Int {
        var clock = -1
        val p = ProcessingState(mutableListOf(), mutableSetOf(), mutableSetOf())
        p.available.addAll(findRoots(graph))
        while(notDone(p, graph)) {
            clock++
            p.tick()
            finishWork(p, graph)
            val readyNodes = readyNodes(numWorkers, p, graph)
            startWork(fixedDuration, readyNodes, p)
        }
        return clock
    }

}

data class Node(val name: String)
data class Edge(val from: Node, val to: Node)
data class Graph(val nodes: Set<Node>, val edges: Set<Edge>)
data class ProcessingState(val processed: MutableList<Node>,
    val available: MutableSet<Node>,
    val workers: MutableSet<Worker>) {

    fun tick() {
        workers.forEach { it.tick() }
    }
}