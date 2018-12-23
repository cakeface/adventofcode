package com.ckfce.adventofcode.day5

class Polymer(val polymer: String) {

    fun reactOnce(polymer: String): String {
        for (i in 0 until polymer.length - 1) {
            if (unitReaction(polymer[i], polymer[i+1])) {
                return polymer.removeRange(i, i + 2)
            }
        }
        return polymer
    }

    fun unitReaction(l: Char, r: Char): Boolean {
        if (l.toLowerCase() != r.toLowerCase()) {
            return false
        }
        return l != r
    }

    fun fullyReact(polymer: String): String {
        val np = reactOnce(polymer)
        if (np == polymer) {
            return polymer
        }
        return fullyReact(np)
    }

    fun react(): String {
        return fullyReact(polymer)
    }

    fun strip(string: String, letter: Char): String {
        return string.replace(letter.toString(), "", ignoreCase = true)
    }

    fun reactWithout(letter: Char): String {
        val s = strip(polymer, letter)
        return fullyReact(s)
    }
}