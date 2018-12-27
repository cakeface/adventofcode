package com.ckfce.adventofcode.day7

data class Worker(val currentNode: Node, val timeToProcess: Int) {
    var remainingSeconds: Int = timeToProcess

    fun tick() {
        remainingSeconds--
    }

    fun isDone(): Boolean {
        return remainingSeconds <= -1
    }
}