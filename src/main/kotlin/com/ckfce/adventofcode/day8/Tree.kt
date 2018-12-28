package com.ckfce.adventofcode.day8

class Tree(val license: String) {

    fun takeNode(state: List<Int>): NodeTaken {
        val numChildren = state[0]
        val numMetadata = state[1]
        var snip = state.subList(2, state.size)
        val accumNodes = mutableListOf<Node>()
        for (i in 0 until numChildren) {
            val t = takeNode(snip)
            snip = t.remainingState
            accumNodes.add(t.node)
        }
        val accumMetadata = mutableListOf<Int>()
        for (i in 0 until  numMetadata) {
            accumMetadata.add(snip[i])
        }
        val n = Node(accumNodes.toList(), accumMetadata.toList())
        return NodeTaken(snip.subList(numMetadata, snip.size), n)
    }

    fun parse(s: String): Node {
        val parts = s.split(" ").map { it.toInt() }.toList()
        return takeNode(parts).node
    }

    fun sumNode(node: Node): Int {
        return node.metadata.sum() + node.children.map { sumNode(it) }.sum()
    }

    fun checksum(): Int {
        val n = parse(license)
        return sumNode(n)
    }

    fun indexedSumNode(node: Node): Int {
        if (node.children.isEmpty()) {
            return node.metadata.sum()
        }

        val nodeSums = node.children.map { indexedSumNode(it) }.toList()

        var accum = 0
        node.metadata.forEach {
            if (it > 0 && it <= nodeSums.size) {
                accum += nodeSums[it - 1]
            }
        }

        return accum
    }
    fun indexedSum(): Int {
        val n = parse(license)
        return indexedSumNode(n)
    }
}

data class NodeTaken(val remainingState: List<Int>, val node: Node)
data class Node(val children: List<Node>, val metadata: List<Int>)
