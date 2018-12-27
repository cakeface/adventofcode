package com.ckfce.adventofcode

open class AdventTest {

    protected fun input(): List<String> {
        val i = this.javaClass.getResource("input.txt").readText(Charsets.UTF_8)
        return i.split("\n").filter { it.isNotBlank() }
    }
}