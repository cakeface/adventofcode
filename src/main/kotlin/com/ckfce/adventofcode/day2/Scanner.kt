package com.ckfce.adventofcode.day2

import com.sun.org.apache.xpath.internal.operations.Bool
import java.lang.IllegalArgumentException

class Scanner(private val boxIds: List<String>) {

    fun checksum(): Int {
        var twos = 0
        var threes = 0
        for (id in boxIds) {
            val grouped = id.asSequence().groupBy { it }
            if (contains(grouped, 2)) {
                twos += 1
            }
            if (contains(grouped, 3)) {
                threes += 1
            }
        }
        return twos * threes
    }

    fun contains(grouped: Map<Char, List<Char>>, size: Int): Boolean {
        for (g in grouped) {
            if (g.value.size == size) {
                return true
            }
        }
        return false
    }

    /**
     * Return true if the words are only 1 letter different.
     */
    fun compare(a: String, b: String): CompareResult {
        if (a.length != b.length) {
            throw IllegalArgumentException("$a and $b must be the same length")
        }

        val tolerance = 1
        var mismatch = 0
        var matches = ""

        for (i in 0 until a.length) {
            if (a[i] != b[i]) {
                mismatch += 1
                if (mismatch > tolerance) {
                    return CompareResult(false, matches)
                }
            } else {
                matches += a[i]
            }
        }
        return CompareResult(true, matches)
    }

    fun findMatched(): String {
        for (s1 in boxIds) {
            for (s2 in boxIds) {
                if (s1 != s2) {
                    val r = compare(s1, s2)
                    if (r.matched) {
                        return r.matches
                    }
                }
            }
        }
        return "" // or throw exeception?
    }

}

class CompareResult(val matched: Boolean, val matches: String)